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
import org.apache.commons.collections4.MultiMap;

/**
 * Represents a type adapter factory for {@link MultiMap} from Apache Commons Collections 4.
 */
@SuppressWarnings("deprecation")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiMapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<MultiMap<K, V>> {

	private final Supplier<? extends MultiMap<K, V>> newMultiMapFactory;
	private final Function<? super K, String> keyMapper;
	private final Function<? super String, ? extends K> keyReverseMapper;

	/**
	 * @param newMultiMapFactory
	 * 		MultiMap factory
	 * @param keyMapper
	 * 		Key mapper
	 * @param keyReverseMapper
	 * 		Key reverse mapper
	 * @param <K>
	 * 		Key type
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link MultiMapTypeAdapterFactory} with a custom new {@link MultiMap} factory.
	 */
	public static <K, V> TypeAdapterFactory getInstance(
			final Supplier<? extends MultiMap<K, V>> newMultiMapFactory,
			final Function<? super K, String> keyMapper,
			final Function<? super String, ? extends K> keyReverseMapper
	) {
		return new MultiMapTypeAdapterFactory<>(newMultiMapFactory, keyMapper, keyReverseMapper);
	}

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return MultiMap.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	protected TypeAdapter<MultiMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		return Adapter.getInstance(valueTypeAdapter, newMultiMapFactory, keyMapper, keyReverseMapper);
	}

	/**
	 * Represents a type adapter for {@link MultiMap} from Apache Commons Collections 4.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<K, V>
			extends TypeAdapter<MultiMap<K, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final Supplier<? extends MultiMap<K, V>> newMultiMapFactory;
		private final Function<? super K, String> keyMapper;
		private final Function<? super String, ? extends K> keyReverseMapper;

		/**
		 * @param valueTypeAdapter
		 * 		MultiMap value type adapter
		 * @param newMultiMapFactory
		 * 		A {@link MultiMap} factory to create instance used while deserialization
		 * @param keyMapper
		 * 		A mapper to convert key to JSON object property names
		 * @param keyReverseMapper
		 * 		A mapper to convert key to JSON object property names in reverse
		 * @param <K>
		 * 		MultiMap key type
		 * @param <V>
		 * 		MultiMap value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <K, V> TypeAdapter<MultiMap<K, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final Supplier<? extends MultiMap<K, V>> newMultiMapFactory,
				final Function<? super K, String> keyMapper,
				final Function<? super String, ? extends K> keyReverseMapper
		) {
			return new Adapter<>(valueTypeAdapter, newMultiMapFactory, keyMapper, keyReverseMapper);
		}

		@Override
		public void write(final JsonWriter out, final MultiMap<K, V> multiMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<K, Object> e : multiMap.entrySet() ) {
				final String key = keyMapper.apply(e.getKey());
				@SuppressWarnings("unchecked")
				final V value = (V) e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public MultiMap<K, V> read(final JsonReader in)
				throws IOException {
			final MultiMap<K, V> multiMap = newMultiMapFactory.get();
			in.beginObject();
			while ( in.hasNext() ) {
				final K key = keyReverseMapper.apply(in.nextName());
				final V value = valueTypeAdapter.read(in);
				multiMap.put(key, value);
			}
			in.endObject();
			return multiMap;
		}

	}

}
