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
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.BidiMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BidiMapTypeAdapter<V>
		extends TypeAdapter<BidiMap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final org.apache.commons.collections4.Factory<? extends IBuilder2<? super String, ? super V, ? extends BidiMap<String, V>>> bidiMapBuilderFactory;

	public static <V> TypeAdapter<BidiMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final org.apache.commons.collections4.Factory<? extends IBuilder2<? super String, ? super V, ? extends BidiMap<String, V>>> bidiMapBuilderFactory
	) {
		return new BidiMapTypeAdapter<>(valueTypeAdapter, bidiMapBuilderFactory)
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
		final IBuilder2<? super String, ? super V, ? extends BidiMap<String, V>> builder = bidiMapBuilderFactory.create();
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
			extends AbstractTypeAdapterFactory<BidiMap<String, V>> {

		private final IBuilder2.IFactory<? super String, ? super V, ? extends BidiMap<String, V>> bidiMapBuilderFactory;

		public static <V> ITypeAdapterFactory<BidiMap<String, V>> getInstance(
		) {
			return getInstance((IBuilder0.IFactory<BidiMap<String, V>>) typeToken -> {
				throw new UnsupportedOperationException(String.valueOf(typeToken));
			});
		}

		public static <V> ITypeAdapterFactory<BidiMap<String, V>> getInstance(
				final IBuilder0.IFactory<BidiMap<String, V>> factoryFactory
		) {
			return getInstance((IBuilder2.IFactory<String, V, BidiMap<String, V>>) typeToken -> builder(typeToken, factoryFactory));
		}

		public static <V> ITypeAdapterFactory<BidiMap<String, V>> getInstance(
				final IBuilder2.IFactory<? super String, ? super V, ? extends BidiMap<String, V>> bidiMapBuilderFactory
		) {
			return new Factory<>(bidiMapBuilderFactory);
		}

		public static <V> IBuilder2<String, V, BidiMap<String, V>> builder(
				final TypeToken<BidiMap<String, V>> typeToken,
				final IBuilder0.IFactory<BidiMap<String, V>> factoryFactory
		) {
			@SuppressWarnings("LawOfDemeter")
			final BidiMap<String, V> bidiMap = factoryFactory.create(typeToken)
					.build();
			return IBuilder2.of(bidiMap);
		}

		@Override
		protected TypeAdapter<BidiMap<String, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !BidiMap.class.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final TypeToken<BidiMap<String, V>> castTypeToken = (TypeToken<BidiMap<String, V>>) typeToken;
			@SuppressWarnings("unchecked")
			final IBuilder2.IFactory<String, V, BidiMap<String, V>> castMultiMapBuilderFactory = (IBuilder2.IFactory<String, V, BidiMap<String, V>>) bidiMapBuilderFactory;
			return BidiMapTypeAdapter.getInstance(valueTypeAdapter, () -> castMultiMapBuilderFactory.create(castTypeToken));
		}

	}

}
