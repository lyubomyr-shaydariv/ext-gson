package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;
import org.junit.Test;

import static lsh.ext.gson.Json.isJsonValid;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class JsonTest {

	private static final String VALID_JSON = "{\"foo\": \"bar\"}";
	private static final String INVALID_JSON = "foobar";

	@Test
	public void isJsonValidForString()
			throws IOException {
		assertThat(isJsonValid(VALID_JSON), is(true));
		assertThat(isJsonValid(INVALID_JSON), is(false));
	}

	@Test
	public void isJsonValid1ForReader()
			throws IOException {
		assertThat(isJsonValid(new StringReader(VALID_JSON)), is(true));
		assertThat(isJsonValid(new StringReader(INVALID_JSON)), is(false));
	}

	@Test
	public void isJsonValid2ForJsonReader()
			throws IOException {
		assertThat(isJsonValid(new JsonReader(new StringReader(VALID_JSON))), is(true));
		assertThat(isJsonValid(new JsonReader(new StringReader(INVALID_JSON))), is(false));
	}

}