package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
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
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class MultisetTypeAdapter<E>
		extends TypeAdapter<Multiset<E>> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final Supplier<? extends IBuilder1<? super E, ? extends Multiset<E>>> multisetBuilderFactory;

	public static <E> TypeAdapter<Multiset<E>> getInstance(
			final TypeAdapter<E> valueTypeAdapter,
			final Supplier<? extends IBuilder1<? super E, ? extends Multiset<E>>> multisetBuilderFactory
	) {
		return new MultisetTypeAdapter<>(valueTypeAdapter, multisetBuilderFactory)
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
		final IBuilder1<? super E, ? extends Multiset<E>> builder = multisetBuilderFactory.get();
		while ( in.hasNext() ) {
			final E element = elementTypeAdapter.read(in);
			builder.accept(element);
		}
		in.endArray();
		return builder.build();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<E>
			extends AbstractTypeAdapterFactory<Multiset<E>> {

		private final IBuilder1.IFactory<? super E, ? extends Multiset<E>> builderFactory;

		public static <E> ITypeAdapterFactory<Multiset<E>> getInstance(
				final IBuilder1.IFactory<? super E, ? extends Multiset<E>> builderFactory
		) {
			return new Factory<>(builderFactory);
		}

		public static <E> ITypeAdapterFactory<Multiset<E>> getDefaultBuilderInstance(
				final IBuilder1.IFactory<? super E, ? extends Multiset<E>> fallback
		) {
			return getInstance(typeToken -> defaultBuilder(typeToken, fallback));
		}

		public static <E> IBuilder1<E, Multiset<E>> defaultBuilder(
				final TypeToken<? super Multiset<E>> typeToken,
				final IBuilder1.IFactory<? super E, ? extends Multiset<E>> fallback
		) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Class<? extends Multiset> rawType = (Class<? extends Multiset>) typeToken.getRawType();
			if ( ImmutableMultiset.class.isAssignableFrom(rawType) ) {
				return immutableBuilder();
			}
			if ( rawType == HashMultiset.class ) {
				return IBuilder1.of(HashMultiset.create());
			}
			if ( rawType == LinkedHashMultiset.class ) {
				return IBuilder1.of(HashMultiset.create());
			}
			@SuppressWarnings("unchecked")
			final IBuilder1<E, Multiset<E>> fallbackBuilder = (IBuilder1<E, Multiset<E>>) fallback.create(typeToken);
			return fallbackBuilder;
		}

		public static <E> IBuilder1<E, Multiset<E>> immutableBuilder() {
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

		@Override
		@Nullable
		protected TypeAdapter<Multiset<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !Multiset.class.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			@Nullable
			final Type elementType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
			assert elementType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
			@SuppressWarnings("unchecked")
			final TypeToken<Multiset<E>> castTypeToken = (TypeToken<Multiset<E>>) typeToken;
			@SuppressWarnings("unchecked")
			final IBuilder1.IFactory<E, Multiset<E>> castMultisetBuilderFactory = (IBuilder1.IFactory<E, Multiset<E>>) builderFactory;
			return MultisetTypeAdapter.getInstance(elementTypeAdapter, () -> castMultisetBuilderFactory.create(castTypeToken));
		}

	}

}
