package lsh.ext.gson.ext.java.util;

import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapterFactory;

public final class IteratorTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	@Getter
	private static final TypeAdapterFactory instance = new IteratorTypeAdapterFactory<>();

	private IteratorTypeAdapterFactory() {
		super(Iterator.class);
	}

	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
		return Adapter.getInstance(elementTypeAdapter);
	}

	public static final class Adapter<E>
			extends AbstractAdapter<Iterator<E>, E> {

		private Adapter(final TypeAdapter<E> elementTypeAdapter) {
			super(elementTypeAdapter);
		}

		public static <E> TypeAdapter<Iterator<E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
			return new Adapter<>(elementTypeAdapter)
					.nullSafe();
		}

		@Override
		protected Iterator<E> toIterator(final Iterator<E> iterator) {
			return iterator;
		}

		@Override
		protected Iterator<E> fromIterator(final Iterator<E> iterator) {
			return iterator;
		}

	}

}
