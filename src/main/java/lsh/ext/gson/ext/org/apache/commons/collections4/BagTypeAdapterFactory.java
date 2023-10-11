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
import lsh.ext.gson.IInstanceFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.Transformer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BagTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<Bag<E>>
		implements ITypeAdapterFactory<Bag<E>> {

	private final IInstanceFactory.IProvider<? extends Bag<E>> newBagFactoryProvider;
	private final IKeyMapperFactory<E> keyMapperFactory;

	public static <E> ITypeAdapterFactory<Bag<E>> getInstance(
			final IInstanceFactory.IProvider<? extends Bag<E>> newBagFactoryProvider,
			final IKeyMapperFactory<E> keyMapperFactory
	) {
		return new BagTypeAdapterFactory<>(newBagFactoryProvider, keyMapperFactory);
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
		final IInstanceFactory.IProvider<Bag<E>> castNewBagFactoryProvider = (IInstanceFactory.IProvider<Bag<E>>) newBagFactoryProvider;
		return Adapter.getInstance(castNewBagFactoryProvider.provide(castTypeToken), keyMapperFactory.createKeyMapper(elementTypeToken), keyMapperFactory.createReverseKeyMapper(elementTypeToken));
	}

	public interface IKeyMapperFactory<E> {

		Transformer<E, String> createKeyMapper(TypeToken<E> typeToken);

		Transformer<String, E> createReverseKeyMapper(TypeToken<E> typeToken);

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<E>
			extends TypeAdapter<Bag<E>> {

		private final IInstanceFactory<? extends Bag<E>> newBagFactory;
		private final Transformer<? super E, String> keyMapper;
		private final Transformer<? super String, ? extends E> keyReverseMapper;

		public static <E> TypeAdapter<Bag<E>> getInstance(
				final IInstanceFactory<? extends Bag<E>> newBagFactory,
				final Transformer<? super E, String> keyMapper,
				final Transformer<? super String, ? extends E> keyReverseMapper
		) {
			return new Adapter<>(newBagFactory, keyMapper, keyReverseMapper)
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
			final Bag<E> bag = newBagFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				bag.add(keyReverseMapper.transform(in.nextName()), in.nextInt());
			}
			in.endObject();
			return bag;
		}

	}

}
