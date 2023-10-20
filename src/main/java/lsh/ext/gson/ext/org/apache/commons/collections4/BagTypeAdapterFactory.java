package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.Transformer;

/**
 * Represents a type adapter factory for {@link Bag} from Apache Commons Collection 4.
 *
 * @param <E>
 * 		Element type
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BagTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<Bag<E>> {

	private final Factory<? extends Bag<E>> newBagFactory;
	private final Transformer<? super E, String> keyMapper;
	private final Transformer<? super String, ? extends E> keyReverseMapper;

	/**
	 * @param newBagFactory
	 * 		Bag factory
	 * @param <E>
	 * 		Element type
	 *
	 * @return An instance of {@link BagTypeAdapterFactory} with a custom new {@link Bag} factory.
	 */
	public static <E> TypeAdapterFactory getInstance(final Factory<? extends Bag<E>> newBagFactory,
			final Transformer<? super E, String> keyMapper, final Transformer<? super String, ? extends E> keyReverseMapper
	) {
		return new BagTypeAdapterFactory<>(newBagFactory, keyMapper, keyReverseMapper);
	}

	@Override
	protected TypeAdapter<Bag<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !Bag.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type elementType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
		assert elementType != null;
		return Adapter.getInstance(newBagFactory, keyMapper, keyReverseMapper);
	}

	/**
	 * Represents a type adapter for {@link Bag} from Apache Commons Collection 4.
	 *
	 * @param <E>
	 * 		Element type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<E>
			extends TypeAdapter<Bag<E>> {

		private final Factory<? extends Bag<E>> newBagFactory;
		private final Transformer<? super E, String> keyMapper;
		private final Transformer<? super String, ? extends E> keyReverseMapper;

		/**
		 * @param newBagFactory
		 * 		A {@link Bag} factory to create instance used while deserialization
		 * @param <V>
		 * 		Bag element type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<Bag<V>> getInstance(
				final Factory<? extends Bag<V>> newBagFactory,
				final Transformer<? super V, String> keyMapper,
				final Transformer<? super String, ? extends V> keyReverseMapper
		) {
			return new Adapter<>(newBagFactory, keyMapper, keyReverseMapper);
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
			final Bag<E> bag = newBagFactory.create();
			in.beginObject();
			while ( in.hasNext() ) {
				bag.add(keyReverseMapper.transform(in.nextName()), in.nextInt());
			}
			in.endObject();
			return bag;
		}

	}

}