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
import org.apache.commons.collections4.MultiValuedMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiValuedMapTypeAdapter<V>
		extends TypeAdapter<MultiValuedMap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final org.apache.commons.collections4.Factory<? extends IBuilder2<? super String, ? super V, ? extends MultiValuedMap<String, V>>> multiValuedMapBuilderFactory;

	public static <V> TypeAdapter<MultiValuedMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final org.apache.commons.collections4.Factory<? extends IBuilder2<? super String, ? super V, ? extends MultiValuedMap<String, V>>> multiValuedMapBuilderFactory
	) {
		return new MultiValuedMapTypeAdapter<>(valueTypeAdapter, multiValuedMapBuilderFactory)
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
		final IBuilder2<? super String, ? super V, ? extends MultiValuedMap<String, V>> builder = multiValuedMapBuilderFactory.create();
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

		private final IBuilder2.IFactory<? super String, ? super V, ? extends MultiValuedMap<String, V>> multiValuedMapBuilderFactory;

		public static <V> ITypeAdapterFactory<MultiValuedMap<String, V>> getInstance() {
			return getInstance((IFactory0.IFactory<MultiValuedMap<String, V>>) typeToken -> {
				throw new UnsupportedOperationException(String.valueOf(typeToken));
			});
		}

		public static <V> ITypeAdapterFactory<MultiValuedMap<String, V>> getInstance(
				final IFactory0.IFactory<MultiValuedMap<String, V>> factoryFactory
		) {
			return getInstance((IBuilder2.IFactory<String, V, MultiValuedMap<String, V>>) typeToken -> builder(typeToken, factoryFactory));
		}

		public static <V> ITypeAdapterFactory<MultiValuedMap<String, V>> getInstance(
				final IBuilder2.IFactory<? super String, ? super V, ? extends MultiValuedMap<String, V>> multiValuedMapBuilderFactory
		) {
			return new Factory<>(multiValuedMapBuilderFactory);
		}

		public static <V> IBuilder2<String, V, MultiValuedMap<String, V>> builder(
				final TypeToken<MultiValuedMap<String, V>> typeToken,
				final IFactory0.IFactory<MultiValuedMap<String, V>> factoryFactory
		) {
			@SuppressWarnings("LawOfDemeter")
			final MultiValuedMap<String, V> multiValuedMap = factoryFactory.create(typeToken)
					.create();
			return new IBuilder2<>() {
				@Override
				public void accept(final String k, final V v) {
					multiValuedMap.put(k, v);
				}

				@Override
				public MultiValuedMap<String, V> build() {
					return multiValuedMap;
				}
			};
		}

		@Override
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
			final IBuilder2.IFactory<String, V, MultiValuedMap<String, V>> castMultiValuedMapBuilderFactory = (IBuilder2.IFactory<String, V, MultiValuedMap<String, V>>) multiValuedMapBuilderFactory;
			return MultiValuedMapTypeAdapter.getInstance(valueTypeAdapter, () -> castMultiValuedMapBuilderFactory.create(castTypeToken));
		}

	}

}
