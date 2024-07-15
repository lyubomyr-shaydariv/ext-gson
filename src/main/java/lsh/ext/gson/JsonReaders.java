package lsh.ext.gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
		return JsonReaderIterator.getInstance(jsonReader, elementTypeAdapter);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class JsonReaderIterator<E>
			implements Iterator<E> {

		private final JsonReader in;
		private final TypeAdapter<? extends E> elementTypeAdapter;

		private ReadingIteratorState state = ReadingIteratorState.BEFORE_ARRAY;

		private static <E> Iterator<E> getInstance(@WillNotClose final JsonReader in, final TypeAdapter<? extends E> elementTypeAdapter) {
			return new JsonReaderIterator<>(in, elementTypeAdapter);
		}

		@Override
		public boolean hasNext() {
			try {
				if ( state == ReadingIteratorState.END_OF_STREAM ) {
					return false;
				}
				final boolean hasNext;
				loop:
				for ( ; ; ) {
					switch ( state ) {
					case BEFORE_ARRAY:
						if ( in.peek() == JsonToken.BEGIN_ARRAY ) {
							in.beginArray();
							state = ReadingIteratorState.WITHIN_ARRAY;
						}
						continue;
					case WITHIN_ARRAY:
						if ( in.peek() == JsonToken.END_ARRAY ) {
							in.endArray();
							state = ReadingIteratorState.END_OF_STREAM;
							continue;
						}
						hasNext = true;
						break loop;
					case AFTER_ARRAY:
						hasNext = false;
						state = ReadingIteratorState.END_OF_STREAM;
						break loop;
					case END_OF_STREAM:
						hasNext = false;
						break loop;
					default:
						throw new AssertionError(state);
					}
				}
				return hasNext;
			} catch ( final IOException ex ) {
				throw new JsonIOException(ex);
			}
		}

		@Override
		@SuppressWarnings({ "checkstyle:CyclomaticComplexity", "ForLoopReplaceableByWhile" })
		public E next() {
			try {
				if ( !in.hasNext() || state == ReadingIteratorState.END_OF_STREAM ) {
					throw new NoSuchElementException();
				}
				final E element;
				loop:
				for ( ; ; ) {
					switch ( state ) {
					case BEFORE_ARRAY:
						in.beginArray();
						state = ReadingIteratorState.WITHIN_ARRAY;
						if ( in.peek() == JsonToken.END_ARRAY ) {
							state = ReadingIteratorState.END_OF_STREAM;
						}
						break;
					case WITHIN_ARRAY:
						element = elementTypeAdapter.read(in);
						if ( in.peek() == JsonToken.END_ARRAY ) {
							state = ReadingIteratorState.AFTER_ARRAY;
						}
						break loop;
					case AFTER_ARRAY:
						in.endArray();
						state = ReadingIteratorState.END_OF_STREAM;
						break;
					case END_OF_STREAM:
						throw new NoSuchElementException(String.valueOf(state));
					default:
						throw new AssertionError(state);
					}
				}
				return element;
			} catch ( final IOException ex ) {
				throw new JsonIOException(ex);
			}
		}

		private enum ReadingIteratorState {

			BEFORE_ARRAY,
			WITHIN_ARRAY,
			AFTER_ARRAY,
			END_OF_STREAM

		}

	}

}
