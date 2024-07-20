package lsh.ext.gson.ext.java.util;

import java.util.Enumeration;
import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapter;

public final class EnumerationTypeAdapter<E>
		extends AbstractCursorTypeAdapter<Enumeration<? extends E>, E> {

	private EnumerationTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		super(elementTypeAdapter);
	}

	public static <E> TypeAdapter<Enumeration<? extends E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
		return new EnumerationTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	protected Iterator<E> toIterator(final Enumeration<? extends E> enumeration) {
		return new Iterator<>() {
			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public E next() {
				return enumeration.nextElement();
			}
		};
	}

	@Override
	protected Enumeration<E> fromIterator(final Iterator<? extends E> iterator) {
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

	public static final class Factory<E>
			extends AbstractCursorTypeAdapter.AbstractFactory<E> {

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
