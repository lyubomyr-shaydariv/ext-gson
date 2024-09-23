package lsh.ext.gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import javax.annotation.WillNotClose;

import com.google.gson.TypeAdapter;
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

	public static <E> Iterator<E> asIterator(@WillNotClose final JsonReader in, final TypeAdapter<? extends E> elementTypeAdapter) {
		return new Iterator<>() {
			@Override
			public boolean hasNext() {
				try {
					return in.hasNext();
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}

			@Override
			public E next() {
				try {
					if ( !hasNext() ) {
						throw new NoSuchElementException(String.valueOf(in));
					}
					return elementTypeAdapter.read(in);
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
		};
	}

	public static PrimitiveIterator.OfInt asIntIterator(@WillNotClose final JsonReader in) {
		return new PrimitiveIterator.OfInt() {
			@Override
			public boolean hasNext() {
				try {
					return in.hasNext();
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}

			@Override
			public int nextInt() {
				try {
					if ( !hasNext() ) {
						throw new NoSuchElementException(String.valueOf(in));
					}
					return in.nextInt();
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
		};
	}

	public static PrimitiveIterator.OfLong asLongIterator(@WillNotClose final JsonReader in) {
		return new PrimitiveIterator.OfLong() {
			@Override
			public boolean hasNext() {
				try {
					return in.hasNext();
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}

			@Override
			public long nextLong() {
				try {
					if ( !hasNext() ) {
						throw new NoSuchElementException(String.valueOf(in));
					}
					return in.nextLong();
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
		};
	}

	public static PrimitiveIterator.OfDouble asDoubleIterator(@WillNotClose final JsonReader in) {
		return new PrimitiveIterator.OfDouble() {
			@Override
			public boolean hasNext() {
				try {
					return in.hasNext();
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}

			@Override
			public double nextDouble() {
				try {
					if ( !hasNext() ) {
						throw new NoSuchElementException(String.valueOf(in));
					}
					return in.nextDouble();
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
		};
	}

}
