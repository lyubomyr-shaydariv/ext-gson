package lsh.ext.gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import javax.annotation.WillNotClose;

import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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

	@SuppressWarnings("checkstyle:CyclomaticComplexity")
	public static void skipValue(@WillNotClose final JsonReader jsonReader)
			throws IOException {
		final JsonToken jsonToken = jsonReader.peek();
		switch ( jsonToken ) {
		case BEGIN_ARRAY:
		case BEGIN_OBJECT:
		case NAME:
		case STRING:
		case NUMBER:
		case BOOLEAN:
		case NULL:
			jsonReader.skipValue();
			return;
		case END_ARRAY:
			jsonReader.endArray();
			return;
		case END_OBJECT:
			jsonReader.endObject();
			return;
		case END_DOCUMENT:
			// do nothing
			return;
		default:
			throw new AssertionError(jsonToken);
		}
	}

	public static <E> Iterator<E> asIterator(@WillNotClose final JsonReader jsonReader, final TypeAdapter<? extends E> elementTypeAdapter) {
		return new JsonReaderIterator<>(jsonReader, elementTypeAdapter);
	}

	public static PrimitiveIterator.OfInt asIntIterator(@WillNotClose final JsonReader jsonReader) {
		return new JsonReaderIntIterator(jsonReader);
	}

	public static PrimitiveIterator.OfLong asLongIterator(@WillNotClose final JsonReader jsonReader) {
		return new JsonReaderLongIterator(jsonReader);
	}

	public static PrimitiveIterator.OfDouble asDoubleIterator(@WillNotClose final JsonReader jsonReader) {
		return new JsonReaderDoubleIterator(jsonReader);
	}

	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	private abstract static class AbstractJsonReaderIterator {

		@SuppressWarnings("ProtectedField")
		protected final JsonReader in;

		public final boolean hasNext() {
			try {
				return in.hasNext();
			} catch ( final IOException ex ) {
				throw new JsonIOException(ex);
			}
		}

	}

	private static final class JsonReaderIterator<E>
			extends AbstractJsonReaderIterator
			implements Iterator<E> {

		private final TypeAdapter<? extends E> elementTypeAdapter;

		private JsonReaderIterator(final JsonReader in, final TypeAdapter<? extends E> elementTypeAdapter) {
			super(in);
			this.elementTypeAdapter = elementTypeAdapter;
		}

		@Override
		public E next() {
			try {
				if ( !in.hasNext() ) {
					throw new NoSuchElementException();
				}
				return elementTypeAdapter.read(in);
			} catch ( final IOException ex ) {
				throw new JsonIOException(ex);
			}
		}

	}

	private static final class JsonReaderIntIterator
			extends AbstractJsonReaderIterator
			implements PrimitiveIterator.OfInt {

		private JsonReaderIntIterator(final JsonReader in) {
			super(in);
		}

		@Override
		public int nextInt() {
			try {
				return in.nextInt();
			} catch ( final IOException ex ) {
				throw new JsonIOException(ex);
			}
		}

	}

	private static final class JsonReaderLongIterator
			extends AbstractJsonReaderIterator
			implements PrimitiveIterator.OfLong {

		private JsonReaderLongIterator(final JsonReader in) {
			super(in);
		}

		@Override
		public long nextLong() {
			try {
				return in.nextLong();
			} catch ( final IOException ex ) {
				throw new JsonIOException(ex);
			}
		}

	}

	private static final class JsonReaderDoubleIterator
			extends AbstractJsonReaderIterator
			implements PrimitiveIterator.OfDouble {

		private JsonReaderDoubleIterator(final JsonReader in) {
			super(in);
		}

		@Override
		public double nextDouble() {
			try {
				return in.nextDouble();
			} catch ( final IOException ex ) {
				throw new JsonIOException(ex);
			}
		}

	}

}
