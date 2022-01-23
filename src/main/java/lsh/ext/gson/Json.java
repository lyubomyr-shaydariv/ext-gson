package lsh.ext.gson;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import lombok.experimental.UtilityClass;

/**
 * Provides generic JSON methods.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class Json {

	/**
	 * @param json JSON string
	 *
	 * @return {@code true} if the given JSON reader represents a valid JSON, otherwise {@code false}.
	 *
	 * @throws IOException A rethrown exception.
	 */
	public static boolean isValid(final String json)
			throws IOException {
		return isValid(new StringReader(json));
	}

	/**
	 * @param reader Reader
	 *
	 * @return {@code true} if the given JSON reader represents a valid JSON, otherwise {@code false}.
	 *
	 * @throws IOException A rethrown exception
	 */
	public static boolean isValid(final Reader reader)
			throws IOException {
		return isValid(new JsonReader(reader));
	}

	/**
	 * @param jsonReader JSON reader
	 *
	 * @return {@code true} if the given JSON reader represents a valid JSON, otherwise {@code false}.
	 *
	 * @throws IOException A rethrown exception
	 */
	public static boolean isValid(final JsonReader jsonReader)
			throws IOException {
		try {
			JsonToken token;
			while ( (token = jsonReader.peek()) != JsonToken.END_DOCUMENT && token != null ) {
				JsonReaders.skipToken(jsonReader);
			}
			return true;
		} catch ( final MalformedJsonException ignored ) {
			return false;
		}
	}

}
