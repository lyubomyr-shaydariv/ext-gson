package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
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
public final class MultimapTypeAdapter<V>
		extends TypeAdapter<Multimap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends IBuilder2<? super String, ? super V, ? extends Multimap<String, V>>> multimapBuilderFactory;

	public static <V> TypeAdapter<Multimap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super V, ? extends Multimap<String, V>>> multimapBuilderFactory
	) {
		return new MultimapTypeAdapter<>(valueTypeAdapter, multimapBuilderFactory)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Multimap<String, V> multimap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<String, V> e : multimap.entries() ) {
			final String key = e.getKey();
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public Multimap<String, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super String, ? super V, ? extends Multimap<String, V>> builder = multimapBuilderFactory.get();
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
			extends AbstractTypeAdapterFactory<Multimap<String, V>> {

		private final IBuilder2.IFactory<? super String, ? super V, ? extends Multimap<String, V>> builderFactory;

		public static <V> ITypeAdapterFactory<Multimap<String, V>> getInstance(
				final IBuilder2.IFactory<? super String, ? super V, ? extends Multimap<String, V>> builderFactory
		) {
			return new Factory<>(builderFactory);
		}

		public static <V> ITypeAdapterFactory<Multimap<String, V>> getDefaultBuilderInstance(
				final IBuilder0.IFactory<? extends Multimap<String, V>> builderFactory
		) {
			return getInstance(typeToken -> defaultBuilder(typeToken, builderFactory));
		}

		public static <V> IBuilder2<String, V, Multimap<String, V>> defaultBuilder(
				final TypeToken<? super Multimap<String, V>> typeToken,
				final IBuilder0.IFactory<? extends Multimap<String, V>> builderFactory
		) {
			@SuppressWarnings("unchecked")
			final Class<? extends Multimap<?, ?>> rawType = (Class<? extends Multimap<?, ?>>) typeToken.getRawType();
			if ( ImmutableMultimap.class.isAssignableFrom(rawType) ) {
				return new IBuilder2<>() {
					private final ImmutableMultimap.Builder<String, V> builder = ImmutableMultimap.builder();

					@Override
					public void accept(final String k, final V v) {
						builder.put(k, v);
					}

					@Override
					public Multimap<String, V> build() {
						return builder.build();
					}
				};
			}
			@SuppressWarnings("LawOfDemeter")
			final Multimap<String, V> multimap = builderFactory.create(typeToken)
					.build();
			return new IBuilder2<>() {
				@Override
				public void accept(final String k, final V v) {
					multimap.put(k, v);
				}

				@Override
				public Multimap<String, V> build() {
					return multimap;
				}
			};
		}

		@Override
		@Nullable
		protected TypeAdapter<Multimap<String, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !Multimap.class.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 1);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final TypeToken<Multimap<String, V>> castTypeToken = (TypeToken<Multimap<String, V>>) typeToken;
			@SuppressWarnings("unchecked")
			final IBuilder2.IFactory<String, V, Multimap<String, V>> castMultimapBuilderFactory = (IBuilder2.IFactory<String, V, Multimap<String, V>>) builderFactory;
			return MultimapTypeAdapter.getInstance(valueTypeAdapter, () -> castMultimapBuilderFactory.create(castTypeToken));
		}

	}

}
