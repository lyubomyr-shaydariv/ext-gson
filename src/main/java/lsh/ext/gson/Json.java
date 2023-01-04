package lsh.ext.gson;

import java.io.IOException;

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
