package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.TypeAdapters;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonPathTypeAdapter<T>
		extends TypeAdapter<T> {

	private static final TypeAdapter<JsonElement> jsonElementTypeAdapter = TypeAdapters.getJsonElementTypeAdapter();

	private final Configuration configuration;
	private final TypeAdapter<T> delegateAdapter;
	private final Item<?>[] items;

	private static <T> TypeAdapter<T> getInstance(
			final Configuration configuration,
			final TypeAdapter<T> delegateAdapter,
			final Item<?>[] items
	) {
		return new JsonPathTypeAdapter<>(configuration, delegateAdapter, items)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		delegateAdapter.write(out, value);
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		final JsonElement outerElement = jsonElementTypeAdapter.read(in);
		final T outerValue = delegateAdapter.fromJsonTree(outerElement);
		for ( final Item<?> item : items ) {
			try {
				final JsonElement foundSubElement = item.jsonPath.read(outerElement, configuration);
				final Object innerValue = item.typeAdapter.fromJsonTree(foundSubElement);
				item.accessor.assignFound(outerValue, innerValue);
			} catch ( final PathNotFoundException ignored ) {
				item.accessor.assignNotFound(outerValue);
			}
		}
		return outerValue;
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<T>
			implements ITypeAdapterFactory<T> {

		private final Function<? super Gson, ? extends Configuration> provideConfiguration;
		private final Function<TypeToken<?>, Collection<IAccessor<Object, Object>>> resolveAccessors;

		public static ITypeAdapterFactory<?> getInstance(
				final Function<TypeToken<?>, Collection<IAccessor<Object, Object>>> resolveAccessors
		) {
			return getInstance(Factory::defaultConfiguration, resolveAccessors);
		}

		public static ITypeAdapterFactory<?> getInstance(
				final Function<? super Gson, ? extends Configuration> provideConfiguration,
				final Function<TypeToken<?>, Collection<IAccessor<Object, Object>>> resolveAccessors
		) {
			return new Factory<>(provideConfiguration, resolveAccessors);
		}

		public static Configuration defaultConfiguration(final Gson gson) {
			return Configuration.builder()
					.jsonProvider(new GsonJsonProvider(gson))
					.mappingProvider(new GsonMappingProvider(gson))
					.build();
		}

		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			final Collection<IAccessor<Object, Object>> accessors = resolveAccessors.apply(typeToken);
			if ( accessors.isEmpty() ) {
				return null;
			}
			return JsonPathTypeAdapter.getInstance(
					provideConfiguration.apply(gson),
					gson.getDelegateAdapter(this, typeToken),
					accessors.stream()
							.map(accessor -> new Item<>(accessor, accessor.getJsonPath(), gson.getAdapter(TypeToken.get(accessor.getType()))))
							.toArray(Item[]::new)
			);
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class Item<T> {

		private final IAccessor<? super Object, ? super Object> accessor;
		private final JsonPath jsonPath;
		private final TypeAdapter<? super T> typeAdapter;

	}

}
