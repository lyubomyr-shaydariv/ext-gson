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
import org.apache.commons.collections4.MultiValuedMap;

/**
 * Represents a type adapter factory for {@link MultiValuedMap} from Apache Commons Collections 4.
 *
 * @param <V>
 * 		Value type
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiValuedMapTypeAdapterFactory<V>
		extends AbstractTypeAdapterFactory<MultiValuedMap<String, V>>
		implements ITypeAdapterFactory<MultiValuedMap<String, V>> {

	private final IInstanceFactory.IProvider<? extends MultiValuedMap<String, V>> newMultiValuedMapFactoryProvider;

	/**
	 * @param newMultiValuedMapFactoryProvider
	 * 		MultiValuedMap factory provider
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link MultiValuedMapTypeAdapterFactory} with a custom new {@link MultiValuedMap} factory.
	 */
	public static <V> ITypeAdapterFactory<MultiValuedMap<String, V>> getInstance(
			final IInstanceFactory.IProvider<? extends MultiValuedMap<String, V>> newMultiValuedMapFactoryProvider
	) {
		return new MultiValuedMapTypeAdapterFactory<>(newMultiValuedMapFactoryProvider);
	}

	@Override
	protected TypeAdapter<MultiValuedMap<String, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !MultiValuedMap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<MultiValuedMap<String, V>> castTypeToken = (TypeToken<MultiValuedMap<String, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<MultiValuedMap<String, V>> castNewMultiValuedMapFactoryProvider = (IInstanceFactory.IProvider<MultiValuedMap<String, V>>) newMultiValuedMapFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewMultiValuedMapFactoryProvider.provide(castTypeToken));
	}

	/**
	 * Represents a type adapter for {@link MultiValuedMap} from Apache Commons Collections 4.
	 *
	 * @param <V>
	 * 		Value type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<V>
			extends TypeAdapter<MultiValuedMap<String, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends MultiValuedMap<String, V>> newMultiValuedMapFactory;

		/**
		 * @param valueTypeAdapter
		 * 		MultiValuedMap value type adapter
		 * @param newMultiValuedMapFactory
		 * 		A {@link MultiValuedMap} factory to create instance used while deserialization
		 * @param <V>
		 * 		MultiValuedMap value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<MultiValuedMap<String, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final IInstanceFactory<? extends MultiValuedMap<String, V>> newMultiValuedMapFactory
		) {
			return new Adapter<>(valueTypeAdapter, newMultiValuedMapFactory)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final MultiValuedMap<String, V> multiValuedMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<String, V> e : multiValuedMap.entries() ) {
				final String key = e.getKey();
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public MultiValuedMap<String, V> read(final JsonReader in)
				throws IOException {
			final MultiValuedMap<String, V> multiValuedMap = newMultiValuedMapFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final String key = in.nextName();
				final V value = valueTypeAdapter.read(in);
				multiValuedMap.put(key, value);
			}
			in.endObject();
			return multiValuedMap;
		}

	}

}
