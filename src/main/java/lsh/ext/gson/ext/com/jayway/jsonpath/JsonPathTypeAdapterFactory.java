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

/**
 * <p>
 * Represents a type adapter factory that can deserialize object fields using {@link JsonPathExpression}-annotations in JSON mappings. Example of use, the
 * following JSON:
 * </p>
 *
 * <pre>
 *     {
 *         "l1": {
 *             "l2": {
 *                 "l3": {
 *                     "foo": "Foo!"
 *                 }
 *             }
 *         }
 *     }
 * </pre>
 *
 * <p>
 * can be mapped to the following mapping using this type adapter factory:
 * </p>
 *
 * <pre>
 *     public final class Foo {
 *
 *        {@code @}JsonPathExpression("$.l1.l2.l3.foo")
 *         public final String fooRef = null;
 *
 *     }
 * </pre>
 *
 * <p>
 * So, the following code outputs {@code Foo!} to stdout:
 * </p>
 *
 * <pre>
 *     final Gson gson = new GsonBuilder()
 *         .registerTypeAdapterFactory(getJsonPathTypeAdapterFactory())
 *         .create();
 *     final Foo foo = gson.fromJson("{\"l1\":{\"l2\":{\"l3\":{\"foo\":\"Foo!\"}}}}", Foo.class);
 *     System.out.println(foo.fooRef);
 * </pre>
 *
 * <p>
 * JSON path expressions that point to not existing paths are ignored.
 * </p>
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonPathTypeAdapterFactory
		implements ITypeAdapterFactory<Object> {

	/**
	 * A {@link JsonPathTypeAdapterFactory} instance that is configured with the predefined JsonPath configuration. The default JsonPath configuration
	 * used for this instance uses {@link Configuration.Defaults} with an internally defined {@link GsonJsonProvider} bound to the {@link Gson} instance
	 * provided in {@link #create(Gson, TypeToken)}, an internally defined {@link GsonMappingProvider} bound to the {@link Gson} instance provided in {@link
	 * #create(Gson, TypeToken)}, and empty options set.
	 */
	@Getter
	private static final ITypeAdapterFactory<?> instance = new JsonPathTypeAdapterFactory(JsonPathTypeAdapterFactory::buildDefaultConfiguration);

	/**
	 * Example of use:
	 *
	 * <pre>
	 * private static void configureJsonPathGlobally(final JsonProvider gsonJsonProvider, final MappingProvider gsonMappingProvider) {
	 *     Configuration.setDefaults(new Configuration.Defaults() {
	 *        {@code @}Override
	 *         public JsonProvider jsonProvider() {
	 *             return gsonJsonProvider;
	 *         }
	 *
	 *        {@code @}Override
	 *         public MappingProvider mappingProvider() {
	 *             return gsonMappingProvider;
	 *         }
	 *
	 *        {@code @}Override
	 *         public Set&lt;Option&gt; options() {
	 *             return EnumSet.noneOf(Option.class);
	 *         }
	 *     });
	 * }
	 * </pre>
	 */
	@Getter
	private static final TypeAdapterFactory instanceWithGlobalDefaults = new JsonPathTypeAdapterFactory(gson -> Configuration.defaultConfiguration());

	private final Function<? super Gson, ? extends Configuration> configurationProvider;

	/**
	 * @param configurationProvider
	 * 		A function (strategy) to return a JsonPath {@link Configuration}.
	 *
	 * @return A {@link JsonPathTypeAdapterFactory} instance that can be configured with the given strategy.
	 */
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
