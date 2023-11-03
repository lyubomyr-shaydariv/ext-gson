package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMultiset;
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
import lsh.ext.gson.IFactory0;
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
			builder.modify(element);
		}
		in.endArray();
		return builder.build();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<E>
			extends AbstractTypeAdapterFactory<Multiset<E>> {

		private final IBuilder1.IFactory<? super E, ? extends Multiset<E>> multisetBuilderFactory;

		public static <E> ITypeAdapterFactory<Multiset<E>> getInstance() {
			return getInstance((IFactory0.IFactory<Multiset<E>>) typeToken -> {
				throw new UnsupportedOperationException(String.valueOf(typeToken));
			});
		}

		public static <E> ITypeAdapterFactory<Multiset<E>> getInstance(
				final IFactory0.IFactory<Multiset<E>> factoryFactory
		) {
			return getInstance((IBuilder1.IFactory<E, Multiset<E>>) typeToken -> builder(typeToken, factoryFactory));
		}

		public static <E> ITypeAdapterFactory<Multiset<E>> getInstance(
				final IBuilder1.IFactory<? super E, ? extends Multiset<E>> builderFactory
		) {
			return new Factory<>(builderFactory);
		}

		public static <E> IBuilder1<E, Multiset<E>> builder(
				final TypeToken<Multiset<E>> typeToken,
				final IFactory0.IFactory<Multiset<E>> factoryFactory
		) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Class<? extends Multiset> rawType = (Class<? extends Multiset<?>>) typeToken.getRawType();
			if ( ImmutableMultiset.class.isAssignableFrom(rawType) ) {
				return new ImmutableBuilder<>(ImmutableMultiset.builder());
			}
			final IFactory0<Multiset<E>> factory = factoryFactory.create(typeToken);
			@SuppressWarnings("LawOfDemeter")
			final Multiset<E> multiset = factory.create();
			return new MutableBuilder<>(multiset);
		}

		@Override
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
			final IBuilder1.IFactory<E, Multiset<E>> castMultisetBuilderFactory = (IBuilder1.IFactory<E, Multiset<E>>) multisetBuilderFactory;
			return MultisetTypeAdapter.getInstance(elementTypeAdapter, () -> castMultisetBuilderFactory.create(castTypeToken));
		}

		@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
		private static final class MutableBuilder<E>
				implements IBuilder1<E, Multiset<E>> {

			private final Multiset<E> multiset;

			@Override
			public void modify(final E v) {
				multiset.add(v);
			}

			@Override
			public Multiset<E> build() {
				return multiset;
			}

		}

		@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
		private static final class ImmutableBuilder<E>
				implements IBuilder1<E, Multiset<E>> {

			private final ImmutableMultiset.Builder<E> builder;

			@Override
			public void modify(final E v) {
				builder.add(v);
			}

			@Override
			public Multiset<E> build() {
				return builder.build();
			}

		}

	}

}
