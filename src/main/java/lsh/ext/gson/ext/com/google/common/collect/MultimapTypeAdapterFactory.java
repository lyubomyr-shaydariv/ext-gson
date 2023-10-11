package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

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

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultimapTypeAdapterFactory<V>
		extends AbstractTypeAdapterFactory<Multimap<String, V>>
		implements ITypeAdapterFactory<Multimap<String, V>> {

	private final IInstanceFactory.IProvider<? extends Multimap<String, V>> newMultimapFactoryProvider;

	public static <V> ITypeAdapterFactory<Multimap<String, V>> getInstance(
			final IInstanceFactory.IProvider<? extends Multimap<String, V>> newMultimapFactoryProvider
	) {
		return new MultimapTypeAdapterFactory<>(newMultimapFactoryProvider);
	}

	@Override
	protected TypeAdapter<Multimap<String, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !Multimap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<Multimap<String, V>> castTypeToken = (TypeToken<Multimap<String, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<Multimap<String, V>> castNewMultimapFactoryProvider = (IInstanceFactory.IProvider<Multimap<String, V>>) newMultimapFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewMultimapFactoryProvider.provide(castTypeToken));
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<V>
			extends TypeAdapter<Multimap<String, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends Multimap<String, V>> newMultimapFactory;

		public static <V> TypeAdapter<Multimap<String, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final IInstanceFactory<? extends Multimap<String, V>> newMultimapFactory
		) {
			return new Adapter<>(valueTypeAdapter, newMultimapFactory)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final Multimap<String, V> multimap)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<String, V> e : multimap.entries() ) {
				final String key = e.getKey();
				final V value = e.getValue();
				out.name(key);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}

		@Override
		public Multimap<String, V> read(final JsonReader in)
				throws IOException {
			final Multimap<String, V> multimap = newMultimapFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final String key = in.nextName();
				final V value = valueTypeAdapter.read(in);
				multimap.put(key, value);
			}
			in.endObject();
			return multimap;
		}

	}

}
