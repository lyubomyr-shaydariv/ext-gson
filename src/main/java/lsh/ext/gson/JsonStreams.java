package lsh.ext.gson;

import java.io.IOException;
import java.math.BigDecimal;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonStreams {

	public static void copyTo(final JsonReader reader, final JsonWriter writer)
			throws IOException {
		copyTo(reader, writer, true);
	}

	@SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:JavaNCSS" })
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
