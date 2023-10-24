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
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.Transformer;

/**
 * Represents a type adapter factory for {@link BidiMap} from Apache Commons Collections 4.
 *
 * @param <K>
 * 		Key type
 * @param <V>
 * 		Value type
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BidiMapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<BidiMap<K, V>>
		implements ITypeAdapterFactory<BidiMap<K, V>> {

	private final IInstanceFactory.IProvider<? extends BidiMap<K, V>> newBidiMapFactoryProvider;
	private final Transformer<? super K, String> keyMapper;
	private final Transformer<? super String, ? extends K> keyReverseMapper;

	/**
	 * @param newBidiMapFactoryProvider
	 * 		New bidirectional map factory provider
	 * @param keyMapper
	 * 		Key mapper
	 * @param keyReverseMapper
	 * 		Key reverse mapper
	 * @param <K>
	 * 		Key type
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link BidiMapTypeAdapterFactory} with a custom new {@link BidiMap} factory provider.
	 */
	public static <K, V> ITypeAdapterFactory<BidiMap<K, V>> getInstance(
			final IInstanceFactory.IProvider<? extends BidiMap<K, V>> newBidiMapFactoryProvider,
			final Transformer<? super K, String> keyMapper,
			final Transformer<? super String, ? extends K> keyReverseMapper
	) {
		return new BidiMapTypeAdapterFactory<>(newBidiMapFactoryProvider, keyMapper, keyReverseMapper);
	}

	@Override
	protected TypeAdapter<BidiMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !BidiMap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<BidiMap<K, V>> castTypeToken = (TypeToken<BidiMap<K, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<BidiMap<K, V>> castNewBidiMapFactoryProvider = (IInstanceFactory.IProvider<BidiMap<K, V>>) newBidiMapFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewBidiMapFactoryProvider.provide(castTypeToken), keyMapper, keyReverseMapper);
	}

	/**
	 * Represents a type adapter for {@link BidiMap} from Apache Commons Collections 4.
	 *
	 * @param <K>
	 * 		Key type
	 * @param <V>
	 * 		Value type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<K, V>
			extends TypeAdapter<BidiMap<K, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends BidiMap<K, V>> newBidiMapFactory;
		private final Transformer<? super K, String> keyMapper;
		private final Transformer<? super String, ? extends K> keyReverseMapper;

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
				final IInstanceFactory<? extends BidiMap<K, V>> newBidiMapFactory,
				final Transformer<? super K, String> keyMapper,
				final Transformer<? super String, ? extends K> keyReverseMapper
		) {
			return new Adapter<>(valueTypeAdapter, newBidiMapFactory, keyMapper, keyReverseMapper)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final BidiMap<K, V> bidiMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<K, V> e : bidiMap.entrySet() ) {
				final String key = keyMapper.transform(e.getKey());
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public BidiMap<K, V> read(final JsonReader in)
				throws IOException {
			final BidiMap<K, V> bidiMap = newBidiMapFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final K key = keyReverseMapper.transform(in.nextName());
				final V value = valueTypeAdapter.read(in);
				bidiMap.put(key, value);
			}
			in.endObject();
			return bidiMap;
		}

	}

}
