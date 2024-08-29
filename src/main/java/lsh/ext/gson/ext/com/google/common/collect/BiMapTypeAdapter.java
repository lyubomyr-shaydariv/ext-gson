package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractRawClassHierarchyTypeAdapterFactory;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class BiMapTypeAdapter<K, V>
		extends TypeAdapter<BiMap<K, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final IFactory<? extends IBuilder2<K, V, BiMap<K, V>>> builderFactory;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;

	public static <V> TypeAdapter<BiMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder2<String, V, BiMap<String, V>>> builderFactory
	) {
		return getInstance(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<BiMap<K, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder2<K, V, BiMap<K, V>>> builderFactory,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return new BiMapTypeAdapter<>(valueTypeAdapter, builderFactory, encodeKey, decodeKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final BiMap<K, V> biMap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : biMap.entrySet() ) {
			final String key = encodeKey.apply(e.getKey());
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public BiMap<K, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super K, ? super V, ? extends BiMap<K, V>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.accept(decodeKey.apply(key), value);
		}
		in.endObject();
		return builder.build();
	}

	public static final class Factory<K, V>
			extends AbstractRawClassHierarchyTypeAdapterFactory<BiMap<K, V>> {

		private static final ITypeAdapterFactory<?> instance = getInstance(Factory::defaultBuilderLookup, Function.identity(), Function.identity());

		private final IBuilder2.ILookup<? super K, ? super V, ? extends BiMap<K, V>> builderLookup;
		private final Function<? super K, String> encodeKey;
		private final Function<? super String, ? extends K> decodeKey;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Factory(
				final IBuilder2.ILookup<? super K, ? super V, ? extends BiMap<K, V>> builderLookup,
				final Function<? super K, String> encodeKey,
				final Function<? super String, ? extends K> decodeKey
		) {
			super((Class) BiMap.class);
			this.builderLookup = builderLookup;
			this.encodeKey = encodeKey;
			this.decodeKey = decodeKey;
		}

		public static <V> ITypeAdapterFactory<BiMap<String, V>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<BiMap<String, V>> castInstance = (ITypeAdapterFactory<BiMap<String, V>>) instance;
			return castInstance;
		}

		public static <K, V> ITypeAdapterFactory<BiMap<K, V>> getInstance(
				final IBuilder2.ILookup<? super K, ? super V, ? extends BiMap<K, V>> builderLookup,
				final Function<? super K, String> encodeKey,
				final Function<? super String, ? extends K> decodeKey
		) {
			return new Factory<>(builderLookup, encodeKey, decodeKey);
		}

		// TODO handle all known implementations
		public static <K, V> IFactory<IBuilder2<K, V, BiMap<K, V>>> defaultBuilderLookup(final TypeToken<? super BiMap<K, V>> typeToken) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Class<? extends BiMap> rawType = (Class<? extends BiMap>) typeToken.getRawType();
			if ( rawType == HashBiMap.class ) {
				return () -> IBuilder2.of(HashBiMap.create());
			}
			if ( ImmutableBiMap.class.isAssignableFrom(rawType) ) {
				return Factory::immutableBuilder;
			}
			return () -> IBuilder2.of(HashBiMap.create());
		}

		@Override
		protected TypeAdapter<BiMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<? super BiMap<K, V>> typeToken) {
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final IBuilder2.ILookup<K, V, BiMap<K, V>> castBuilderLookup = (IBuilder2.ILookup<K, V, BiMap<K, V>>) builderLookup;
			return BiMapTypeAdapter.getInstance(valueTypeAdapter, castBuilderLookup.lookup(typeToken), encodeKey, decodeKey);
		}

		private static <K, V> IBuilder2<K, V, BiMap<K, V>> immutableBuilder() {
			return new IBuilder2<>() {
				private final ImmutableBiMap.Builder<K, V> builder = ImmutableBiMap.builder();

				@Override
				public void accept(final K k, final V v) {
					builder.put(k, v);
				}

				@Override
				public BiMap<K, V> build() {
					return builder.build();
				}
			};
		}

	}

}
