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
import lsh.ext.gson.IFactory0;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.MultiMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("deprecation")
public final class MultiMapTypeAdapter<V>
		extends TypeAdapter<MultiMap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final org.apache.commons.collections4.Factory<? extends IBuilder2<? super String, ? super V, ? extends MultiMap<String, V>>> multiMapBuilderFactory;

	public static <V> TypeAdapter<MultiMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final org.apache.commons.collections4.Factory<? extends IBuilder2<? super String, ? super V, ? extends MultiMap<String, V>>> multiMapBuilderFactory
	) {
		return new MultiMapTypeAdapter<>(valueTypeAdapter, multiMapBuilderFactory)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final MultiMap<String, V> multiMap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<String, Object> e : multiMap.entrySet() ) {
			final String key = e.getKey();
			@SuppressWarnings("unchecked")
			final V value = (V) e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public MultiMap<String, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super String, ? super V, ? extends MultiMap<String, V>> builder = multiMapBuilderFactory.create();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.modify(key, value);
		}
		in.endObject();
		return builder.build();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<V>
			extends AbstractTypeAdapterFactory<MultiMap<String, V>> {

		private final IBuilder2.IFactory<? super String, ? super V, ? extends MultiMap<String, V>> multiMapBuilderFactory;

		public static <V> ITypeAdapterFactory<MultiMap<String, V>> getInstance() {
			return getInstance((IFactory0.IFactory<MultiMap<String, V>>) typeToken -> {
				throw new UnsupportedOperationException(String.valueOf(typeToken));
			});
		}

		public static <V> ITypeAdapterFactory<MultiMap<String, V>> getInstance(
				final IFactory0.IFactory<MultiMap<String, V>> factoryFactory
		) {
			return getInstance((IBuilder2.IFactory<String, V, MultiMap<String, V>>) typeToken -> builder(typeToken, factoryFactory));
		}

		public static <V> ITypeAdapterFactory<MultiMap<String, V>> getInstance(
				final IBuilder2.IFactory<? super String, ? super V, ? extends MultiMap<String, V>> multiMapBuilderFactory
		) {
			return new Factory<>(multiMapBuilderFactory);
		}

		public static <V> IBuilder2<String, V, MultiMap<String, V>> builder(
				final TypeToken<MultiMap<String, V>> typeToken,
				final IFactory0.IFactory<MultiMap<String, V>> factoryFactory
		) {
			@SuppressWarnings("LawOfDemeter")
			final MultiMap<String, V> multiMap = factoryFactory.create(typeToken)
					.create();
			return new IBuilder2<>() {
				@Override
				public void modify(final String k, final V v) {
					multiMap.put(k, v);
				}

				@Override
				public MultiMap<String, V> build() {
					return multiMap;
				}
			};
		}

		@Override
		protected TypeAdapter<MultiMap<String, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !MultiMap.class.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final TypeToken<MultiMap<String, V>> castTypeToken = (TypeToken<MultiMap<String, V>>) typeToken;
			@SuppressWarnings("unchecked")
			final IBuilder2.IFactory<String, V, MultiMap<String, V>> castMultiMapBuilderFactory = (IBuilder2.IFactory<String, V, MultiMap<String, V>>) multiMapBuilderFactory;
			return MultiMapTypeAdapter.getInstance(valueTypeAdapter, () -> castMultiMapBuilderFactory.create(castTypeToken));
		}

	}

}
