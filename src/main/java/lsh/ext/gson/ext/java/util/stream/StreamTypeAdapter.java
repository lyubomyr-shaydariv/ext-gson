package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lsh.ext.gson.AbstractElementCursorTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.JsonReaders;

public final class StreamTypeAdapter<E>
		extends AbstractElementCursorTypeAdapter<Stream<? extends E>, Iterator<? extends E>, E> {

	private StreamTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		super(elementTypeAdapter);
	}

	public static <E> TypeAdapter<Stream<? extends E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
		return new StreamTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	protected Stream<E> toCursor(final JsonReader jsonReader, final TypeAdapter<E> elementTypeAdapter) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(JsonReaders.asIterator(jsonReader, elementTypeAdapter), Spliterator.IMMUTABLE), false);
	}

	@Override
	protected Iterator<? extends E> toElementCursor(final Stream<? extends E> cursor) {
		return cursor.iterator();
	}

	@Override
	protected boolean hasNext(final Iterator<? extends E> elementCursor) {
		return elementCursor.hasNext();
	}

	@Override
	protected void writeNext(final JsonWriter out, final Iterator<? extends E> elementCursor, final TypeAdapter<E> elementTypeAdapter)
			throws IOException {
		elementTypeAdapter.write(out, elementCursor.next());
	}

	public static final class Factory<E>
			extends AbstractElementTypeAdapterFactory<E> {

		@Getter
		private static final ITypeAdapterFactory<? extends Stream<?>> instance = new Factory<>();

		private Factory() {
			super(Stream.class);
		}

		@Override
		protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
			return StreamTypeAdapter.getInstance(elementTypeAdapter);
		}

	}

}
