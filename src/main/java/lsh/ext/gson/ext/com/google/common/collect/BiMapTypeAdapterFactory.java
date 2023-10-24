package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.BiMap;
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

/**
 * Represents a type adapter factory for {@link BiMap} from Google Guava.
 *
 * @param <K>
 * 		Key type
 * @param <V>
 * 		Value type
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BiMapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<BiMap<K, V>>
		implements ITypeAdapterFactory<BiMap<K, V>> {

	private final IInstanceFactory.IProvider<? extends BiMap<K, V>> newBiMapFactoryProvider;
	@SuppressWarnings("Guava")
	private final Function<? super K, String> keyMapper;
	@SuppressWarnings("Guava")
	private final Function<? super String, ? extends K> keyReverseMapper;

	/**
	 * @param newBiMapFactoryProvider
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
	 * @return An instance of {@link BiMapTypeAdapterFactory} with a custom new {@link BiMap} factory.
	 */
	public static <K, V> ITypeAdapterFactory<BiMap<K, V>> getInstance(
			final IInstanceFactory.IProvider<? extends BiMap<K, V>> newBiMapFactoryProvider,
			@SuppressWarnings("Guava") final Function<? super K, String> keyMapper,
			@SuppressWarnings("Guava") final Function<? super String, ? extends K> keyReverseMapper
	) {
		return new BiMapTypeAdapterFactory<>(newBiMapFactoryProvider, keyMapper, keyReverseMapper);
	}

	@Override
	@Nullable
	protected TypeAdapter<BiMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !BiMap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<BiMap<K, V>> castTypeToken = (TypeToken<BiMap<K, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<BiMap<K, V>> castNewBimapFactoryProvider = (IInstanceFactory.IProvider<BiMap<K, V>>) newBiMapFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewBimapFactoryProvider.provide(castTypeToken), keyMapper, keyReverseMapper);
	}

	/**
	 * Represents a type adapter for {@link BiMap} from Google Guava.
	 *
	 * @param <K>
	 * 		Key type
	 * @param <V>
	 * 		Value type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<K, V>
			extends TypeAdapter<BiMap<K, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends BiMap<K, V>> newBiMapInstanceFactory;
		@SuppressWarnings("Guava")
		private final Function<? super K, String> keyMapper;
		@SuppressWarnings("Guava")
		private final Function<? super String, ? extends K> keyReverseMapper;

		/**
		 * @param valueTypeAdapter
		 * 		Bidirectional map value type adapter
		 * @param newBiMapInstanceFactory
		 * 		A {@link BiMap} factory to create instance used while deserialization
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
		public static <K, V> TypeAdapter<BiMap<K, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final IInstanceFactory<? extends BiMap<K, V>> newBiMapInstanceFactory,
				@SuppressWarnings("Guava") final Function<? super K, String> keyMapper,
				@SuppressWarnings("Guava") final Function<? super String, ? extends K> keyReverseMapper
		) {
			return new Adapter<>(valueTypeAdapter, newBiMapInstanceFactory, keyMapper, keyReverseMapper)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final BiMap<K, V> biMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<K, V> e : biMap.entrySet() ) {
				final String key = keyMapper.apply(e.getKey());
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public BiMap<K, V> read(final JsonReader in)
				throws IOException {
			final BiMap<K, V> biMap = newBiMapInstanceFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final K key = keyReverseMapper.apply(in.nextName());
				final V value = valueTypeAdapter.read(in);
				biMap.put(key, value);
			}
			in.endObject();
			return biMap;
		}

	}

}
