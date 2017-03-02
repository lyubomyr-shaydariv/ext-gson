package lsh.ext.gson;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * Provides miscellaneous {@link JsonReader} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class JsonReaders {

	private JsonReaders() {
	}

	/**
	 * <p>Google Gson does not fail on empty strings since version 1.5:</p>
	 *
	 * <pre>
	 * public &lt;T&gt; T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
	 *   boolean isEmpty = true;
	 *   ...
	 *   try {
	 *     reader.peek();
	 *     ...
	 *   } catch (EOFException e) {
	 *     /*
	 *      * For compatibility with JSON 1.5 and earlier, we return null for empty
	 *      * documents instead of throwing.
	 *     {@literal *}/
	 *     if (isEmpty) {
	 *       return null;
	 *     }
	 *     throw new JsonSyntaxException(e);
	 * </pre>
	 *
	 * <p>In some cases it can be undesirable.</p>
	 *
	 * @param reader Reader
	 *
	 * @return An empty content fail-fast JSON reader.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonReader getEmptyStringFailFastJsonReader(final Reader reader) {
		return new EmptyStringFailFastJsonReader(reader);
	}

	private static final class EmptyStringFailFastJsonReader
			extends JsonReader {

		private EmptyStringFailFastJsonReader(final Reader reader) {
			super(reader);
		}

		@Override
		public JsonToken peek()
				throws IOException {
			try {
				return super.peek();
			} catch ( final EOFException ex ) {
				throw new JsonSyntaxException(ex);
			}
		}

	}

}
