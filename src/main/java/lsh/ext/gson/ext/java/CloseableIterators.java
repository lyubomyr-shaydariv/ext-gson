package lsh.ext.gson.ext.java;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

/**
 * Provides closeable iterators utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @see CloseableIteratorTypeAdapter
 * @see CloseableIteratorTypeAdapterFactory
 */
@UtilityClass
public final class CloseableIterators {

	/**
	 * @param iterator
	 * 		Iterator
	 * @param <E>
	 * 		Iterator element type
	 *
	 * @return A new iterator if the iterator is not {@link ICloseableIterator}, otherwise self.
	 *
	 * @see #asCloseable(ICloseableIterator)
	 */
	public static <E> ICloseableIterator<E> asCloseable(final Iterator<? extends E> iterator) {
		if ( iterator instanceof final ICloseableIterator<?> closeableIterator ) {
			@SuppressWarnings("unchecked")
			final ICloseableIterator<E> castCloseableIterator = (ICloseableIterator<E>) closeableIterator;
			return castCloseableIterator;
		}
		return new CloseableIterator<>(iterator);
	}

	/**
	 * @param enumeration
	 * 		Enumeration to convert
	 * @param <E>
	 * 		Element type
	 *
	 * @return An enumeration wrapped in a iterator.
	 */
	public static <E> ICloseableIterator<E> from(final ICloseableEnumeration<? extends E> enumeration) {
		return new CloseableIteratorFromCloseableEnumeration<>(enumeration);
	}

	private static void tryClose(final Object object)
			throws IOException {
		if ( object instanceof final Closeable closeable ) {
			closeable.close();
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class CloseableIterator<E>
			implements ICloseableIterator<E> {

		private final Iterator<? extends E> iterator;

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public E next() {
			return iterator.next();
		}

		@Override
		public void remove() {
			iterator.remove();
		}

		@Override
		public void close()
				throws IOException {
			tryClose(iterator);
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class CloseableIteratorFromCloseableEnumeration<E>
			implements ICloseableIterator<E> {

		private final ICloseableEnumeration<? extends E> enumeration;

		@Override
		public boolean hasNext() {
			return enumeration.hasMoreElements();
		}

		@Override
		public E next() {
			return enumeration.nextElement();
		}

		@Override
		public void close()
				throws IOException {
			tryClose(enumeration);
		}

	}

}
