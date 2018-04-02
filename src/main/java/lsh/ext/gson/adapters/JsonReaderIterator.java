package lsh.ext.gson.adapters;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

final class JsonReaderIterator<T>
		implements Iterator<T>, Closeable {

	private final Type elementType;
	private final Gson gson;
	private final JsonReader in;

	private ReadingIteratorState state = ReadingIteratorState.BEFORE_ARRAY;

	private JsonReaderIterator(final Type elementType, final Gson gson, final JsonReader in) {
		this.elementType = elementType;
		this.gson = gson;
		this.in = in;
	}

	static <T> Iterator<T> get(final Type elementType, final Gson gson, final JsonReader in) {
		return new JsonReaderIterator<>(elementType, gson, in);
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
			throw new RuntimeException(ex);
		}
	}

	@Override
	public T next() {
		try {
			if ( !in.hasNext() || state == ReadingIteratorState.END_OF_STREAM ) {
				throw new NoSuchElementException();
			}
			final T element;
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
					element = gson.fromJson(in, elementType);
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
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void close()
			throws IOException {
		in.close();
	}

	private enum ReadingIteratorState {

		BEFORE_ARRAY,
		WITHIN_ARRAY,
		AFTER_ARRAY,
		END_OF_STREAM

	}

}
