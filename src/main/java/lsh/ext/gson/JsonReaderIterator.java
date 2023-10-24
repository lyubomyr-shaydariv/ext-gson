package lsh.ext.gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.WillNotClose;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("checkstyle:MissingJavadocType")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonReaderIterator<E>
		implements Iterator<E> {

	private final TypeAdapter<? extends E> elementTypeAdapter;
	private final JsonReader in;

	private ReadingIteratorState state = ReadingIteratorState.BEFORE_ARRAY;

	@SuppressWarnings("checkstyle:MissingJavadocMethod")
	public static <E> Iterator<E> getInstance(final TypeAdapter<? extends E> elementTypeAdapter, @WillNotClose final JsonReader in) {
		return new JsonReaderIterator<>(elementTypeAdapter, in);
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
	@SuppressWarnings("checkstyle:CyclomaticComplexity")
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
			throw new RuntimeException(ex);
		}
	}

	private enum ReadingIteratorState {

		BEFORE_ARRAY,
		WITHIN_ARRAY,
		AFTER_ARRAY,
		END_OF_STREAM

	}

}
