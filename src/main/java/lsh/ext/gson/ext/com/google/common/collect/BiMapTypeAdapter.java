package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class BiMapTypeAdapter<V>
		extends TypeAdapter<BiMap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends IBuilder2<String, V, BiMap<String, V>>> biMapBuilderFactory;

	public static <V> TypeAdapter<BiMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<String, V, BiMap<String, V>>> biMapBuilderFactory
	) {
		return new BiMapTypeAdapter<>(valueTypeAdapter, biMapBuilderFactory)
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
		final IBuilder2<? super String, ? super V, ? extends BiMap<String, V>> builder = biMapBuilderFactory.get();
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

		private final IBuilder2.IFactory<? super String, ? super V, ? extends BiMap<String, V>> biMapBuilderFactory;

		public static <V> ITypeAdapterFactory<BiMap<String, V>> getInstance() {
			return getInstance((IBuilder0.IFactory<BiMap<String, V>>) typeToken -> {
				throw new UnsupportedOperationException(String.valueOf(typeToken));
			});
		}

		public static <V> ITypeAdapterFactory<BiMap<String, V>> getInstance(
				final IBuilder0.IFactory<BiMap<String, V>> factoryFactory
		) {
			return getInstance((IBuilder2.IFactory<String, V, BiMap<String, V>>) typeToken -> builder(typeToken, factoryFactory));
		}

		public static <V> ITypeAdapterFactory<BiMap<String, V>> getInstance(
				final IBuilder2.IFactory<? super String, ? super V, ? extends BiMap<String, V>> builderFactory
		) {
			return new Factory<>(builderFactory);
		}

		public static <V> IBuilder2<String, V, BiMap<String, V>> builder(
				final TypeToken<BiMap<String, V>> typeToken,
				final IBuilder0.IFactory<BiMap<String, V>> factoryFactory
		) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Class<? extends BiMap> rawType = (Class<? extends BiMap<String, ?>>) typeToken.getRawType();
			if ( ImmutableBiMap.class.isAssignableFrom(rawType) ) {
				final ImmutableBiMap.Builder<String, V> builder = ImmutableBiMap.builder();
				return new IBuilder2<>() {
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
			@SuppressWarnings("LawOfDemeter")
			final BiMap<String, V> biMap = factoryFactory.create(typeToken)
					.build();
			return IBuilder2.of(biMap);
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
			final IBuilder2.IFactory<String, V, BiMap<String, V>> castNewBiMapBuilderFactory = (IBuilder2.IFactory<String, V, BiMap<String, V>>) biMapBuilderFactory;
			return BiMapTypeAdapter.getInstance(valueTypeAdapter, () -> castNewBiMapBuilderFactory.create(castTypeToken));
		}

	}

}
