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
import lsh.ext.gson.AbstractHierarchyTypeAdapterFactory;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BidiMapTypeAdapter<V>
		extends TypeAdapter<BidiMap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final IFactory<? extends IBuilder2<? super String, ? super V, ? extends BidiMap<String, V>>> builderFactory;

	public static <V> TypeAdapter<BidiMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder2<? super String, ? super V, ? extends BidiMap<String, V>>> builderFactory
	) {
		return new BidiMapTypeAdapter<>(valueTypeAdapter, builderFactory)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final BidiMap<String, V> bidiMap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<String, V> e : bidiMap.entrySet() ) {
			final String key = e.getKey();
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public BidiMap<String, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super String, ? super V, ? extends BidiMap<String, V>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.accept(key, value);
		}
		in.endObject();
		return builder.build();
	}

	public static final class Factory<V>
			extends AbstractHierarchyTypeAdapterFactory<BidiMap<String, V>> {

		private static final ITypeAdapterFactory<?> instance = new Factory<>(Factory::defaultBuilderFactory);

		private final IBuilder2.ILookup<? super String, ? super V, ? extends BidiMap<String, V>> builderLookup;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Factory(final IBuilder2.ILookup<? super String, ? super V, ? extends BidiMap<String, V>> builderLookup) {
			super((Class) BidiMap.class);
			this.builderLookup = builderLookup;
		}

		public static <V> ITypeAdapterFactory<BidiMap<String, V>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<BidiMap<String, V>> castInstance = (ITypeAdapterFactory<BidiMap<String, V>>) instance;
			return castInstance;
		}

		public static <V> ITypeAdapterFactory<BidiMap<String, V>> getInstance(
				final IBuilder2.ILookup<? super String, ? super V, ? extends BidiMap<String, V>> builderLookup
		) {
			return new Factory<>(builderLookup);
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
		protected TypeAdapter<BidiMap<String, V>> createTypeAdapter(final Gson gson, final TypeToken<? super BidiMap<String, V>> typeToken) {
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final IBuilder2.ILookup<String, V, BidiMap<String, V>> castBuilderLookup = (IBuilder2.ILookup<String, V, BidiMap<String, V>>) builderLookup;
			return BidiMapTypeAdapter.getInstance(valueTypeAdapter, castBuilderLookup.lookup(typeToken));
		}

	}

}
