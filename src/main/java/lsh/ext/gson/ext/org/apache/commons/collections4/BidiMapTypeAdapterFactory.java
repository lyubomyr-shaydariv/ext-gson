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

/**
 * Represents a type adapter factory for {@link BidiMap} from Apache Commons Collections 4.
 *
 * @param <V>
 * 		Value type
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BidiMapTypeAdapterFactory<V>
		extends AbstractTypeAdapterFactory<BidiMap<String, V>>
		implements ITypeAdapterFactory<BidiMap<String, V>> {

	private final IInstanceFactory.IProvider<? extends BidiMap<String, V>> newBidiMapFactoryProvider;

	/**
	 * @param newBidiMapFactoryProvider
	 * 		New bidirectional map factory provider
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link BidiMapTypeAdapterFactory} with a custom new {@link BidiMap} factory provider.
	 */
	public static <V> ITypeAdapterFactory<BidiMap<String, V>> getInstance(
			final IInstanceFactory.IProvider<? extends BidiMap<String, V>> newBidiMapFactoryProvider
	) {
		return new BidiMapTypeAdapterFactory<>(newBidiMapFactoryProvider);
	}

	@Override
	protected TypeAdapter<BidiMap<String, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !BidiMap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<BidiMap<String, V>> castTypeToken = (TypeToken<BidiMap<String, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<BidiMap<String, V>> castNewBidiMapFactoryProvider = (IInstanceFactory.IProvider<BidiMap<String, V>>) newBidiMapFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewBidiMapFactoryProvider.provide(castTypeToken));
	}

	/**
	 * Represents a type adapter for {@link BidiMap} from Apache Commons Collections 4.
	 *
	 * @param <V>
	 * 		Value type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<V>
			extends TypeAdapter<BidiMap<String, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends BidiMap<String, V>> newBidiMapFactory;

		/**
		 * @param valueTypeAdapter
		 * 		Bidirectional map value type adapter
		 * @param newBidiMapFactory
		 * 		A {@link BidiMap} factory to create instance used while deserialization
		 * @param <V>
		 * 		Bidirectional map value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<BidiMap<String, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final IInstanceFactory<? extends BidiMap<String, V>> newBidiMapFactory
		) {
			return new Adapter<>(valueTypeAdapter, newBidiMapFactory)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final BidiMap<String, V> bidiMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<String, V> e : bidiMap.entrySet() ) {
				final String key = e.getKey();
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public BidiMap<String, V> read(final JsonReader in)
				throws IOException {
			final BidiMap<String, V> bidiMap = newBidiMapFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final String key = in.nextName();
				final V value = valueTypeAdapter.read(in);
				bidiMap.put(key, value);
			}
			in.endObject();
			return bidiMap;
		}

	}

}
