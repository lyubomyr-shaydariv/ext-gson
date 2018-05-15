package lsh.ext.gson;

import java.io.IOException;
import java.math.BigDecimal;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Provides miscellaneous utility methods for JSON streams.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class JsonStreams {

	private JsonStreams() {
	}

	/**
	 * Copies the given {@link JsonReader} stream to normalized JSON output via {@link JsonWriter} not ignoring incoming JSON stream trailing tokens.
	 *
	 * @param reader JSON reader. The reader must have {@link JsonReader#setLenient(boolean)} set to {@code} in order to read not normalized JSON streams.
	 * @param writer JSON writer
	 *
	 * @throws IOException A rethrown exception
	 * @see #copyTo(JsonReader, JsonWriter, boolean)
	 * @since 0-SNAPSHOT
	 */
	public static void copyTo(final JsonReader reader, final JsonWriter writer)
			throws IOException {
		copyTo(reader, writer, true);
	}

	/**
	 * Copies the given {@link JsonReader} content to normalized JSON output via {@link JsonWriter}.
	 *
	 * @param reader               JSON reader. The reader must have {@link JsonReader#setLenient(boolean)} set to {@code} in order to read not normalized JSON streams.
	 * @param writer               JSON writer
	 * @param ignoreTrailingTokens Ignore incoming JSON stream trailing tokens
	 *
	 * @throws IOException A rethrown exception
	 * @see #copyTo(JsonReader, JsonWriter)
	 * @since 0-SNAPSHOT
	 */
	@SuppressWarnings("resource")
	public static void copyTo(final JsonReader reader, final JsonWriter writer, final boolean ignoreTrailingTokens)
			throws IOException {
		int level = 0;
		loop:
		for ( JsonToken token = reader.peek(); token != null; token = reader.peek() ) {
			switch ( token ) {
			case BEGIN_ARRAY:
				reader.beginArray();
				writer.beginArray();
				++level;
				break;
			case END_ARRAY:
				reader.endArray();
				writer.endArray();
				if ( --level == 0 && ignoreTrailingTokens ) {
					return;
				}
				break;
			case BEGIN_OBJECT:
				reader.beginObject();
				writer.beginObject();
				++level;
				break;
			case END_OBJECT:
				reader.endObject();
				writer.endObject();
				if ( --level == 0 && ignoreTrailingTokens ) {
					return;
				}
				break;
			case NAME:
				final String name = reader.nextName();
				writer.name(name);
				break;
			case STRING:
				final String s = reader.nextString();
				writer.value(s);
				break;
			case NUMBER:
				final Number n = new BigDecimal(reader.nextString());
				writer.value(n);
				break;
			case BOOLEAN:
				final boolean b = reader.nextBoolean();
				writer.value(b);
				break;
			case NULL:
				reader.nextNull();
				writer.nullValue();
				break;
			case END_DOCUMENT:
				break loop;
			default:
				throw new AssertionError(token);
			}
		}
	}

}
