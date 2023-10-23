package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
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
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.Transformer;

/**
 * Represents a type adapter factory for {@link MultiMap} from Apache Commons Collections 4.
 */
@SuppressWarnings("deprecation")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiMapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<MultiMap<K, V>>
		implements ITypeAdapterFactory<MultiMap<K, V>> {

	private final IInstanceFactory.IProvider<? extends MultiMap<K, V>> newMultiMapFactoryProvider;
	private final Transformer<? super K, String> keyMapper;
	private final Transformer<? super String, ? extends K> keyReverseMapper;

	/**
	 * @param newMultiMapFactoryProvider
	 * 		MultiMap factory provider
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
	public static <K, V> ITypeAdapterFactory<MultiMap<K, V>> getInstance(
			final IInstanceFactory.IProvider<? extends MultiMap<K, V>> newMultiMapFactoryProvider,
			final Transformer<? super K, String> keyMapper,
			final Transformer<? super String, ? extends K> keyReverseMapper
	) {
		return new MultiMapTypeAdapterFactory<>(newMultiMapFactoryProvider, keyMapper, keyReverseMapper);
	}

	@Override
	protected TypeAdapter<MultiMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !MultiMap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<MultiMap<K, V>> castTypeToken = (TypeToken<MultiMap<K, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<MultiMap<K, V>> castNewMultiMapFactoryProvider = (IInstanceFactory.IProvider<MultiMap<K, V>>) newMultiMapFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewMultiMapFactoryProvider.provide(castTypeToken), keyMapper, keyReverseMapper);
	}

	/**
	 * Represents a type adapter for {@link MultiMap} from Apache Commons Collections 4.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<K, V>
			extends TypeAdapter<MultiMap<K, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends MultiMap<K, V>> newMultiMapFactory;
		private final Transformer<? super K, String> keyMapper;
		private final Transformer<? super String, ? extends K> keyReverseMapper;

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
				final IInstanceFactory<? extends MultiMap<K, V>> newMultiMapFactory,
				final Transformer<? super K, String> keyMapper,
				final Transformer<? super String, ? extends K> keyReverseMapper
		) {
			return new Adapter<>(valueTypeAdapter, newMultiMapFactory, keyMapper, keyReverseMapper)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final MultiMap<K, V> multiMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<K, Object> e : multiMap.entrySet() ) {
				final String key = keyMapper.transform(e.getKey());
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
			final MultiMap<K, V> multiMap = newMultiMapFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final K key = keyReverseMapper.transform(in.nextName());
				final V value = valueTypeAdapter.read(in);
				multiMap.put(key, value);
			}
			in.endObject();
			return multiMap;
		}

	}

}
