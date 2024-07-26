package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Enumeration;

import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lsh.ext.gson.AbstractElementCursorTypeAdapter;

public final class EnumerationTypeAdapter<E>
		extends AbstractElementCursorTypeAdapter<Enumeration<? extends E>, Enumeration<? extends E>> {

	private final TypeAdapter<E> elementTypeAdapter;

	private EnumerationTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		this.elementTypeAdapter = elementTypeAdapter;
	}

	public static <E> TypeAdapter<Enumeration<? extends E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
		return new EnumerationTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	protected Enumeration<? extends E> toCursor(final JsonReader jsonReader) {
		return new Enumeration<>() {
			@Override
			public boolean hasMoreElements() {
				try {
					return jsonReader.hasNext();
				} catch ( final IOException ex ) {
					throw new JsonIOException(ex);
				}
			}

			@Override
			public E nextElement() {
				try {
					return elementTypeAdapter.read(jsonReader);
				} catch ( final IOException ex ) {
					throw new JsonIOException(ex);
				}
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
	protected void writeNext(final JsonWriter out, final Enumeration<? extends E> elementCursor)
			throws IOException {
		elementTypeAdapter.write(out, elementCursor.nextElement());
	}

	public static final class Factory<E>
			extends AbstractElementTypeAdapterFactory<E> {

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
