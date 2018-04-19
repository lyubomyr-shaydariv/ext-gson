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
	 * @param json JSON string
	 *
	 * @return {@code true} if the given JSON reader represents a valid JSON, otherwise {@code false}.
	 *
	 * @throws IOException A rethrown exception.
	 * @since 0-SNAPSHOT
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
	 * @since 0-SNAPSHOT
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
	 * @since 0-SNAPSHOT
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
