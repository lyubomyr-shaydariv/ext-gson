package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractHierarchyTypeAdapterFactory;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiValuedMapTypeAdapter<K, V>
		extends TypeAdapter<MultiValuedMap<K, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final IFactory<? extends IBuilder2<? super K, ? super V, ? extends MultiValuedMap<K, V>>> builderFactory;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;

	public static <V> TypeAdapter<MultiValuedMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder2<? super String, ? super V, ? extends MultiValuedMap<String, V>>> builderFactory
	) {
		return getInstance(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<MultiValuedMap<K, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder2<? super K, ? super V, ? extends MultiValuedMap<K, V>>> builderFactory,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return new MultiValuedMapTypeAdapter<>(valueTypeAdapter, builderFactory, encodeKey, decodeKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final MultiValuedMap<K, V> multiValuedMap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : multiValuedMap.entries() ) {
			final String key = encodeKey.apply(e.getKey());
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public MultiValuedMap<K, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super K, ? super V, ? extends MultiValuedMap<K, V>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.accept(decodeKey.apply(key), value);
		}
		in.endObject();
		return builder.build();
	}

	public static final class Factory<K, V>
			extends AbstractHierarchyTypeAdapterFactory<MultiValuedMap<K, V>> {

		private static final ITypeAdapterFactory<?> instance = getInstance(Factory::defaultBuilderFactory, Function.identity(), Function.identity());

		private final IBuilder2.ILookup<? super K, ? super V, ? extends MultiValuedMap<K, V>> builderLookup;
		private final Function<? super K, String> encodeKey;
		private final Function<? super String, ? extends K> decodeKey;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Factory(
				final IBuilder2.ILookup<? super K, ? super V, ? extends MultiValuedMap<K, V>> builderLookup,
				final Function<? super K, String> encodeKey,
				final Function<? super String, ? extends K> decodeKey
		) {
			super((Class) MultiValuedMap.class);
			this.builderLookup = builderLookup;
			this.encodeKey = encodeKey;
			this.decodeKey = decodeKey;
		}

		public static <V> ITypeAdapterFactory<MultiValuedMap<String, V>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<MultiValuedMap<String, V>> castInstance = (ITypeAdapterFactory<MultiValuedMap<String, V>>) instance;
			return castInstance;
		}

		public static <K, V> ITypeAdapterFactory<MultiValuedMap<K, V>> getInstance(
				final IBuilder2.ILookup<? super K, ? super V, ? extends MultiValuedMap<K, V>> builderLookup,
				final Function<? super K, String> encodeKey,
				final Function<? super String, ? extends K> decodeKey
		) {
			return new Factory<>(builderLookup, encodeKey, decodeKey);
		}

		// TODO handle all known implementations
		public static <V> IFactory<IBuilder2<String, V, MultiValuedMap<String, V>>> defaultBuilderFactory(final TypeToken<? super MultiValuedMap<String, V>> typeToken) {
			@SuppressWarnings("unchecked")
			final Class<? extends MultiValuedMap<?, ?>> rawType = (Class<? extends MultiValuedMap<?, ?>>) typeToken.getRawType();
			if ( ArrayListValuedHashMap.class.isAssignableFrom(rawType) ) {
				return () -> builder(new ArrayListValuedHashMap<>());
			}
			if ( HashSetValuedHashMap.class.isAssignableFrom(rawType) ) {
				return () -> builder(new HashSetValuedHashMap<>());
			}
			return () -> builder(new ArrayListValuedHashMap<>());
		}

		@Override
		protected TypeAdapter<MultiValuedMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<? super MultiValuedMap<K, V>> typeToken) {
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final IBuilder2.ILookup<K, V, MultiValuedMap<K, V>> castBuilderLookup = (IBuilder2.ILookup<K, V, MultiValuedMap<K, V>>) builderLookup;
			return MultiValuedMapTypeAdapter.getInstance(valueTypeAdapter, castBuilderLookup.lookup(typeToken), encodeKey, decodeKey);
		}

		private static <K, V, M extends MultiValuedMap<K, V>> IBuilder2<K, V, M> builder(final M multiValuedMap) {
			return new IBuilder2<>() {
				@Override
				public void accept(final K k, final V v) {
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
