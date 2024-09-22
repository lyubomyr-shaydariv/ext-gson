package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lsh.ext.gson.AbstractElementCursorTypeAdapter;
import lsh.ext.gson.JsonReaders;

public final class EnumerationTypeAdapter<E>
		extends AbstractElementCursorTypeAdapter<Enumeration<? extends E>, Enumeration<? extends E>, E> {

	private EnumerationTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		super(elementTypeAdapter);
	}

	public static <E> TypeAdapter<Enumeration<? extends E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
		return new EnumerationTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	protected Enumeration<? extends E> toCursor(final JsonReader jsonReader, final TypeAdapter<E> elementTypeAdapter) {
		final Iterator<E> iterator = JsonReaders.asIterator(jsonReader, elementTypeAdapter);
		return new Enumeration<>() {
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public E nextElement() {
				return iterator.next();
			}
		};
	}

	@Override
	protected Enumeration<? extends E> toElementCursor(final Enumeration<? extends E> cursor) {
		return cursor;
	}

	@Override
	protected boolean hasNext(final Enumeration<? extends E> elementCursor) {
		return elementCursor.hasMoreElements();
	}

	@Override
	protected void writeNext(final JsonWriter out, final Enumeration<? extends E> elementCursor, final TypeAdapter<E> elementTypeAdapter)
			throws IOException {
		elementTypeAdapter.write(out, elementCursor.nextElement());
	}

	public static final class Factory<E>
			extends AbstractElementCursorTypeAdapter.AbstractElementTypeAdapterFactory<E> {

		@Getter
		private static final TypeAdapterFactory instance = new Factory<>();

		private Factory() {
			super(Enumeration.class);
		}

		@Override
		protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
			return EnumerationTypeAdapter.getInstance(elementTypeAdapter);
		}

	}

}
