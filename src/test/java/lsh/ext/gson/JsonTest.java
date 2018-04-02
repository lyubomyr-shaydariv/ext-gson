package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public final class JsonTest {

	private static final String VALID_JSON = "{\"foo\": \"bar\"}";
	private static final String INVALID_JSON = "foobar";

	@Test
	public void isJsonValidForString()
			throws IOException {
		Assert.assertThat(Json.isValid(VALID_JSON), CoreMatchers.is(true));
		Assert.assertThat(Json.isValid(INVALID_JSON), CoreMatchers.is(false));
	}

	@Test
	public void isJsonValid1ForReader()
			throws IOException {
		Assert.assertThat(Json.isValid(new StringReader(VALID_JSON)), CoreMatchers.is(true));
		Assert.assertThat(Json.isValid(new StringReader(INVALID_JSON)), CoreMatchers.is(false));
	}

	@Test
	public void isJsonValid2ForJsonReader()
			throws IOException {
		Assert.assertThat(Json.isValid(new JsonReader(new StringReader(VALID_JSON))), CoreMatchers.is(true));
		Assert.assertThat(Json.isValid(new JsonReader(new StringReader(INVALID_JSON))), CoreMatchers.is(false));
	}

}