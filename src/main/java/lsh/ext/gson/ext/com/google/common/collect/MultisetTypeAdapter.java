package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractHierarchyTypeAdapterFactory;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class MultisetTypeAdapter<E>
		extends TypeAdapter<Multiset<E>> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final IFactory<? extends IBuilder1<? super E, ? extends Multiset<E>>> builderFactory;

	public static <E> TypeAdapter<Multiset<E>> getInstance(
			final TypeAdapter<E> valueTypeAdapter,
			final IFactory<? extends IBuilder1<? super E, ? extends Multiset<E>>> builderFactory
	) {
		return new MultisetTypeAdapter<>(valueTypeAdapter, builderFactory)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Multiset<E> multiset)
			throws IOException {
		out.beginArray();
		for ( final Multiset.Entry<E> e : multiset.entrySet() ) {
			final E element = e.getElement();
			final int count = e.getCount();
			for ( int i = 0; i < count; i++ ) {
				elementTypeAdapter.write(out, element);
			}
		}
		out.endArray();
	}

	@Override
	public Multiset<E> read(final JsonReader in)
			throws IOException {
		in.beginArray();
		final IBuilder1<? super E, ? extends Multiset<E>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			final E element = elementTypeAdapter.read(in);
			builder.accept(element);
		}
		in.endArray();
		return builder.build();
	}

	public static final class Factory<E>
			extends AbstractHierarchyTypeAdapterFactory<Multiset<E>> {

		private static final ITypeAdapterFactory<?> instance = new Factory<>(Factory::defaultBuilderFactory);

		private final IBuilder1.ILookup<? super E, ? extends Multiset<E>> builderLookup;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Factory(final IBuilder1.ILookup<? super E, ? extends Multiset<E>> builderLookup) {
			super((Class) Multiset.class);
			this.builderLookup = builderLookup;
		}

		public static <E> ITypeAdapterFactory<Multiset<E>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<Multiset<E>> castInstance = (ITypeAdapterFactory<Multiset<E>>) instance;
			return castInstance;
		}

		public static <E> ITypeAdapterFactory<Multiset<E>> getInstance(
				final IBuilder1.ILookup<? super E, ? extends Multiset<E>> builderLookup
		) {
			return new Factory<>(builderLookup);
		}

		// TODO handle all known implementations
		public static <E> IFactory<IBuilder1<E, Multiset<E>>> defaultBuilderFactory(final TypeToken<? super Multiset<E>> typeToken) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Class<? extends Multiset> rawType = (Class<? extends Multiset>) typeToken.getRawType();
			if ( rawType == HashMultiset.class ) {
				return () -> IBuilder1.of(HashMultiset.create());
			}
			if ( rawType == LinkedHashMultiset.class ) {
				return () -> IBuilder1.of(LinkedHashMultiset.create());
			}
			if ( ImmutableMultiset.class.isAssignableFrom(rawType) ) {
				return Factory::immutableBuilder;
			}
			return () -> IBuilder1.of(HashMultiset.create());
		}

		@Override
		@Nullable
		protected TypeAdapter<Multiset<E>> createTypeAdapter(final Gson gson, final TypeToken<Multiset<E>> typeToken) {
			@Nullable
			final Type elementType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
			assert elementType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
			@SuppressWarnings("unchecked")
			final IBuilder1.ILookup<E, Multiset<E>> castBuilderLookup = (IBuilder1.ILookup<E, Multiset<E>>) builderLookup;
			return MultisetTypeAdapter.getInstance(elementTypeAdapter, castBuilderLookup.lookup(typeToken));
		}

		private static <E> IBuilder1<E, Multiset<E>> immutableBuilder() {
			return new IBuilder1<>() {
				private final ImmutableMultiset.Builder<E> builder = ImmutableMultiset.builder();

				@Override
				public void accept(final E e) {
					builder.add(e);
				}

				@Override
				public Multiset<E> build() {
					return builder.build();
				}
			};
		}

	}

}
