package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class TypeEncoderTypeAdapterTest {

	private static final Gson gson = Gsons.getNormalized();

	@Test
	public void testReadWrite()
			throws IOException {
		final TypeAdapter<Integer> typeAdapter = TypeEncoderTypeAdapter.getInstance(gson, "type", "value", TypeEncoderTypeAdapter.ITypeResolver.forClass, TypeEncoderTypeAdapter.ITypeEncoder.forUnsafeClass);
		final Writer jsonWriter = new StringWriter();
		final int before = 2;
		typeAdapter.write(new JsonWriter(jsonWriter), before);
		final String json = jsonWriter.toString();
		final int after = typeAdapter.read(new JsonReader(new StringReader(json)));
		Assertions.assertEquals(before, after);
	}

}
