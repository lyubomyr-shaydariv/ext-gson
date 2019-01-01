package lsh.ext.gson.adapters;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public final class PackedJsonTypeAdapterTest {

	private static final Gson gson = new Gson();

	private static final String OUTER_JSON = outer().toString();
	private static final String INNER_JSON = inner().toString();

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapter<String> unit = PackedJsonTypeAdapter.get();
		final Reader reader = new StringReader(INNER_JSON);
		final JsonReader jsonReader = new JsonReader(reader);
		final String innerJson = unit.read(jsonReader);
		MatcherAssert.assertThat(innerJson, CoreMatchers.is(INNER_JSON));
	}

	@Test
	public void testWrite()
			throws IOException {
		final TypeAdapter<String> unit = PackedJsonTypeAdapter.get();
		final Writer writer = new StringWriter();
		final JsonWriter jsonWriter = new JsonWriter(writer);
		unit.write(jsonWriter, INNER_JSON);
		final JsonElement innerJsonElement = gson.fromJson(writer.toString(), JsonElement.class);
		MatcherAssert.assertThat(innerJsonElement, CoreMatchers.is(inner()));
	}

	@Test
	public void testJsonAdapter() {
		final Outer outer = gson.fromJson(OUTER_JSON, Outer.class);
		MatcherAssert.assertThat(outer.id, CoreMatchers.is(1));
		MatcherAssert.assertThat(outer.inner, CoreMatchers.is(INNER_JSON));
	}

	private static final class Outer {

		private final int id = Integer.valueOf(0);

		@JsonAdapter(PackedJsonTypeAdapter.class)
		private final String inner = null;

	}

	private static JsonElement outer() {
		final JsonObject packedJson = new JsonObject();
		packedJson.addProperty("id", 1);
		packedJson.add("inner", inner());
		return packedJson;
	}

	private static JsonElement inner() {
		final JsonObject whatever = new JsonObject();
		whatever.addProperty("foo", 1);
		whatever.addProperty("bar", "bar");
		final JsonArray baz = new JsonArray();
		baz.add(1);
		baz.add(2);
		baz.add(3);
		whatever.add("baz", baz);
		return whatever;
	}

}
