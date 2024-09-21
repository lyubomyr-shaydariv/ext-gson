package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class AbstractEncodingTypeAdapterFactoryTest {

	private static final Gson gson = Gsons.getNormalized();

	@Test
	public void testRead() {
		Assertions.assertEquals(new Wrapper(List.of(1, 2, 3)), gson.fromJson("{\"value\":\"[1,2,3]\"}", Wrapper.class));
	}

	@Test
	public void testWrite() {
		Assertions.assertEquals("{\"value\":\"[1,3]\"}", gson.toJson(new Wrapper(List.of(1, 3)), Wrapper.class));
	}

	private record Wrapper(
			@JsonAdapter(EncodedJsonStringTypeAdapter.Factory.class) List<Integer> value
	) {
	}

	private static final Function<Object, Class<?>> forClass = Object::getClass;
	private static final Function<String, Class<?>> forUnsafeClass = typeName -> {
		try {
			return Class.forName(typeName);
		} catch ( final ClassNotFoundException ex ) {
			throw new RuntimeException(ex);
		}
	};
	private static final Predicate<String> weakGuard = name -> true;

	@Test
	public void testJsonAsTypedValueReadWrite()
			throws IOException {
		final TypeAdapter<Integer> typeAdapter = AbstractEncodingTypeAdapter.TypedValueAsJsonObject.getInstance(gson, "type", "value", forClass, Class::getTypeName, forUnsafeClass, weakGuard);
		final Writer jsonWriter = new StringWriter();
		final int before = 2;
		typeAdapter.write(new JsonWriter(jsonWriter), before);
		final String json = jsonWriter.toString();
		final int after = typeAdapter.read(new JsonReader(new StringReader(json)));
		Assertions.assertEquals(before, after);
	}

	@Test
	public void testJsonAsTypedValueReadWriteGuardPasses() {
		@SuppressWarnings("unchecked")
		final Predicate<String> guardMock = Mockito.mock(Predicate.class);
		Mockito.doAnswer(invocationOnMock -> primitiveTypeGuard.test(invocationOnMock.getArgument(0, String.class)))
				.when(guardMock)
				.test(ArgumentMatchers.anyString());
		final TypeAdapter<Integer> typeAdapter = AbstractEncodingTypeAdapter.TypedValueAsJsonObject.getInstance(gson, "t", "v", forClass, Class::getTypeName, primitiveTypeDecoder, guardMock);
		final JsonElement legalJsonTree = JsonObjects.of(
				"t", JsonPrimitives.orNullable("int"),
				"v", JsonPrimitives.orNullable(1000)
		);
		final Integer actualPayload = Assertions.assertDoesNotThrow(() -> typeAdapter.fromJsonTree(legalJsonTree));
		Assertions.assertEquals(1000, actualPayload);
		Mockito.verify(guardMock)
				.test(ArgumentMatchers.eq("int"));
		Mockito.verifyNoMoreInteractions(guardMock);
	}

	@Test
	public void testJsonAsTypedValueReadWriteGuardDoesNotPass() {
		@SuppressWarnings("unchecked")
		final Predicate<String> guardMock = Mockito.mock(Predicate.class);
		Mockito.doAnswer(invocationOnMock -> primitiveTypeGuard.test(invocationOnMock.getArgument(0, String.class)))
				.when(guardMock)
				.test(ArgumentMatchers.anyString());
		final TypeAdapter<Integer> typeAdapter = AbstractEncodingTypeAdapter.TypedValueAsJsonObject.getInstance(gson, "t", "v", forClass, Class::getTypeName, primitiveTypeDecoder, guardMock);
		final JsonElement illegalJsonTree = JsonObjects.of(
				"t", JsonPrimitives.orNullable("void")
		);
		Assertions.assertThrows(JsonIOException.class, () -> typeAdapter.fromJsonTree(illegalJsonTree));
		Mockito.verify(guardMock)
				.test(ArgumentMatchers.eq("void"));
		Mockito.verifyNoMoreInteractions(guardMock);
	}

	private static final Map<String, Class<?>> primitiveTypeNames = Stream.of(boolean.class, byte.class, short.class, int.class, long.class, float.class, double.class, char.class)
			.collect(Collectors.toMap(Class::getName, Function.identity()));

	private static final Predicate<String> primitiveTypeGuard = primitiveTypeNames::containsKey;

	private static final Function<String, Class<?>> primitiveTypeDecoder = primitiveTypeNames::get;

}
