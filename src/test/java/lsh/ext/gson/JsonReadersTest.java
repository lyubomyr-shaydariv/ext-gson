package lsh.ext.gson;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.junit.Test;

import static lsh.ext.gson.JsonReaders.getEmptyStringFailFastJsonReader;

public final class JsonReadersTest {

	private static final Gson gson = new Gson();

	@Test
	public void testGsonWithoutEmptyStringFailFastJsonReaderMustNotFailOnReadingAnEmptyString() {
		gson.fromJson(new StringReader(""), Void.class);
	}

	@Test(expected = JsonSyntaxException.class)
	public void testGsonWithEmptyStringFailFastJsonReaderMustFailOnReadingAnEmptyString() {
		gson.fromJson(getEmptyStringFailFastJsonReader(new StringReader("")), Void.class);
	}

}
