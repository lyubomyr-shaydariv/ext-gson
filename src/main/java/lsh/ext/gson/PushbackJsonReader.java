package lsh.ext.gson;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import javax.annotation.Nullable;

import com.google.common.annotations.Beta;
import com.google.common.collect.Iterators;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * Represents a pushback implementation of {@link JsonReader} similar to {@link java.io.PushbackInputStream} and {@link java.io.PushbackReader}.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
@Beta
public final class PushbackJsonReader
		extends JsonReader
		implements Iterable<ValuedJsonToken<?>> {

	private final Queue<ValuedJsonToken<?>> tokens = new ArrayDeque<>();

	private Mode mode;

	private PushbackJsonReader(final Reader jsonReader, final Mode mode) {
		super(jsonReader);
		this.mode = mode;
	}

	/**
	 * @param reader Reader to provide JSON character stream
	 *
	 * @return An instance of {@link PushbackJsonReader}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static PushbackJsonReader getAndRecord(final Reader reader) {
		return new PushbackJsonReader(reader, Mode.RECORDING);
	}

	@Override
	public void beginArray()
			throws IOException {
		switch ( mode ) {
		case RECORDING:
			super.beginArray();
			tokens.add(ValuedJsonToken.arrayBegin());
			break;
		case REPLAYING:
			replayValue(JsonToken.BEGIN_ARRAY);
			break;
		case REPLAYED:
			super.beginArray();
			break;
		default:
			throw new AssertionError(mode);
		}
	}

	@Override
	public void endArray()
			throws IOException {
		switch ( mode ) {
		case RECORDING:
			super.endArray();
			tokens.add(ValuedJsonToken.arrayEnd());
			break;
		case REPLAYING:
			replayValue(JsonToken.END_ARRAY);
			break;
		case REPLAYED:
			super.endArray();
			break;
		default:
			throw new AssertionError(mode);
		}
	}

	@Override
	public void beginObject()
			throws IOException {
		switch ( mode ) {
		case RECORDING:
			super.beginObject();
			tokens.add(ValuedJsonToken.objectBegin());
			break;
		case REPLAYING:
			replayValue(JsonToken.BEGIN_OBJECT);
			break;
		case REPLAYED:
			super.beginObject();
			break;
		default:
			throw new AssertionError(mode);
		}
	}

	@Override
	public void endObject()
			throws IOException {
		switch ( mode ) {
		case RECORDING:
			super.endObject();
			tokens.add(ValuedJsonToken.objectEnd());
			break;
		case REPLAYING:
			replayValue(JsonToken.END_OBJECT);
			break;
		case REPLAYED:
			super.endObject();
			break;
		default:
			throw new AssertionError(mode);
		}
	}

	@Override
	public boolean hasNext()
			throws IOException {
		final boolean hasNext;
		switch ( mode ) {
		case RECORDING:
			hasNext = super.hasNext();
			break;
		case REPLAYING:
			final ValuedJsonToken<?> token = tokens.peek();
			hasNext = token.getToken() != JsonToken.END_ARRAY && token.getToken() != JsonToken.END_OBJECT;
			break;
		case REPLAYED:
			hasNext = super.hasNext();
			break;
		default:
			throw new AssertionError(mode);
		}
		return hasNext;
	}

	@Override
	public JsonToken peek()
			throws IOException {
		final JsonToken peek;
		switch ( mode ) {
		case RECORDING:
			peek = super.peek();
			break;
		case REPLAYING:
			peek = tokens.peek().getToken();
			break;
		case REPLAYED:
			peek = super.peek();
			break;
		default:
			throw new AssertionError(mode);
		}
		return peek;
	}

	@Override
	public String nextName()
			throws IOException {
		final String nextName;
		switch ( mode ) {
		case RECORDING:
			nextName = super.nextName();
			tokens.add(ValuedJsonToken.name(nextName));
			break;
		case REPLAYING:
			nextName = replayValue(JsonToken.NAME);
			break;
		case REPLAYED:
			nextName = super.nextName();
			break;
		default:
			throw new AssertionError(mode);
		}
		return nextName;
	}

	@Override
	public String nextString()
			throws IOException {
		final String nextString;
		switch ( mode ) {
		case RECORDING:
			nextString = super.nextString();
			tokens.add(ValuedJsonToken.value(nextString));
			break;
		case REPLAYING:
			nextString = replayValue(JsonToken.STRING);
			break;
		case REPLAYED:
			nextString = super.nextString();
			break;
		default:
			throw new AssertionError(mode);
		}
		return nextString;
	}

	@Override
	public boolean nextBoolean()
			throws IOException {
		final boolean nextBoolean;
		switch ( mode ) {
		case RECORDING:
			nextBoolean = super.nextBoolean();
			tokens.add(ValuedJsonToken.value(nextBoolean));
			break;
		case REPLAYING:
			final Boolean valueToReplay = replayValue(JsonToken.BOOLEAN);
			assert valueToReplay != null;
			nextBoolean = valueToReplay;
			break;
		case REPLAYED:
			nextBoolean = super.nextBoolean();
			break;
		default:
			throw new AssertionError(mode);
		}
		return nextBoolean;
	}

	@Override
	public void nextNull()
			throws IOException {
		switch ( mode ) {
		case RECORDING:
			super.nextNull();
			tokens.add(ValuedJsonToken.value());
			return;
		case REPLAYING:
			replayValue(JsonToken.NULL);
			break;
		case REPLAYED:
			super.nextNull();
			break;
		default:
			throw new AssertionError(mode);
		}
	}

	@Override
	public double nextDouble()
			throws IOException {
		final double nextDouble;
		switch ( mode ) {
		case RECORDING:
			nextDouble = super.nextDouble();
			tokens.add(ValuedJsonToken.value(nextDouble));
			break;
		case REPLAYING:
			final Number valueToReplay = replayValue(JsonToken.NUMBER);
			assert valueToReplay != null;
			nextDouble = valueToReplay.doubleValue();
			break;
		case REPLAYED:
			nextDouble = super.nextDouble();
			break;
		default:
			throw new AssertionError(mode);
		}
		return nextDouble;
	}

	@Override
	public long nextLong()
			throws IOException {
		final long nextLong;
		switch ( mode ) {
		case RECORDING:
			nextLong = super.nextLong();
			tokens.add(ValuedJsonToken.value(nextLong));
			break;
		case REPLAYING:
			final Number valueToReplay = replayValue(JsonToken.NUMBER);
			assert valueToReplay != null;
			nextLong = valueToReplay.longValue();
			break;
		case REPLAYED:
			nextLong = super.nextLong();
			break;
		default:
			throw new AssertionError(mode);
		}
		return nextLong;
	}

	@Override
	public int nextInt()
			throws IOException {
		final int nextInt;
		switch ( mode ) {
		case RECORDING:
			nextInt = super.nextInt();
			tokens.add(ValuedJsonToken.value(nextInt));
			break;
		case REPLAYING:
			final Number valueToReplay = replayValue(JsonToken.NUMBER);
			assert valueToReplay != null;
			nextInt = valueToReplay.intValue();
			break;
		case REPLAYED:
			nextInt = super.nextInt();
			break;
		default:
			throw new AssertionError(mode);
		}
		return nextInt;
	}

	@Override
	public void close()
			throws IOException {
		switch ( mode ) {
		case RECORDING:
			super.close();
			tokens.clear();
			break;
		case REPLAYING:
			super.close();
			tokens.clear();
			break;
		case REPLAYED:
			super.close();
			break;
		default:
			throw new AssertionError(mode);
		}
	}

	@Override
	public void skipValue()
			throws IOException {
		switch ( mode ) {
		case RECORDING:
			@SuppressWarnings("resource")
			final Iterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(this);
			while ( iterator.hasNext() ) {
				@SuppressWarnings("unused")
				final ValuedJsonToken<?> token = iterator.next();
			}
			break;
		case REPLAYING:
			final ValuedJsonToken<?> token = tokens.peek();
			switch ( token.getToken() ) {
			case BEGIN_ARRAY:
			case BEGIN_OBJECT:
				skipComplexValue();
				break;
			case NAME:
				skipEntry();
				break;
			case STRING:
			case NUMBER:
			case BOOLEAN:
			case NULL:
				skipSimpleValue();
				break;
			case END_ARRAY:
			case END_OBJECT:
			case END_DOCUMENT:
				throw new AssertionError();
			default:
				throw new AssertionError(token.getToken());
			}
			break;
		case REPLAYED:
			super.skipValue();
			break;
		default:
			throw new AssertionError(mode);
		}
	}

	@Override
	public String toString() {
		final String toString;
		switch ( mode ) {
		case RECORDING:
			throw NotImplementedYetException.create();
		case REPLAYING:
			throw NotImplementedYetException.create();
		case REPLAYED:
			toString = super.toString();
			break;
		default:
			throw new AssertionError(mode);
		}
		return toString;
	}

	@Override
	public String getPath() {
		final String getPath;
		switch ( mode ) {
		case RECORDING:
			throw NotImplementedYetException.create();
		case REPLAYING:
			throw NotImplementedYetException.create();
		case REPLAYED:
			getPath = super.getPath();
			break;
		default:
			throw new AssertionError(mode);
		}
		return getPath;
	}

	@Override
	public Iterator<ValuedJsonToken<?>> iterator() {
		return Iterators.unmodifiableIterator(tokens.iterator());
	}

	/**
	 * Stops the recording and resets the reader state to replay recorded JSON tokens.
	 *
	 * @since 0-SNAPSHOT
	 */
	public void pushback() {
		mode = Mode.REPLAYING;
	}

	private enum Mode {

		RECORDING,
		REPLAYING,
		REPLAYED

	}

	@Nullable
	private <T> T replayValue(final JsonToken expectedJsonToken) {
		@Nullable
		final Object valueToReplay;
		final ValuedJsonToken<?> valuedJsonToken = tokens.poll();
		final JsonToken actualToken = valuedJsonToken.getToken();
		if ( actualToken != expectedJsonToken ) {
			if ( actualToken == JsonToken.STRING && expectedJsonToken == JsonToken.NUMBER ) {
				@SuppressWarnings("unchecked")
				final ValuedJsonToken<String> stringValuedJsonToken = (ValuedJsonToken<String>) valuedJsonToken;
				valueToReplay = new LazilyParsedNumber(stringValuedJsonToken.getValue());
			} else {
				throw new IllegalStateException("Expected token: " + expectedJsonToken + " but was " + actualToken);
			}
		} else {
			valueToReplay = valuedJsonToken.getValue();
		}
		if ( tokens.isEmpty() ) {
			mode = Mode.REPLAYED;
		}
		@SuppressWarnings("unchecked")
		final T castValueToReplay = (T) valueToReplay;
		return castValueToReplay;
	}

	private void skipSimpleValue() {
		tokens.poll();
	}

	private void skipComplexValue() {
		for ( int level = 0; level > -1; ) {
			final JsonToken jsonToken = tokens.poll().getToken();
			switch ( jsonToken ) {
			case BEGIN_ARRAY:
			case BEGIN_OBJECT:
				level++;
				break;
			case END_ARRAY:
			case END_OBJECT:
				level--;
				if ( level == 0 ) {
					return;
				}
				break;
			case NAME:
			case STRING:
			case NUMBER:
			case BOOLEAN:
			case NULL:
				// do nothing
				break;
			case END_DOCUMENT:
				throw new AssertionError();
			default:
				throw new AssertionError(jsonToken);
			}
		}
	}

	private void skipEntry() {
		tokens.poll();
		tokens.poll();
	}

}
