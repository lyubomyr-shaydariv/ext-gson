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

@SuppressWarnings("deprecation")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiMapTypeAdapterFactory<V>
		extends AbstractTypeAdapterFactory<MultiMap<String, V>>
		implements ITypeAdapterFactory<MultiMap<String, V>> {

	private final IInstanceFactory.IProvider<? extends MultiMap<String, V>> newMultiMapFactoryProvider;

	public static <V> ITypeAdapterFactory<MultiMap<String, V>> getInstance(
			final IInstanceFactory.IProvider<? extends MultiMap<String, V>> newMultiMapFactoryProvider
	) {
		return new MultiMapTypeAdapterFactory<>(newMultiMapFactoryProvider);
	}

	@Override
	protected TypeAdapter<MultiMap<String, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !MultiMap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<MultiMap<String, V>> castTypeToken = (TypeToken<MultiMap<String, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<MultiMap<String, V>> castNewMultiMapFactoryProvider = (IInstanceFactory.IProvider<MultiMap<String, V>>) newMultiMapFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewMultiMapFactoryProvider.provide(castTypeToken));
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<V>
			extends TypeAdapter<MultiMap<String, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends MultiMap<String, V>> newMultiMapFactory;

		public static <V> TypeAdapter<MultiMap<String, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final IInstanceFactory<? extends MultiMap<String, V>> newMultiMapFactory
		) {
			return new Adapter<>(valueTypeAdapter, newMultiMapFactory)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final MultiMap<String, V> multiMap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<String, Object> e : multiMap.entrySet() ) {
				final String key = e.getKey();
				@SuppressWarnings("unchecked")
				final V value = (V) e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public MultiMap<String, V> read(final JsonReader in)
				throws IOException {
			final MultiMap<String, V> multiMap = newMultiMapFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final String key = in.nextName();
				final V value = valueTypeAdapter.read(in);
				multiMap.put(key, value);
			}
			in.endObject();
			return multiMap;
		}

	}

}
