package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

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
 * @param <V>
 * 		Value type
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BiMapTypeAdapterFactory<V>
		extends AbstractTypeAdapterFactory<BiMap<String, V>>
		implements ITypeAdapterFactory<BiMap<String, V>> {

	private final IInstanceFactory.IProvider<? extends BiMap<String, V>> newBiMapFactoryProvider;

	/**
	 * @param newBiMapFactoryProvider
	 * 		New bidirectional map factory
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link BiMapTypeAdapterFactory} with a custom new {@link BiMap} factory.
	 */
	public static <V> ITypeAdapterFactory<BiMap<String, V>> getInstance(
			final IInstanceFactory.IProvider<? extends BiMap<String, V>> newBiMapFactoryProvider
	) {
		return new BiMapTypeAdapterFactory<>(newBiMapFactoryProvider);
	}

	@Override
	@Nullable
	protected TypeAdapter<BiMap<String, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !BiMap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<BiMap<String, V>> castTypeToken = (TypeToken<BiMap<String, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<BiMap<String, V>> castNewBimapFactoryProvider = (IInstanceFactory.IProvider<BiMap<String, V>>) newBiMapFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewBimapFactoryProvider.provide(castTypeToken));
	}

	/**
	 * Represents a type adapter for {@link BiMap} from Google Guava.
	 *
	 * @param <V>
	 * 		Value type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<V>
			extends TypeAdapter<BiMap<String, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends BiMap<String, V>> newBiMapInstanceFactory;

		/**
		 * @param valueTypeAdapter
		 * 		Bidirectional map value type adapter
		 * @param newBiMapInstanceFactory
		 * 		A {@link BiMap} factory to create instance used while deserialization
		 * @param <V>
		 * 		Bidirectional map value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<BiMap<String, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final IInstanceFactory<? extends BiMap<String, V>> newBiMapInstanceFactory
		) {
			return new Adapter<>(valueTypeAdapter, newBiMapInstanceFactory)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final BiMap<String, V> biMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<String, V> e : biMap.entrySet() ) {
				final String key = e.getKey();
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public BiMap<String, V> read(final JsonReader in)
				throws IOException {
			final BiMap<String, V> biMap = newBiMapInstanceFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final String key = in.nextName();
				final V value = valueTypeAdapter.read(in);
				biMap.put(key, value);
			}
			in.endObject();
			return biMap;
		}

	}

}
