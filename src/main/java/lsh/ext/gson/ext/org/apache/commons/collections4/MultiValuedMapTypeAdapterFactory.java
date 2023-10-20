package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

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
import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.Transformer;

/**
 * Represents a type adapter factory for {@link MultiValuedMap} from Apache Commons Collections 4.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiValuedMapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<MultiValuedMap<K, V>> {

	private final Factory<? extends MultiValuedMap<K, V>> newMultiValuedMapFactory;
	private final Transformer<? super K, String> keyMapper;
	private final Transformer<? super String, ? extends K> keyReverseMapper;

	/**
	 * @param newMultiValuedMapFactory
	 * 		MultiValuedMap factory
	 * @param keyMapper
	 * 		Key mapper
	 * @param keyReverseMapper
	 * 		Key reverse mapper
	 * @param <K>
	 * 		Key type
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link MultiValuedMapTypeAdapterFactory} with a custom new {@link MultiValuedMap} factory.
	 */
	public static <K, V> TypeAdapterFactory getInstance(
			final Factory<? extends MultiValuedMap<K, V>> newMultiValuedMapFactory,
			final Transformer<? super K, String> keyMapper,
			final Transformer<? super String, ? extends K> keyReverseMapper
	) {
		return new MultiValuedMapTypeAdapterFactory<>(newMultiValuedMapFactory, keyMapper, keyReverseMapper);
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	protected TypeAdapter<MultiValuedMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !MultiValuedMap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		return Adapter.getInstance(valueTypeAdapter, newMultiValuedMapFactory, keyMapper, keyReverseMapper);
	}

	/**
	 * Represents a type adapter for {@link MultiValuedMap} from Apache Commons Collections 4.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<K, V>
			extends TypeAdapter<MultiValuedMap<K, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final Factory<? extends MultiValuedMap<K, V>> newMultiValuedMapFactory;
		private final Transformer<? super K, String> keyMapper;
		private final Transformer<? super String, ? extends K> keyReverseMapper;

		/**
		 * @param valueTypeAdapter
		 * 		MultiValuedMap value type adapter
		 * @param newMultiValuedMapFactory
		 * 		A {@link MultiValuedMap} factory to create instance used while deserialization
		 * @param keyMapper
		 * 		A mapper to convert key to JSON object property names
		 * @param keyReverseMapper
		 * 		A mapper to convert key to JSON object property names in reverse
		 * @param <K>
		 * 		MultiValuedMap key type
		 * @param <V>
		 * 		MultiValuedMap value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <K, V> TypeAdapter<MultiValuedMap<K, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final Factory<? extends MultiValuedMap<K, V>> newMultiValuedMapFactory,
				final Transformer<? super K, String> keyMapper,
				final Transformer<? super String, ? extends K> keyReverseMapper
		) {
			return new Adapter<>(valueTypeAdapter, newMultiValuedMapFactory, keyMapper, keyReverseMapper)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final MultiValuedMap<K, V> multiValuedMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<K, V> e : multiValuedMap.entries() ) {
				final String key = keyMapper.transform(e.getKey());
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public MultiValuedMap<K, V> read(final JsonReader in)
				throws IOException {
			final MultiValuedMap<K, V> multiValuedMap = newMultiValuedMapFactory.create();
			in.beginObject();
			while ( in.hasNext() ) {
				final K key = keyReverseMapper.transform(in.nextName());
				final V value = valueTypeAdapter.read(in);
				multiValuedMap.put(key, value);
			}
			in.endObject();
			return multiValuedMap;
		}

	}

}
