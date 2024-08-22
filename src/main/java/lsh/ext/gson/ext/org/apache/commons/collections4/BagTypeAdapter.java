package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.bag.HashBag;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BagTypeAdapter<E>
		extends TypeAdapter<Bag<E>> {

	private final org.apache.commons.collections4.Factory<? extends IBuilder2<? super E, ? super Integer, ? extends Bag<E>>> builderFactory;
	private final Transformer<? super E, String> keyMapper;
	private final Transformer<? super String, ? extends E> keyReverseMapper;

	public static <E> TypeAdapter<Bag<E>> getInstance(
			final org.apache.commons.collections4.Factory<? extends IBuilder2<? super E, ? super Integer, ? extends Bag<E>>> builderFactory,
			final Transformer<? super E, String> keyMapper,
			final Transformer<? super String, ? extends E> keyReverseMapper
	) {
		return new BagTypeAdapter<>(builderFactory, keyMapper, keyReverseMapper)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Bag<E> bag)
			throws IOException {
		out.beginObject();
		for ( final E e : bag ) {
			out.name(keyMapper.transform(e));
			out.value(bag.getCount(e));
		}
		out.endObject();
	}

	@Override
	public Bag<E> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super E, ? super Integer, ? extends Bag<E>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			builder.accept(keyReverseMapper.transform(in.nextName()), in.nextInt());
		}
		in.endObject();
		return builder.build();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<E>
			extends AbstractTypeAdapterFactory<Bag<E>> {

		private final IBuilder2.IFactory<? super E, ? super Integer, ? extends Bag<E>> builderFactory;
		private final Transformer<? super TypeToken<E>, ? extends Transformer<? super E, String>> createToString;
		private final Transformer<? super TypeToken<E>, ? extends Transformer<? super String, ? extends E>> createFromString;

		public static <E> ITypeAdapterFactory<Bag<E>> getInstance(
				final IBuilder2.IFactory<? super E, ? super Integer, ? extends Bag<E>> fallback,
				final Transformer<? super TypeToken<E>, ? extends Transformer<? super E, String>> createToString,
				final Transformer<? super TypeToken<E>, ? extends Transformer<? super String, ? extends E>> createFromString
		) {
			final IBuilder2.IFactory<? super E, ? super Integer, ? extends Bag<E>> bagBuilderFactory = typeToken -> defaultBuilder(typeToken, fallback);
			return new Factory<>(bagBuilderFactory, createToString, createFromString);
		}

		public static <E> IBuilder2<E, Integer, Bag<E>> defaultBuilder(
				final TypeToken<? super Bag<E>> typeToken,
				final IBuilder2.IFactory<? super E, ? super Integer, ? extends Bag<E>> fallback
		) {
			@SuppressWarnings("unchecked")
			final Class<? super Bag<?>> rawType = (Class<? super Bag<?>>) typeToken.getRawType();
			if ( HashBag.class.isAssignableFrom(rawType) ) {
				return builder(new HashBag<>());
			}
			@SuppressWarnings("unchecked")
			final IBuilder2<E, Integer, Bag<E>> fallbackBuilder = (IBuilder2<E, Integer, Bag<E>>) fallback.create(typeToken);
			return fallbackBuilder;
		}

		public static <E, B extends Bag<E>> IBuilder2<E, Integer, B> builder(final B bag) {
			return new IBuilder2<>() {
				@Override
				public void accept(final E e, final Integer n) {
					bag.add(e, n);
				}

				@Override
				public B build() {
					return bag;
				}
			};
		}

		@Override
		@Nullable
		protected TypeAdapter<Bag<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !Bag.class.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			@Nullable
			final Type elementType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
			assert elementType != null;
			@SuppressWarnings("unchecked")
			final TypeToken<E> elementTypeToken = (TypeToken<E>) TypeToken.get(elementType);
			@SuppressWarnings("unchecked")
			final TypeToken<Bag<E>> castTypeToken = (TypeToken<Bag<E>>) typeToken;
			@SuppressWarnings("unchecked")
			final IBuilder2.IFactory<E, Integer, Bag<E>> castBuilderFactory = (IBuilder2.IFactory<E, Integer, Bag<E>>) builderFactory;
			return BagTypeAdapter.getInstance(() -> castBuilderFactory.create(castTypeToken), createToString.transform(elementTypeToken), createFromString.transform(elementTypeToken));
		}

	}

}
