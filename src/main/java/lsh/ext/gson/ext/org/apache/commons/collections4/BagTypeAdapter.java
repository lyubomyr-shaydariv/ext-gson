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
import lsh.ext.gson.IFactory0;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.Transformer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BagTypeAdapter<E>
		extends TypeAdapter<Bag<E>> {

	private final org.apache.commons.collections4.Factory<? extends IBuilder2<? super E, ? super Integer, ? extends Bag<E>>> bagBuilderFactory;
	private final Transformer<? super E, String> keyMapper;
	private final Transformer<? super String, ? extends E> keyReverseMapper;

	public static <E> TypeAdapter<Bag<E>> getInstance(
			final org.apache.commons.collections4.Factory<? extends IBuilder2<? super E, ? super Integer, ? extends Bag<E>>> bagBuilderFactory,
			final Transformer<? super E, String> keyMapper,
			final Transformer<? super String, ? extends E> keyReverseMapper
	) {
		return new BagTypeAdapter<>(bagBuilderFactory, keyMapper, keyReverseMapper)
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
		final IBuilder2<? super E, ? super Integer, ? extends Bag<E>> builder = bagBuilderFactory.create();
		while ( in.hasNext() ) {
			builder.modify(keyReverseMapper.transform(in.nextName()), in.nextInt());
		}
		in.endObject();
		return builder.build();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<E>
			extends AbstractTypeAdapterFactory<Bag<E>> {

		private final IBuilder2.IFactory<? super E, ? super Integer, ? extends Bag<E>> bagBuilderFactory;
		private final BagTypeAdapter.IKeyMapperFactory<E> keyMapperFactory;

		public static <E> ITypeAdapterFactory<Bag<E>> getInstance() {
			return getInstance(new BagTypeAdapter.IKeyMapperFactory<>() {
				@Override
				public Transformer<E, String> createKeyMapper(final TypeToken<E> typeToken) {
					throw new UnsupportedOperationException(String.valueOf(typeToken));
				}

				@Override
				public Transformer<String, E> createReverseKeyMapper(final TypeToken<E> typeToken) {
					throw new UnsupportedOperationException(String.valueOf(typeToken));
				}
			});
		}

		public static <E> ITypeAdapterFactory<Bag<E>> getInstance(
				final BagTypeAdapter.IKeyMapperFactory<E> keyMapperFactory
		) {
			return getInstance((IFactory0.IFactory<Bag<E>>) typeToken -> {
				throw new UnsupportedOperationException(String.valueOf(typeToken));
			}, keyMapperFactory);
		}

		public static <E> ITypeAdapterFactory<Bag<E>> getInstance(
				final IFactory0.IFactory<Bag<E>> factoryFactory,
				final BagTypeAdapter.IKeyMapperFactory<E> keyMapperFactory
		) {
			return getInstance((IBuilder2.IFactory<E, Integer, Bag<E>>) typeToken -> builder(typeToken, factoryFactory), keyMapperFactory);
		}

		public static <E> ITypeAdapterFactory<Bag<E>> getInstance(
				final IBuilder2.IFactory<? super E, ? super Integer, ? extends Bag<E>> bagBuilderFactory,
				final BagTypeAdapter.IKeyMapperFactory<E> keyMapperFactory
		) {
			return new Factory<>(bagBuilderFactory, keyMapperFactory);
		}

		public static <E> IBuilder2<E, Integer, Bag<E>> builder(
				final TypeToken<Bag<E>> typeToken,
				final IFactory0.IFactory<Bag<E>> factoryFactory
		) {
			final IFactory0<Bag<E>> factory = factoryFactory.create(typeToken);
			return new IBuilder2<>() {
				private final Bag<E> hashBag = factory.create();

				@Override
				public void modify(final E e, final Integer n) {
					hashBag.add(e, n);
				}

				@Override
				public Bag<E> build() {
					return hashBag;
				}
			};
		}

		@Override
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
			final IBuilder2.IFactory<E, Integer, Bag<E>> castBagBuilderFactory = (IBuilder2.IFactory<E, Integer, Bag<E>>) bagBuilderFactory;
			return BagTypeAdapter.getInstance(() -> castBagBuilderFactory.create(castTypeToken), keyMapperFactory.createKeyMapper(elementTypeToken), keyMapperFactory.createReverseKeyMapper(elementTypeToken));
		}

	}

	public interface IKeyMapperFactory<E> {

		Transformer<E, String> createKeyMapper(TypeToken<E> typeToken);

		Transformer<String, E> createReverseKeyMapper(TypeToken<E> typeToken);

	}

}
