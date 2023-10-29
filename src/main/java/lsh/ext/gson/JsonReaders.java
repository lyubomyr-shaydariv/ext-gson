package lsh.ext.gson;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.WillNotClose;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonReaders {

	public static boolean isValid(final JsonReader jsonReader)
			throws IOException {
		try {
			JsonToken token;
			while ( (token = jsonReader.peek()) != JsonToken.END_DOCUMENT && token != null ) {
				skipToken(jsonReader);
			}
			return true;
		} catch ( final MalformedJsonException ignored ) {
			return false;
		}
	}

	public static JsonReader getEmptyStringFailFastJsonReader(final Reader reader) {
		return new EmptyStringFailFastJsonReader(reader);
	}

	@SuppressWarnings("checkstyle:CyclomaticComplexity")
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

	@SuppressWarnings("checkstyle:CyclomaticComplexity")
	public static ValuedJsonToken<?> readValuedJsonToken(final JsonReader jsonReader)
			throws IOException {
		final JsonToken jsonToken = jsonReader.peek();
		final ValuedJsonToken<?> valuedJsonToken;
		switch ( jsonToken ) {
		case BEGIN_ARRAY:
			jsonReader.beginArray();
			valuedJsonToken = ValuedJsonToken.arrayBegin;
			break;
		case END_ARRAY:
			jsonReader.endArray();
			valuedJsonToken = ValuedJsonToken.arrayEnd;
			break;
		case BEGIN_OBJECT:
			jsonReader.beginObject();
			valuedJsonToken = ValuedJsonToken.objectBegin;
			break;
		case END_OBJECT:
			jsonReader.endObject();
			valuedJsonToken = ValuedJsonToken.objectEnd;
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
			valuedJsonToken = ValuedJsonToken.nullValue;
			break;
		case END_DOCUMENT:
			valuedJsonToken = ValuedJsonToken.documentEnd;
			break;
		default:
			throw new AssertionError(jsonToken);
		}
		return valuedJsonToken;
	}

	public static Iterator<ValuedJsonToken<?>> readValuedJsonTokenRecursively(@WillNotClose final JsonReader jsonReader) {
		return new RecursiveIterator(jsonReader);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class RecursiveIterator
			implements Iterator<ValuedJsonToken<?>> {

		@WillNotClose
		private final JsonReader jsonReader;

		private int level;

		@Override
		public boolean hasNext() {
			if ( level < 0 ) {
				return false;
			}
			try {
				final JsonToken token = jsonReader.peek();
				return token != JsonToken.END_DOCUMENT;
			} catch ( final IOException ex ) {
				throw new JsonIOException(ex);
			}
		}

		@Override
		@SuppressWarnings("checkstyle:CyclomaticComplexity")
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
