package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lsh.ext.gson.JsonReaders;
import lsh.ext.gson.AbstractElementCursorTypeAdapter;

public final class IteratorTypeAdapter<E>
		extends AbstractElementCursorTypeAdapter<Iterator<? extends E>, Iterator<? extends E>> {

	private final TypeAdapter<E> elementTypeAdapter;

	private IteratorTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		this.elementTypeAdapter = elementTypeAdapter;
	}

	public static <E> TypeAdapter<Iterator<? extends E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
		return new IteratorTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	protected Iterator<? extends E> toCursor(final JsonReader jsonReader) {
		return JsonReaders.asIterator(jsonReader, elementTypeAdapter);
	}

	@Override
	protected Iterator<? extends E> toElementCursor(final Iterator<? extends E> cursor) {
		return cursor;
	}

	@Override
	protected boolean hasNext(final Iterator<? extends E> elementCursor) {
		return elementCursor.hasNext();
	}

	@Override
	protected void writeNext(final JsonWriter out, final Iterator<? extends E> elementCursor)
			throws IOException {
		elementTypeAdapter.write(out, elementCursor.next());
	}

	public static final class Factory<E>
			extends AbstractElementTypeAdapterFactory<E> {

		@Getter
		private static final TypeAdapterFactory instance = new Factory<>();

		private Factory() {
			super(Iterator.class);
		}

		@Override
		protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
			return IteratorTypeAdapter.getInstance(elementTypeAdapter);
		}

	}

}
