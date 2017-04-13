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

	/**
	 * <p>Skips a token of any type for the given {@link JsonReader}. Unlike {@link JsonReader#skipValue()}, this method can also skip tokens such as:</p>
	 *
	 * <ul>
	 * <li>{@link JsonToken#BEGIN_OBJECT} and {@link JsonToken#END_OBJECT}</li>
	 * <li>{@link JsonToken#BEGIN_ARRAY} and {@link JsonToken#END_ARRAY}</li>
	 * <li>{@link JsonToken#NAME}</li>
	 * <li>{@link JsonToken#END_DOCUMENT} (ignored)</li>
	 * </ul>
	 *
	 * @param reader Reader any token to read and skip from
	 *
	 * @throws IOException A rethrown exception
	 * @see JsonReader#skipValue()
	 * @since 0-SNAPSHOT
	 */
	public static void skipToken(final JsonReader reader)
			throws IOException {
		final JsonToken token = reader.peek();
		switch ( token ) {
		case BEGIN_ARRAY:
			reader.beginArray();
			break;
		case END_ARRAY:
			reader.endArray();
			break;
		case BEGIN_OBJECT:
			reader.beginObject();
			break;
		case END_OBJECT:
			reader.endObject();
			break;
		case NAME:
			reader.nextName();
			break;
		case STRING:
		case NUMBER:
		case BOOLEAN:
		case NULL:
			reader.skipValue();
			break;
		case END_DOCUMENT:
		default:
			throw new AssertionError(token);
		}
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
