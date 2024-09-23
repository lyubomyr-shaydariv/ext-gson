package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public final class AbstractPolymorphicTypeAdapterTest {

	private static final Gson gson = Gsons.getNormalized();

	private static final Map<Class<? extends Any>, String> classToDiscriminator = Map.of(Abstract.class, "abstract", Concrete.class, "concrete");
	private static final Map<String, Class<? extends Any>> discriminatorToClass = HashBiMap.create(classToDiscriminator).inverse();

	private static final Map<Class<? extends Any>, TypeAdapter<? super Any>> classToTypeAdapter = discriminatorToClass.values()
			.stream()
			.map(klass -> {
				@SuppressWarnings("unchecked")
				final TypeAdapter<? super Any> adapter = (TypeAdapter<? super Any>) gson.getAdapter(klass);
				return new AbstractMap.SimpleImmutableEntry<>(klass, adapter);
			})
			.collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

	private static final Map<String, TypeAdapter<Any>> discriminatorToTypeAdapter = discriminatorToClass.entrySet()
			.stream()
			.map(e -> {
				final String discriminator = e.getKey();
				@SuppressWarnings("unchecked")
				final TypeAdapter<Any> adapter = (TypeAdapter<Any>) gson.getAdapter(e.getValue());
				return new AbstractMap.SimpleImmutableEntry<>(discriminator, adapter);
			})
			.collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

	private static final Abstract __abstract = new Abstract("very abstract");
	private static final Concrete concrete = new Concrete("very concrete", Math.E);

	private static final JsonElement abstractJsonElement = JsonObjects.of(
			"type", new JsonPrimitive("abstract"),
			"value", new JsonPrimitive("very abstract")
	);

	private static final JsonElement concreteJsonObject = JsonObjects.of(
			"type", new JsonPrimitive("concrete"),
			"value", new JsonPrimitive("very concrete"),
			"weight", new JsonPrimitive(Math.E)
	);

	private static final TypeAdapter<Any> bufferedUnit = AbstractPolymorphicTypeAdapter.JsonTree.getInstance(
			"type",
			classToDiscriminator::get,
			classToTypeAdapter::get,
			discriminatorToTypeAdapter::get
	);

	private static Stream<Arguments> testJsonTreeWrite() {
		return Stream.of(
				Arguments.of(abstractJsonElement, "type", "abstract", __abstract),
				Arguments.of(concreteJsonObject, "type", "concrete", concrete)
		);
	}

	@ParameterizedTest
	@MethodSource
	public void testJsonTreeWrite(final JsonElement expectedJsonElement, final String expectedProperty, final String expectedDiscriminator, final Any any) {
		final JsonElement actual = bufferedUnit.toJsonTree(any);
		// this is an order-unaware check, it must work in any case
		Assertions.assertEquals(expectedJsonElement, actual);
		// JSON objects are unordered, but let's assume the order is known
		final Map.Entry<String, JsonElement> firstEntry = ((JsonObject) actual).entrySet().iterator().next();
		Assertions.assertEquals(expectedProperty, firstEntry.getKey());
		Assertions.assertEquals(expectedDiscriminator, firstEntry.getValue().getAsString());
	}

	private static Stream<Arguments> testJsonTreeRead() {
		return Stream.of(
				Arguments.of(__abstract, abstractJsonElement),
				Arguments.of(concrete, concreteJsonObject)
		);
	}

	@ParameterizedTest
	@MethodSource
	public void testJsonTreeRead(final Any expected, final JsonElement jsonElement) {
		final Any actualAny = bufferedUnit.fromJsonTree(jsonElement);
		Assertions.assertEquals(expected, actualAny);
	}

	private static final TypeAdapter<Any> streamedUnit = AbstractPolymorphicTypeAdapter.Streamed.getInstance("type", classToDiscriminator::get, classToTypeAdapter::get, discriminatorToTypeAdapter::get);

	private static Stream<Arguments> testStreamedWrite() {
		return Stream.of(
				Arguments.of("{\"type\":\"abstract\",\"value\":\"very abstract\"}", "type", "abstract", __abstract),
				Arguments.of("{\"type\":\"concrete\",\"weight\":2.718281828459045,\"value\":\"very concrete\"}", "type", "concrete", concrete)
		);
	}

	@ParameterizedTest
	@MethodSource
	public void testStreamedWrite(final String expectedJson, final String expectedProperty, final String expectedDiscriminator, final Any any)
			throws IOException {
		final String json = streamedUnit.toJson(any);
		Assertions.assertEquals(expectedJson, json);
		// this is an order-aware check, it must work in any case
		@SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
		final JsonReader in = new JsonReader(new StringReader(json));
		in.beginObject();
		Assertions.assertEquals(expectedProperty, in.nextName());
		Assertions.assertEquals(expectedDiscriminator, in.nextString());
		Assertions.assertNotEquals(JsonToken.END_DOCUMENT, in.peek());
	}

	private static Stream<Arguments> testStreamedRead() {
		return Stream.of(
				Arguments.of(__abstract, "{\"type\":\"abstract\",\"value\":\"very abstract\"}"),
				Arguments.of(concrete, "{\"type\":\"concrete\",\"weight\":2.718281828459045,\"value\":\"very concrete\"}")
		);
	}

	@ParameterizedTest
	@MethodSource
	public void testStreamedRead(final Any expectedAny, final String json)
			throws IOException {
		final Any actualAny = streamedUnit.read(new JsonReader(new StringReader(json)));
		Assertions.assertEquals(expectedAny, actualAny);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@EqualsAndHashCode
	@ToString
	private abstract static class Any {

		private final String value;

	}

	@EqualsAndHashCode(callSuper = true)
	@ToString(callSuper = true)
	private static final class Abstract
			extends Any {

		private Abstract(final String value) {
			super(value);
		}

	}

	@EqualsAndHashCode(callSuper = true)
	@ToString(callSuper = true)
	private static final class Concrete
			extends Any {

		private final double weight;

		private Concrete(final String value, final double weight) {
			super(value);
			this.weight = weight;
		}

	}

}
