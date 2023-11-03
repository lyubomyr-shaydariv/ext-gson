package lsh.ext.gson.ext.java.util;

import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapter;

public final class IteratorTypeAdapter<E>
		extends AbstractCursorTypeAdapter<Iterator<? extends E>, E> {

	private IteratorTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		super(elementTypeAdapter);
	}

	public static <E> TypeAdapter<Iterator<? extends E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
		return new IteratorTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	protected Iterator<E> toIterator(final Iterator<? extends E> iterator) {
		@SuppressWarnings("unchecked")
		final Iterator<E> castIterator = (Iterator<E>) iterator;
		return castIterator;
	}

	@Override
	protected Iterator<E> fromIterator(final Iterator<? extends E> iterator) {
		@SuppressWarnings("unchecked")
		final Iterator<E> castIterator = (Iterator<E>) iterator;
		return castIterator;
	}

	public static final class Factory<E>
			extends AbstractCursorTypeAdapter.AbstractFactory<E> {

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
