package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
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
public final class MultimapTypeAdapter<K, V>
		extends TypeAdapter<Multimap<K, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final IFactory<? extends IBuilder2<? super K, ? super V, ? extends Multimap<K, V>>> builderFactory;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;

	public static <V> TypeAdapter<Multimap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder2<? super String, ? super V, ? extends Multimap<String, V>>> builderFactory
	) {
		return getInstance(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<Multimap<K, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder2<? super K, ? super V, ? extends Multimap<K, V>>> builderFactory,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return new MultimapTypeAdapter<>(valueTypeAdapter, builderFactory, encodeKey, decodeKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Multimap<K, V> multimap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : multimap.entries() ) {
			final K key = e.getKey();
			final V value = e.getValue();
			out.name(encodeKey.apply(key));
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public Multimap<K, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super K, ? super V, ? extends Multimap<K, V>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.accept(decodeKey.apply(key), value);
		}
		in.endObject();
		return builder.build();
	}

	public static final class Factory<K, V>
			extends AbstractRawClassHierarchyTypeAdapterFactory<Multimap<K, V>> {

		private static final ITypeAdapterFactory<?> instance = getInstance(Factory::defaultBuilderFactory, Function.identity(), Function.identity());

		private final IBuilder2.ILookup<? super K, ? super V, ? extends Multimap<K, V>> builderLookup;
		private final Function<? super K, String> encodeKey;
		private final Function<? super String, ? extends K> decodeKey;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Factory(
				final IBuilder2.ILookup<? super K, ? super V, ? extends Multimap<K, V>> builderLookup,
				final Function<? super K, String> encodeKey,
				final Function<? super String, ? extends K> decodeKey
		) {
			super((Class) Multimap.class);
			this.builderLookup = builderLookup;
			this.encodeKey = encodeKey;
			this.decodeKey = decodeKey;
		}

		public static <V> ITypeAdapterFactory<Multimap<String, V>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<Multimap<String, V>> castInstance = (ITypeAdapterFactory<Multimap<String, V>>) instance;
			return castInstance;
		}

		public static <K, V> ITypeAdapterFactory<Multimap<K, V>> getInstance(
				final IBuilder2.ILookup<? super K, ? super V, ? extends Multimap<K, V>> builderLookup,
				final Function<? super K, String> encodeKey,
				final Function<? super String, ? extends K> decodeKey
		) {
			return new Factory<>(builderLookup, encodeKey, decodeKey);
		}

		// TODO handle all known implementations
		public static <K, V> IFactory<IBuilder2<K, V, Multimap<K, V>>> defaultBuilderFactory(final TypeToken<? super Multimap<K, V>> typeToken) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Class<? extends Multimap> rawType = (Class<? extends Multimap>) typeToken.getRawType();
			if ( rawType == HashMultimap.class ) {
				return () -> builder(HashMultimap.create());
			}
			if ( rawType == LinkedHashMultimap.class ) {
				return () -> builder(LinkedHashMultimap.create());
			}
			if ( ImmutableMultimap.class.isAssignableFrom(rawType) ) {
				return Factory::immutableBuilder;
			}
			return () -> builder(HashMultimap.create());
		}

		@Override
		protected TypeAdapter<Multimap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<? super Multimap<K, V>> typeToken) {
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final IBuilder2.ILookup<K, V, Multimap<K, V>> castBuilderLookup = (IBuilder2.ILookup<K, V, Multimap<K, V>>) builderLookup;
			return MultimapTypeAdapter.getInstance(valueTypeAdapter, castBuilderLookup.lookup(typeToken), encodeKey, decodeKey);

		}

		private static <K, V, M extends Multimap<K, V>> IBuilder2<K, V, M> builder(final M biMap) {
			return new IBuilder2<>() {
				@Override
				public void accept(final K k, final V v) {
					biMap.put(k, v);
				}

				@Override
				public M build() {
					return biMap;
				}
			};
		}

		private static <K, V> IBuilder2<K, V, Multimap<K, V>> immutableBuilder() {
			return new IBuilder2<>() {
				private final ImmutableMultimap.Builder<K, V> builder = ImmutableMultimap.builder();

				@Override
				public void accept(final K k, final V v) {
					builder.put(k, v);
				}

				@Override
				public Multimap<K, V> build() {
					return builder.build();
				}
			};
		}

	}

}
