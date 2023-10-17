package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.common.base.Converter;
import com.google.common.base.Supplier;
import com.google.common.collect.Multimap;
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

/**
 * Represents a type adapter factory for {@link Multimap} from Google Guava.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultimapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<Multimap<K, V>> {

	@SuppressWarnings("Guava")
	private final Supplier<? extends Multimap<K, V>> newMultimapFactory;
	private final Converter<K, String> keyConverter;

	/**
	 * @param newMultimapFactory
	 * 		Multimap factory
	 * @param keyConverter
	 * 		Key converter
	 * @param <K>
	 * 		Key type
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link MultimapTypeAdapterFactory} with a custom new {@link Multimap} factory.
	 */
	public static <K, V> TypeAdapterFactory getInstance(@SuppressWarnings("Guava") final Supplier<? extends Multimap<K, V>> newMultimapFactory,
			final Converter<K, String> keyConverter) {
		return new MultimapTypeAdapterFactory<>(newMultimapFactory, keyConverter);
	}

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return Multimap.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	protected TypeAdapter<Multimap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		return Adapter.getInstance(valueTypeAdapter, newMultimapFactory, keyConverter);
	}

	/**
	 * Represents a type adapter for {@link Multimap} from Google Guava.
	 */
	public static final class Adapter<K, V>
			extends TypeAdapter<Multimap<K, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		@SuppressWarnings("Guava")
		private final Supplier<? extends Multimap<K, V>> newMultimapFactory;
		private final Converter<? super K, String> keyConverter;
		private final Converter<? super String, ? extends K> reverseKeyConverter;

		private Adapter(
				final TypeAdapter<V> valueTypeAdapter,
				@SuppressWarnings("Guava") final Supplier<? extends Multimap<K, V>> newMultimapFactory,
				@SuppressWarnings("BoundedWildcard") final Converter<K, String> keyConverter
		) {
			this.valueTypeAdapter = valueTypeAdapter;
			this.newMultimapFactory = newMultimapFactory;
			this.keyConverter = keyConverter;
			reverseKeyConverter = keyConverter.reverse();
		}

		/**
		 * @param valueTypeAdapter
		 * 		Multimap value type adapter
		 * @param newMultimapFactory
		 * 		A {@link Multimap} factory to create instance used while deserialization
		 * @param keyConverter
		 * 		A converter to convert key to JSON object property names
		 * @param <K>
		 * 		Multimap key type
		 * @param <V>
		 * 		Multimap value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <K, V> TypeAdapter<Multimap<K, V>> getInstance(final TypeAdapter<V> valueTypeAdapter,
				@SuppressWarnings("Guava") final Supplier<? extends Multimap<K, V>> newMultimapFactory, final Converter<K, String> keyConverter) {
			return new Adapter<>(valueTypeAdapter, newMultimapFactory, keyConverter);
		}

		@Override
		public void write(final JsonWriter out, final Multimap<K, V> multimap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<K, V> e : multimap.entries() ) {
				final String key = keyConverter.convert(e.getKey());
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public Multimap<K, V> read(final JsonReader in)
				throws IOException {
			final Multimap<K, V> multimap = newMultimapFactory.get();
			in.beginObject();
			while ( in.hasNext() ) {
				final K key = reverseKeyConverter.convert(in.nextName());
				final V value = valueTypeAdapter.read(in);
				multimap.put(key, value);
			}
			in.endObject();
			return multimap;
		}

	}

}
