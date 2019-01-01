package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonTest {

	private static final String VALID_JSON = "{\"foo\": \"bar\"}";
	private static final String INVALID_JSON = "foobar";

	@Test
	public void isJsonValidForString()
			throws IOException {
		Assertions.assertTrue(Json.isValid(VALID_JSON));
		Assertions.assertFalse(Json.isValid(INVALID_JSON));
	}

	@Test
	public void isJsonValid1ForReader()
			throws IOException {
		Assertions.assertTrue(Json.isValid(new StringReader(VALID_JSON)));
		Assertions.assertFalse(Json.isValid(new StringReader(INVALID_JSON)));
	}

	@Test
	public void isJsonValid2ForJsonReader()
			throws IOException {
		Assertions.assertTrue(Json.isValid(new JsonReader(new StringReader(VALID_JSON))));
		Assertions.assertFalse(Json.isValid(new JsonReader(new StringReader(INVALID_JSON))));
	}

}