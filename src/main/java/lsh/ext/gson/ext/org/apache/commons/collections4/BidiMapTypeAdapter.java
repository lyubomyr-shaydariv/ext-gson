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
import lsh.ext.gson.AbstractRawClassHierarchyTypeAdapterFactory;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BidiMapTypeAdapter<K, V>
		extends TypeAdapter<BidiMap<K, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final IFactory<? extends IBuilder2<? super K, ? super V, ? extends BidiMap<K, V>>> builderFactory;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;

	public static <V> TypeAdapter<BidiMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder2<? super String, ? super V, ? extends BidiMap<String, V>>> builderFactory
	) {
		return getInstance(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<BidiMap<K, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder2<? super K, ? super V, ? extends BidiMap<K, V>>> builderFactory,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return new BidiMapTypeAdapter<>(valueTypeAdapter, builderFactory, encodeKey, decodeKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final BidiMap<K, V> bidiMap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : bidiMap.entrySet() ) {
			final String key = encodeKey.apply(e.getKey());
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public BidiMap<K, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super K, ? super V, ? extends BidiMap<K, V>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.accept(decodeKey.apply(key), value);
		}
		in.endObject();
		return builder.build();
	}

	public static final class Factory<K, V>
			extends AbstractRawClassHierarchyTypeAdapterFactory<BidiMap<K, V>> {

		private static final ITypeAdapterFactory<?> instance = getInstance(Factory::defaultBuilderFactory, Function.identity(), Function.identity());

		private final IBuilder2.ILookup<? super K, ? super V, ? extends BidiMap<K, V>> builderLookup;
		private final Function<? super K, String> encodeKey;
		private final Function<? super String, ? extends K> decodeKey;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Factory(
				final IBuilder2.ILookup<? super K, ? super V, ? extends BidiMap<K, V>> builderLookup,
				final Function<? super K, String> encodeKey,
				final Function<? super String, ? extends K> decodeKey
		) {
			super((Class) BidiMap.class);
			this.builderLookup = builderLookup;
			this.encodeKey = encodeKey;
			this.decodeKey = decodeKey;
		}

		public static <V> ITypeAdapterFactory<BidiMap<String, V>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<BidiMap<String, V>> castInstance = (ITypeAdapterFactory<BidiMap<String, V>>) instance;
			return castInstance;
		}

		public static <K, V> ITypeAdapterFactory<BidiMap<K, V>> getInstance(
				final IBuilder2.ILookup<? super K, ? super V, ? extends BidiMap<K, V>> builderLookup,
				final Function<? super K, String> encodeKey,
				final Function<? super String, ? extends K> decodeKey
		) {
			return new Factory<>(builderLookup, encodeKey, decodeKey);
		}

		// TODO handle all known implementations
		public static <V> IFactory<IBuilder2<String, V, BidiMap<String, V>>> defaultBuilderFactory(final TypeToken<? super BidiMap<String, V>> typeToken) {
			@SuppressWarnings("unchecked")
			final Class<? extends BidiMap<?, ?>> rawType = (Class<? extends BidiMap<?, ?>>) typeToken.getRawType();
			if ( DualHashBidiMap.class.isAssignableFrom(rawType) ) {
				return () -> IBuilder2.of(new DualHashBidiMap<>());
			}
			if ( DualLinkedHashBidiMap.class.isAssignableFrom(rawType) ) {
				return () -> IBuilder2.of(new DualLinkedHashBidiMap<>());
			}
			return () -> IBuilder2.of(new DualHashBidiMap<>());
		}

		@Override
		protected TypeAdapter<BidiMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<? super BidiMap<K, V>> typeToken) {
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final IBuilder2.ILookup<K, V, BidiMap<K, V>> castBuilderLookup = (IBuilder2.ILookup<K, V, BidiMap<K, V>>) builderLookup;
			return BidiMapTypeAdapter.getInstance(valueTypeAdapter, castBuilderLookup.lookup(typeToken), encodeKey, decodeKey);
		}

	}

}
