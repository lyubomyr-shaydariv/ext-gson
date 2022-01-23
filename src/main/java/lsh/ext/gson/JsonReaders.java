package lsh.ext.gson;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * Provides miscellaneous {@link JsonReader} utility methods.
 *
 * @author Lyubomyr Shaydariv
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
	 * @see #readValuedJsonToken(JsonReader)
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

	/**
	 * @param jsonReader JSON reader
	 *
	 * @return A valued JSON token from the JSON reader
	 *
	 * @throws IOException A rethrown exception
	 * @see #skipToken(JsonReader)
	 * @see #readValuedJsonTokenRecursively(JsonReader)
	 */
	public static ValuedJsonToken<?> readValuedJsonToken(final JsonReader jsonReader)
			throws IOException {
		final JsonToken jsonToken = jsonReader.peek();
		final ValuedJsonToken<?> valuedJsonToken;
		switch ( jsonToken ) {
		case BEGIN_ARRAY:
			jsonReader.beginArray();
			valuedJsonToken = ValuedJsonToken.arrayBegin();
			break;
		case END_ARRAY:
			jsonReader.endArray();
			valuedJsonToken = ValuedJsonToken.arrayEnd();
			break;
		case BEGIN_OBJECT:
			jsonReader.beginObject();
			valuedJsonToken = ValuedJsonToken.objectBegin();
			break;
		case END_OBJECT:
			jsonReader.endObject();
			valuedJsonToken = ValuedJsonToken.objectEnd();
			break;
		case NAME:
			final String name = jsonReader.nextName();
			valuedJsonToken = ValuedJsonToken.name(name);
			break;
		case STRING:
			final String s = jsonReader.nextString();
			valuedJsonToken = ValuedJsonToken.value(s);
			break;
		case NUMBER:
			final double d = jsonReader.nextDouble();
			valuedJsonToken = ValuedJsonToken.value(d);
			break;
		case BOOLEAN:
			final boolean b = jsonReader.nextBoolean();
			valuedJsonToken = ValuedJsonToken.value(b);
			break;
		case NULL:
			jsonReader.nextNull();
			valuedJsonToken = ValuedJsonToken.value();
			break;
		case END_DOCUMENT:
			valuedJsonToken = ValuedJsonToken.documentEnd();
			break;
		default:
			throw new AssertionError(jsonToken);
		}
		return valuedJsonToken;
	}

	/**
	 * @param jsonReader JSON reader
	 *
	 * @return An iterator of a single value JSON tokens: either single primitives, either single object/arrays.
	 *
	 * @see #readValuedJsonToken(JsonReader)
	 */
	public static ICloseableIterator<ValuedJsonToken<?>> readValuedJsonTokenRecursively(final JsonReader jsonReader) {
		return new ICloseableIterator<ValuedJsonToken<?>>() {
			private int level;

			@Override
			public void close()
					throws IOException {
				jsonReader.close();
			}

			@Override
			public boolean hasNext() {
				if ( level < 0 ) {
					return false;
				}
				try {
					final JsonToken token = jsonReader.peek();
					return token != JsonToken.END_DOCUMENT;
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}

			@Override
			public ValuedJsonToken<?> next() {
				try {
					if ( level < 0 ) {
						throw new NoSuchElementException();
					}
					final ValuedJsonToken<?> valuedJsonToken = readValuedJsonToken(jsonReader);
					switch ( valuedJsonToken.getToken() ) {
					case BEGIN_ARRAY:
					case BEGIN_OBJECT:
						level++;
						break;
					case END_ARRAY:
					case END_OBJECT:
						level--;
						break;
					case NAME:
					case STRING:
					case NUMBER:
					case BOOLEAN:
					case NULL:
						break;
					case END_DOCUMENT:
					default:
						throw new AssertionError(valuedJsonToken.getToken());
					}
					if ( level == 0 ) {
						level = -1;
					}
					return valuedJsonToken;
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
		};
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
