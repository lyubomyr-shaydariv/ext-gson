package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
			@JsonAdapter(AbstractEncodingTypeAdapter.JsonAsString.Factory.class) List<Integer> value
	) {
	}

	private static final AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeResolver<Class<?>> forClass = AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeResolver.forClass;
	private static final AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder<Class<?>> forUnsafeClass = AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder.forUnsafeClass;
	private static final AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder.IGuard weakGuard = AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder.IGuard.weak;

	@Test
	public void testJsonAsTypedValueReadWrite()
			throws IOException {
		final TypeAdapter<Integer> typeAdapter = AbstractEncodingTypeAdapter.TypedValueAsJsonObject.getInstance(gson, "type", "value", forClass, forUnsafeClass, weakGuard);
		final Writer jsonWriter = new StringWriter();
		final int before = 2;
		typeAdapter.write(new JsonWriter(jsonWriter), before);
		final String json = jsonWriter.toString();
		final int after = typeAdapter.read(new JsonReader(new StringReader(json)));
		Assertions.assertEquals(before, after);
	}

	@Test
	public void testJsonAsTypedValueReadWriteGuardPasses() {
		final AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder.IGuard primitiveGuardMock = Mockito.mock(AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder.IGuard.class);
		Mockito.doAnswer(invocationOnMock -> primitiveTypeGuard.passes(invocationOnMock.getArgument(0, String.class)))
				.when(primitiveGuardMock)
				.passes(ArgumentMatchers.anyString());
		final TypeAdapter<Integer> typeAdapter = AbstractEncodingTypeAdapter.TypedValueAsJsonObject.getInstance(gson, "t", "v", forClass, primitiveTypeDecoder, primitiveGuardMock);
		final JsonElement legalJsonTree = JsonObjects.of(
				"t", JsonPrimitives.orNullable("int"),
				"v", JsonPrimitives.orNullable(1000)
		);
		final Integer actualPayload = Assertions.assertDoesNotThrow(() -> typeAdapter.fromJsonTree(legalJsonTree));
		Assertions.assertEquals(1000, actualPayload);
		Mockito.verify(primitiveGuardMock)
				.passes(ArgumentMatchers.eq("int"));
		Mockito.verifyNoMoreInteractions(primitiveGuardMock);
	}

	@Test
	public void testJsonAsTypedValueReadWriteGuardDoesNotPass() {
		final AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder.IGuard primitiveGuardMock = Mockito.mock(AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder.IGuard.class);
		Mockito.doAnswer(invocationOnMock -> primitiveTypeGuard.passes(invocationOnMock.getArgument(0, String.class)))
				.when(primitiveGuardMock)
				.passes(ArgumentMatchers.anyString());
		final TypeAdapter<Integer> typeAdapter = AbstractEncodingTypeAdapter.TypedValueAsJsonObject.getInstance(gson, "t", "v", forClass, primitiveTypeDecoder, primitiveGuardMock);
		final JsonElement illegalJsonTree = JsonObjects.of(
				"t", JsonPrimitives.orNullable("void")
		);
		Assertions.assertThrows(JsonIOException.class, () -> typeAdapter.fromJsonTree(illegalJsonTree));
		Mockito.verify(primitiveGuardMock)
				.passes(ArgumentMatchers.eq("void"));
		Mockito.verifyNoMoreInteractions(primitiveGuardMock);
	}

	private static final Map<String, Class<?>> primitiveTypeNames = Stream.of(boolean.class, byte.class, short.class, int.class, long.class, float.class, double.class, char.class)
			.collect(Collectors.toMap(Class::getName, Function.identity()));

	private static final AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder.IGuard primitiveTypeGuard = primitiveTypeNames::containsKey;

	private static final AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder<Class<?>> primitiveTypeDecoder = new AbstractEncodingTypeAdapter.TypedValueAsJsonObject.ITypeEncoder<>() {
		@Override
		public String encode(final Class<?> type) {
			throw new AssertionError(type);
		}

		@Override
		public Class<?> decode(final String typeName) {
			return primitiveTypeNames.get(typeName);
		}
	};

}
