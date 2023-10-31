package lsh.ext.gson;

import java.io.IOException;

import javax.annotation.WillNotClose;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonReaders {

	public static boolean isValid(@WillNotClose final JsonReader jsonReader)
			throws IOException {
		try {
			for ( JsonToken token = jsonReader.peek(); token != JsonToken.END_DOCUMENT && token != null; token = jsonReader.peek() ) {
				skipToken(jsonReader);
			}
			return true;
		} catch ( final MalformedJsonException ignored ) {
			return false;
		}
	}

	@SuppressWarnings("checkstyle:CyclomaticComplexity")
	public static void skipToken(@WillNotClose final JsonReader reader)
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

}
