package lsh.ext.gson;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;

/**
 * Provides generic JSON methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class Json {

	private Json() {
	}

	/**
	 * Checks if the given string represents a valid JSON.
	 *
	 * @param json JSON string
	 *
	 * @return {@code true} if the given JSON reader represents a valid JSON, otherwise {@code false}.
	 *
	 * @throws IOException A rethrown exception.
	 * @since 0-SNAPSHOT
	 */
	public static boolean isJsonValid(final String json)
			throws IOException {
		return isJsonValid(new StringReader(json));
	}

	/**
	 * Checks if the given reader represents a valid JSON.
	 *
	 * @param reader Reader
	 *
	 * @return {@code true} if the given JSON reader represents a valid JSON, otherwise {@code false}.
	 *
	 * @throws IOException A rethrown exception
	 * @since 0-SNAPSHOT
	 */
	public static boolean isJsonValid(final Reader reader)
			throws IOException {
		return isJsonValid(new JsonReader(reader));
	}

	/**
	 * Checks if the given JSON reader represents a valid JSON.
	 *
	 * @param jsonReader JSON reader
	 *
	 * @return {@code true} if the given JSON reader represents a valid JSON, otherwise {@code false}.
	 *
	 * @throws IOException A rethrown exception
	 * @since 0-SNAPSHOT
	 */
	public static boolean isJsonValid(final JsonReader jsonReader)
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
