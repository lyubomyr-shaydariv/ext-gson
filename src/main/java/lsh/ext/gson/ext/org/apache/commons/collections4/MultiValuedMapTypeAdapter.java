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
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiValuedMapTypeAdapter<V>
		extends TypeAdapter<MultiValuedMap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final org.apache.commons.collections4.Factory<? extends IBuilder2<? super String, ? super V, ? extends MultiValuedMap<String, V>>> builderFactory;

	public static <V> TypeAdapter<MultiValuedMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final org.apache.commons.collections4.Factory<? extends IBuilder2<? super String, ? super V, ? extends MultiValuedMap<String, V>>> builderFactory
	) {
		return new MultiValuedMapTypeAdapter<>(valueTypeAdapter, builderFactory)
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
		in.beginObject();
		final IBuilder2<? super String, ? super V, ? extends MultiValuedMap<String, V>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.accept(key, value);
		}
		in.endObject();
		return builder.build();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<V>
			extends AbstractTypeAdapterFactory<MultiValuedMap<String, V>> {

		private static final ITypeAdapterFactory<?> instance = new Factory<>(Factory::defaultBuilder);

		private final IBuilder2.ILookup<? super String, ? super V, ? extends MultiValuedMap<String, V>> builderLookup;

		public static <V> ITypeAdapterFactory<MultiValuedMap<String, V>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<MultiValuedMap<String, V>> castInstance = (ITypeAdapterFactory<MultiValuedMap<String, V>>) instance;
			return castInstance;
		}

		public static <V> ITypeAdapterFactory<MultiValuedMap<String, V>> getInstance(
				final IBuilder2.ILookup<? super String, ? super V, ? extends MultiValuedMap<String, V>> builderFactory
		) {
			return new Factory<>(builderFactory);
		}

		// TODO handle all known implementations
		public static <V> IBuilder2<String, V, MultiValuedMap<String, V>> defaultBuilder(final TypeToken<? super MultiValuedMap<String, V>> typeToken) {
			@SuppressWarnings("unchecked")
			final Class<? extends MultiValuedMap<?, ?>> rawType = (Class<? extends MultiValuedMap<?, ?>>) typeToken.getRawType();
			if ( ArrayListValuedHashMap.class.isAssignableFrom(rawType) ) {
				return builder(new ArrayListValuedHashMap<>());
			}
			if ( HashSetValuedHashMap.class.isAssignableFrom(rawType) ) {
				return builder(new HashSetValuedHashMap<>());
			}
			return builder(new ArrayListValuedHashMap<>());
		}

		@Override
		@Nullable
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
			final IBuilder2.ILookup<String, V, MultiValuedMap<String, V>> castBuilderLookup = (IBuilder2.ILookup<String, V, MultiValuedMap<String, V>>) builderLookup;
			return MultiValuedMapTypeAdapter.getInstance(valueTypeAdapter, () -> castBuilderLookup.lookup(castTypeToken));
		}

		private static <V, M extends MultiValuedMap<String, V>> IBuilder2<String, V, M> builder(final M multiValuedMap) {
			return new IBuilder2<>() {
				@Override
				public void accept(final String k, final V v) {
					multiValuedMap.put(k, v);
				}

				@Override
				public M build() {
					return multiValuedMap;
				}
			};
		}

	}

}
