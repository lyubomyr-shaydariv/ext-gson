package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

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
import org.apache.commons.collections4.BidiMap;

/**
 * Represents a type adapter factory for {@link BidiMap} from Apache Commons Collections 4.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BidiMapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<BidiMap<K, V>> {

	private final Supplier<? extends BidiMap<K, V>> newBidiMapFactory;
	private final Function<? super K, String> keyMapper;
	private final Function<? super String, ? extends K> keyReverseMapper;

	/**
	 * @param newBidiMapFactory
	 * 		New bidirectional map factory
	 * @param keyMapper
	 * 		Key mapper
	 * @param keyReverseMapper
	 * 		Key reverse mapper
	 * @param <K>
	 * 		Key type
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link BidiMapTypeAdapterFactory} with a custom new {@link BidiMap} factory.
	 */
	public static <K, V> TypeAdapterFactory getInstance(
			final Supplier<? extends BidiMap<K, V>> newBidiMapFactory,
			final Function<? super K, String> keyMapper,
			final Function<? super String, ? extends K> keyReverseMapper
	) {
		return new BidiMapTypeAdapterFactory<>(newBidiMapFactory, keyMapper, keyReverseMapper);
	}

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return BidiMap.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	protected TypeAdapter<BidiMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		return Adapter.getInstance(valueTypeAdapter, newBidiMapFactory, keyMapper, keyReverseMapper);
	}

	/**
	 * Represents a type adapter for {@link BidiMap} from Apache Commons Collections 4.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<K, V>
			extends TypeAdapter<BidiMap<K, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final Supplier<? extends BidiMap<K, V>> newBidiMapFactory;
		private final Function<? super K, String> keyMapper;
		private final Function<? super String, ? extends K> keyReverseMapper;

		/**
		 * @param valueTypeAdapter
		 * 		Bidirectional map value type adapter
		 * @param newBidiMapFactory
		 * 		A {@link BidiMap} factory to create instance used while deserialization
		 * @param keyMapper
		 * 		A mapper to map key to JSON object property names
		 * @param keyReverseMapper
		 * 		A mapper to map key to JSON object property names in reverse
		 * @param <K>
		 * 		Bidirectional map key type
		 * @param <V>
		 * 		Bidirectional map value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <K, V> TypeAdapter<BidiMap<K, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final Supplier<? extends BidiMap<K, V>> newBidiMapFactory,
				final Function<? super K, String> keyMapper,
				final Function<? super String, ? extends K> keyReverseMapper
		) {
			return new Adapter<>(valueTypeAdapter, newBidiMapFactory, keyMapper, keyReverseMapper);
		}

		@Override
		public void write(final JsonWriter out, final BidiMap<K, V> bidiMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<K, V> e : bidiMap.entrySet() ) {
				final String key = keyMapper.apply(e.getKey());
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public BidiMap<K, V> read(final JsonReader in)
				throws IOException {
			final BidiMap<K, V> bidiMap = newBidiMapFactory.get();
			in.beginObject();
			while ( in.hasNext() ) {
				final K key = keyReverseMapper.apply(in.nextName());
				final V value = valueTypeAdapter.read(in);
				bidiMap.put(key, value);
			}
			in.endObject();
			return bidiMap;
		}

	}

}
