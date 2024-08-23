package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
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
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class BiMapTypeAdapter<V>
		extends TypeAdapter<BiMap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends IBuilder2<String, V, BiMap<String, V>>> builderFactory;

	public static <V> TypeAdapter<BiMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<String, V, BiMap<String, V>>> builderFactory
	) {
		return new BiMapTypeAdapter<>(valueTypeAdapter, builderFactory)
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
		in.beginObject();
		final IBuilder2<? super String, ? super V, ? extends BiMap<String, V>> builder = builderFactory.get();
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
			extends AbstractTypeAdapterFactory<BiMap<String, V>> {

		private static final ITypeAdapterFactory<?> instance = new Factory<>(Factory::defaultBuilder);

		private final IBuilder2.ILookup<? super String, ? super V, ? extends BiMap<String, V>> builderLookup;

		public static <V> ITypeAdapterFactory<BiMap<String, V>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<BiMap<String, V>> castInstance = (ITypeAdapterFactory<BiMap<String, V>>) instance;
			return castInstance;
		}

		public static <V> ITypeAdapterFactory<BiMap<String, V>> getInstance(
				final IBuilder2.ILookup<? super String, ? super V, ? extends BiMap<String, V>> builderLookup
		) {
			return new Factory<>(builderLookup);
		}

		// TODO handle all known implementations
		public static <V> IBuilder2<String, V, BiMap<String, V>> defaultBuilder(final TypeToken<? super BiMap<String, V>> typeToken) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Class<? extends BiMap> rawType = (Class<? extends BiMap>) typeToken.getRawType();
			if ( rawType == HashBiMap.class ) {
				return IBuilder2.of(HashBiMap.create());
			}
			if ( ImmutableBiMap.class.isAssignableFrom(rawType) ) {
				return immutableBuilder();
			}
			return IBuilder2.of(HashBiMap.create());
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
			final IBuilder2.ILookup<String, V, BiMap<String, V>> castBuilderLookup = (IBuilder2.ILookup<String, V, BiMap<String, V>>) builderLookup;
			return BiMapTypeAdapter.getInstance(valueTypeAdapter, () -> castBuilderLookup.lookup(castTypeToken));
		}

		private static <V> IBuilder2<String, V, BiMap<String, V>> immutableBuilder() {
			return new IBuilder2<>() {
				private final ImmutableBiMap.Builder<String, V> builder = ImmutableBiMap.builder();

				@Override
				public void accept(final String k, final V v) {
					builder.put(k, v);
				}

				@Override
				public BiMap<String, V> build() {
					return builder.build();
				}
			};
		}

	}

}
