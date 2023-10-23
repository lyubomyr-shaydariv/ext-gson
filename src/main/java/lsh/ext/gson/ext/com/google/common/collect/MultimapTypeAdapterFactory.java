package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Multimap;
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
 * Represents a type adapter factory for {@link Multimap} from Google Guava.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultimapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<Multimap<K, V>>
		implements ITypeAdapterFactory<Multimap<K, V>> {

	private final IInstanceFactory.IProvider<? extends Multimap<K, V>> newMultimapFactoryProvider;
	@SuppressWarnings("Guava")
	private final Function<? super K, String> keyMapper;
	@SuppressWarnings("Guava")
	private final Function<? super String, ? extends K> keyReverseMapper;

	/**
	 * @param newMultimapFactoryProvider
	 * 		Multimap factory factory
	 * @param keyMapper
	 * 		Key mapper
	 * @param keyReverseMapper
	 * 		Key reverse mapper
	 * @param <K>
	 * 		Key type
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link MultimapTypeAdapterFactory} with a custom new {@link Multimap} factory.
	 */
	public static <K, V> ITypeAdapterFactory<Multimap<K, V>> getInstance(
			final IInstanceFactory.IProvider<? extends Multimap<K, V>> newMultimapFactoryProvider,
			@SuppressWarnings("Guava") final Function<? super K, String> keyMapper,
			@SuppressWarnings("Guava") final Function<? super String, ? extends K> keyReverseMapper
	) {
		return new MultimapTypeAdapterFactory<>(newMultimapFactoryProvider, keyMapper, keyReverseMapper);
	}

	@Override
	protected TypeAdapter<Multimap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !Multimap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<Multimap<K, V>> castTypeToken = (TypeToken<Multimap<K, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<Multimap<K, V>> castNewMultimapFactoryProvider = (IInstanceFactory.IProvider<Multimap<K, V>>) newMultimapFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewMultimapFactoryProvider.provide(castTypeToken), keyMapper, keyReverseMapper);
	}

	/**
	 * Represents a type adapter for {@link Multimap} from Google Guava.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<K, V>
			extends TypeAdapter<Multimap<K, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends Multimap<K, V>> newMultimapFactory;
		@SuppressWarnings("Guava")
		private final Function<? super K, String> keyMapper;
		@SuppressWarnings("Guava")
		private final Function<? super String, ? extends K> keyReverseMapper;

		/**
		 * @param valueTypeAdapter
		 * 		Multimap value type adapter
		 * @param newMultimapFactory
		 * 		A {@link Multimap} factory to create instance used while deserialization
		 * @param keyMapper
		 * 		A mapper to convert key to JSON object property names
		 * @param keyReverseMapper
		 * 		A mapper to convert key to JSON object property names in reverse
		 * @param <K>
		 * 		Multimap key type
		 * @param <V>
		 * 		Multimap value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <K, V> TypeAdapter<Multimap<K, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final IInstanceFactory<? extends Multimap<K, V>> newMultimapFactory,
				@SuppressWarnings("Guava") final Function<? super K, String> keyMapper,
				@SuppressWarnings("Guava") final Function<? super String, ? extends K> keyReverseMapper
		) {
			return new Adapter<>(valueTypeAdapter, newMultimapFactory, keyMapper, keyReverseMapper)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final Multimap<K, V> multimap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<K, V> e : multimap.entries() ) {
				final String key = keyMapper.apply(e.getKey());
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public Multimap<K, V> read(final JsonReader in)
				throws IOException {
			final Multimap<K, V> multimap = newMultimapFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final K key = keyReverseMapper.apply(in.nextName());
				final V value = valueTypeAdapter.read(in);
				multimap.put(key, value);
			}
			in.endObject();
			return multimap;
		}

	}

}
