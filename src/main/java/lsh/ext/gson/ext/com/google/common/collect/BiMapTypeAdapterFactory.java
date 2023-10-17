package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Converter;
import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for {@link BiMap} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see Adapter
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BiMapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<BiMap<K, V>> {

	@Getter
	private static final TypeAdapterFactory instance = new BiMapTypeAdapterFactory<>(null, null);

	@Nullable
	private final Supplier<? extends BiMap<K, V>> newBiMapFactory;

	@Nullable
	private final Converter<K, String> keyConverter;

	/**
	 * @param newBiMapFactory
	 * 		New bidirectional map factory
	 * @param keyConverter
	 * 		Key converter
	 * @param <K>
	 * 		Key type
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link BiMapTypeAdapterFactory} with a custom new {@link BiMap} factory.
	 */
	public static <K, V> TypeAdapterFactory getInstance(@Nullable final Supplier<? extends BiMap<K, V>> newBiMapFactory,
			@Nullable final Converter<K, String> keyConverter) {
		if ( newBiMapFactory == null && keyConverter == null ) {
			return instance;
		}
		return new BiMapTypeAdapterFactory<>(newBiMapFactory, keyConverter);
	}

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return BiMap.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	protected TypeAdapter<BiMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type[] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type valueType = typeArguments[1];
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		if ( newBiMapFactory == null && keyConverter == null ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TypeAdapter<BiMap<K, V>> castBiMapTypeAdapter = (TypeAdapter) Adapter.getInstance(valueTypeAdapter);
			return castBiMapTypeAdapter;
		}
		if ( newBiMapFactory != null && keyConverter == null ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Supplier<? extends BiMap<String, V>> castNewBiMapFactory = (Supplier) newBiMapFactory;
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TypeAdapter<BiMap<K, V>> castBiMapTypeAdapter = (TypeAdapter) Adapter.getInstance(valueTypeAdapter, castNewBiMapFactory);
			return castBiMapTypeAdapter;
		}
		if ( newBiMapFactory == null && keyConverter != null ) {
			return Adapter.getInstance(valueTypeAdapter, keyConverter);
		}
		return Adapter.getInstance(valueTypeAdapter, newBiMapFactory, keyConverter);
	}

	/**
	 * Represents a type adapter for {@link BiMap} from Google Guava.
	 *
	 * @author Lyubomyr Shaydariv
	 * @see BiMapTypeAdapterFactory
	 */
	public static final class Adapter<K, V>
			extends TypeAdapter<BiMap<K, V>> {

		private static final Supplier<? extends BiMap<?, ?>> defaultNewBiMapFactory = HashBiMap::create;
		private static final Converter<?, String> defaultKeyConverter = Converter.identity();

		private final TypeAdapter<V> valueTypeAdapter;
		private final Supplier<? extends BiMap<K, V>> newBiMapFactory;
		private final Converter<K, String> keyConverter;
		private final Converter<String, K> reverseKeyConverter;

		private Adapter(final TypeAdapter<V> valueTypeAdapter, final Supplier<? extends BiMap<K, V>> newBiMapFactory,
				final Converter<K, String> keyConverter) {
			this.valueTypeAdapter = valueTypeAdapter;
			this.newBiMapFactory = newBiMapFactory;
			this.keyConverter = keyConverter;
			reverseKeyConverter = keyConverter.reverse();
		}

		/**
		 * @param valueTypeAdapter
		 * 		Bidirectional map value type adapter
		 * @param <V>
		 * 		Bidirectional map value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<BiMap<String, V>> getInstance(final TypeAdapter<V> valueTypeAdapter) {
			@SuppressWarnings("unchecked")
			final Supplier<? extends BiMap<String, V>> newBiMapFactory = (Supplier<? extends BiMap<String, V>>) defaultNewBiMapFactory;
			@SuppressWarnings("unchecked")
			final Converter<String, String> keyConverter = (Converter<String, String>) defaultKeyConverter;
			return getInstance(valueTypeAdapter, newBiMapFactory, keyConverter);
		}

		/**
		 * @param valueTypeAdapter
		 * 		Bidirectional map value type adapter
		 * @param newBiMapFactory
		 * 		A {@link BiMap} factory to create instance used while deserialization
		 * @param <V>
		 * 		Bidirectional map value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<BiMap<String, V>> getInstance(final TypeAdapter<V> valueTypeAdapter,
				final Supplier<? extends BiMap<String, V>> newBiMapFactory) {
			@SuppressWarnings("unchecked")
			final Converter<String, String> keyConverter = (Converter<String, String>) defaultKeyConverter;
			return getInstance(valueTypeAdapter, newBiMapFactory, keyConverter);
		}

		/**
		 * @param valueTypeAdapter
		 * 		Bidirectional map value type adapter
		 * @param keyConverter
		 * 		A converter to convert key to JSON object property names
		 * @param <K>
		 * 		Bidirectional map key type
		 * @param <V>
		 * 		Bidirectional map value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <K, V> TypeAdapter<BiMap<K, V>> getInstance(final TypeAdapter<V> valueTypeAdapter, final Converter<K, String> keyConverter) {
			@SuppressWarnings("unchecked")
			final Supplier<? extends BiMap<K, V>> newBiMapFactory = (Supplier<? extends BiMap<K, V>>) defaultNewBiMapFactory;
			return getInstance(valueTypeAdapter, newBiMapFactory, keyConverter);
		}

		/**
		 * @param valueTypeAdapter
		 * 		Bidirectional map value type adapter
		 * @param newBiMapFactory
		 * 		A {@link BiMap} factory to create instance used while deserialization
		 * @param keyConverter
		 * 		A converter to convert key to JSON object property names
		 * @param <K>
		 * 		Bidirectional map key type
		 * @param <V>
		 * 		Bidirectional map value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <K, V> TypeAdapter<BiMap<K, V>> getInstance(final TypeAdapter<V> valueTypeAdapter,
				final Supplier<? extends BiMap<K, V>> newBiMapFactory, final Converter<K, String> keyConverter) {
			return new Adapter<>(valueTypeAdapter, newBiMapFactory, keyConverter);
		}

		@Override
		public void write(final JsonWriter out, final BiMap<K, V> biMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<K, V> e : biMap.entrySet() ) {
				final String key = keyConverter.convert(e.getKey());
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public BiMap<K, V> read(final JsonReader in)
				throws IOException {
			final BiMap<K, V> biMap = newBiMapFactory.get();
			in.beginObject();
			while ( in.hasNext() ) {
				final K key = reverseKeyConverter.convert(in.nextName());
				final V value = valueTypeAdapter.read(in);
				biMap.put(key, value);
			}
			in.endObject();
			return biMap;
		}

	}

}
