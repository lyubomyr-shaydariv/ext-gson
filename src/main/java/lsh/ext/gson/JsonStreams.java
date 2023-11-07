package lsh.ext.gson;

import java.io.IOException;
import java.math.BigDecimal;
import javax.annotation.WillNotClose;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonStreams {

	public static void copyTo(@WillNotClose final JsonReader reader, @WillNotClose final JsonWriter writer)
			throws IOException {
		copyTo(reader, writer, true);
	}

	@SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:JavaNCSS" })
	public static void copyTo(@WillNotClose final JsonReader reader, @WillNotClose final JsonWriter writer, final boolean ignoreTrailingTopLevelValues)
			throws IOException {
		int level = 0;
		topLevelValueLoop:
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
				if ( --level == 0 && ignoreTrailingTopLevelValues ) {
					break topLevelValueLoop;
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
				if ( --level == 0 && ignoreTrailingTopLevelValues ) {
					break topLevelValueLoop;
				}
				break;
			case NAME:
				final String name = reader.nextName();
				writer.name(name);
				break;
			case STRING:
				final String s = reader.nextString();
				writer.value(s);
				if ( level == 0 && ignoreTrailingTopLevelValues ) {
					break topLevelValueLoop;
				}
				break;
			case NUMBER:
				final Number n = new BigDecimal(reader.nextString());
				writer.value(n);
				if ( level == 0 && ignoreTrailingTopLevelValues ) {
					break topLevelValueLoop;
				}
				break;
			case BOOLEAN:
				final boolean b = reader.nextBoolean();
				writer.value(b);
				if ( level == 0 && ignoreTrailingTopLevelValues ) {
					break topLevelValueLoop;
				}
				break;
			case NULL:
				reader.nextNull();
				writer.nullValue();
				if ( level == 0 && ignoreTrailingTopLevelValues ) {
					break topLevelValueLoop;
				}
				break;
			case END_DOCUMENT:
				break topLevelValueLoop;
			default:
				throw new AssertionError(token);
			}
		}
	}

}
