package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTypeAdapterTest<T, R> {

	protected abstract List<Arguments> makeTestCases();

	@Nullable
	protected abstract R normalize(@Nullable T value);

	@SuppressWarnings("NoopMethodInAbstractClass")
	protected void initialize(@SuppressWarnings("unused") final JsonReader jsonReader)
			throws IOException {
	}

	@SuppressWarnings("NoopMethodInAbstractClass")
	protected void finalize(@SuppressWarnings("unused") final JsonReader jsonReader)
			throws IOException {
	}

	@SuppressWarnings("NoopMethodInAbstractClass")
	protected void initialize(@SuppressWarnings("unused") final JsonWriter jsonWriter)
			throws IOException {
	}

	@SuppressWarnings("NoopMethodInAbstractClass")
	protected void finalize(@SuppressWarnings("unused") final JsonWriter jsonWriter)
			throws IOException {
	}

	@ParameterizedTest
	@MethodSource("makeTestCases")
	public final void testRead(final TypeAdapter<? extends T> unit, final String readJson, @SuppressWarnings("unused") final String writeJson, final T value)
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader(readJson));
		initialize(jsonReader);
		Assertions.assertEquals(normalize(value), normalize(unit.read(jsonReader)));
		finalize(jsonReader);
	}

	@ParameterizedTest
	@MethodSource("makeTestCases")
	public final void testWrite(final TypeAdapter<? super T> unit, @SuppressWarnings("unused") final String readJson, final String writeJson, final T value)
			throws IOException {
		final Writer actualWriter = new StringWriter();
		final JsonWriter jsonWriter = new JsonWriter(actualWriter);
		initialize(jsonWriter);
		unit.write(jsonWriter, value);
		finalize(jsonWriter);
		Assertions.assertEquals(writeJson, actualWriter.toString());
	}

	protected final Arguments makeTestCase(final TypeAdapter<?> unit, final String json, final T value) {
		return makeTestCase(unit, json, json, value);
	}

	protected final Arguments makeTestCase(final TypeAdapter<?> unit, final String readJson, final String writeJson, final T value) {
		return Arguments.of(unit, readJson, writeJson, value);
	}

}
