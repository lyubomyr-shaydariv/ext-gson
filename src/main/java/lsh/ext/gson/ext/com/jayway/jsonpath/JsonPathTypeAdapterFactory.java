package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonPathTypeAdapterFactory
		implements ITypeAdapterFactory<Object> {

	@Getter
	private static final ITypeAdapterFactory<?> instance = new JsonPathTypeAdapterFactory(JsonPathTypeAdapterFactory::buildDefaultConfiguration);

	@Getter
	private static final TypeAdapterFactory instanceWithGlobalDefaults = new JsonPathTypeAdapterFactory(gson -> Configuration.defaultConfiguration());

	private final Function<? super Gson, ? extends Configuration> configurationProvider;

	public static TypeAdapterFactory getInstance(final Function<? super Gson, ? extends Configuration> configurationProvider) {
		return new JsonPathTypeAdapterFactory(configurationProvider);
	}

	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		final TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, typeToken);
		@Nullable
		final Collection<FieldDatum> fieldData = findFields(typeToken.getRawType(), gson);
		if ( fieldData == null ) {
			return delegateAdapter;
		}
		return new Adapter<>(delegateAdapter, gson.getAdapter(JsonElement.class), fieldData, configurationProvider.apply(gson));
	}

	private static Configuration buildDefaultConfiguration(final Gson gson) {
		return Configuration.builder()
				.jsonProvider(new GsonJsonProvider(gson))
				.mappingProvider(new GsonMappingProvider(gson))
				.build();
	}

	@Nullable
	private static Collection<FieldDatum> findFields(final Class<?> clazz, final Gson gson) {
		@Nullable
		Collection<FieldDatum> collection = null;
		for ( final Field field : clazz.getDeclaredFields() ) {
			@Nullable
			final JsonPathExpression jsonPathExpression = field.getAnnotation(JsonPathExpression.class);
			if ( jsonPathExpression == null ) {
				continue;
			}
			if ( collection == null ) {
				collection = new ArrayList<>();
			}
			field.setAccessible(true);
			final TypeAdapter<?> typeAdapter = gson.getAdapter(TypeToken.get(field.getType()));
			collection.add(new FieldDatum(field, JsonPath.compile(jsonPathExpression.value()), typeAdapter));
		}
		return collection;
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class Adapter<T>
			extends TypeAdapter<T> {

		private final TypeAdapter<T> delegateAdapter;
		private final TypeAdapter<? extends JsonElement> jsonElementTypeAdapter;
		private final Collection<FieldDatum> fieldData;
		private final Configuration configuration;

		@Override
		public void write(final JsonWriter out, final T value)
				throws IOException {
			delegateAdapter.write(out, value);
		}

		@Override
		public T read(final JsonReader in)
				throws IOException {
			final JsonElement outerJsonElement = jsonElementTypeAdapter.read(in);
			final T value = delegateAdapter.fromJsonTree(outerJsonElement);
			for ( final FieldDatum fieldDatum : fieldData ) {
				try {
					final JsonElement innerJsonElement = fieldDatum.jsonPath.read(outerJsonElement, configuration);
					final Object innerValue = fieldDatum.typeAdapter.fromJsonTree(innerJsonElement);
					fieldDatum.field.set(value, innerValue);
				} catch ( final PathNotFoundException ignored ) {
					// do nothing
				} catch ( final IllegalAccessException ex ) {
					throw new IOException(ex);
				}
			}
			return value;
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class FieldDatum {

		private final Field field;
		private final JsonPath jsonPath;
		private final TypeAdapter<?> typeAdapter;

	}

}
