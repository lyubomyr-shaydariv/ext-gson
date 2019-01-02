package lsh.ext.gson;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;

import lsh.ext.gson.adapters.CloseableIteratorTypeAdapter;
import lsh.ext.gson.adapters.CloseableIteratorTypeAdapterFactory;

/**
 * Provides closeable iterators utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @see CloseableIteratorTypeAdapter
 * @see CloseableIteratorTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class CloseableIterators {

	private CloseableIterators() {
	}

	/**
	 * @param iterator Iterator
	 * @param <E>      Iterator element type
	 *
	 * @return An iterator parameter being a more efficient overload for {@link #asCloseable(Iterator)}.
	 *
	 * @see #asCloseable(Iterator)
	 */
	public static <E> ICloseableIterator<E> asCloseable(final ICloseableIterator<E> iterator) {
		return iterator;
	}

	/**
	 * @param iterator Iterator
	 * @param <E>      Iterator element type
	 *
	 * @return A new iterator if the iterator is not {@link ICloseableIterator}, otherwise self.
	 *
	 * @see #asCloseable(ICloseableIterator)
	 */
	public static <E> ICloseableIterator<E> asCloseable(final Iterator<? extends E> iterator) {
		if ( iterator instanceof ICloseableIterator ) {
			@SuppressWarnings("unchecked")
			final ICloseableIterator<E> closeableIterator = (ICloseableIterator<E>) iterator;
			return closeableIterator;
		}
		return new CloseableIterator<>(iterator);
	}

	/**
	 * Performs an action for each iterator element and tries to close the iterator using {@link #tryClose(Object)}.
	 *
	 * @param iterator Iterator elements to iterate over
	 * @param consumer An action to be performed for each element
	 * @param <E>      Iterator element type
	 *
	 * @throws IOException If an exception during {@link Closeable#close()} occurs.
	 */
	public static <E> void forEachAndTryClose(final Iterator<? extends E> iterator, final Consumer<? super E> consumer)
			throws IOException {
		try {
			while ( iterator.hasNext() ) {
				consumer.accept(iterator.next());
			}
		} finally {
			tryClose(iterator);
		}
	}

	/**
	 * @param enumeration Enumeration to convert
	 * @param <E>         Element type
	 *
	 * @return An enumeration wrapped in a iterator.
	 */
	public static <E> ICloseableIterator<E> from(final ICloseableEnumeration<? extends E> enumeration) {
		return new CloseableIteratorFromCloseableEnumeration<>(enumeration);
	}

	private static void tryClose(final Object object)
			throws IOException {
		if ( object instanceof Closeable ) {
			((Closeable) object).close();
		}
	}

	private static final class CloseableIterator<E>
			implements ICloseableIterator<E> {

		private final Iterator<? extends E> iterator;

		private CloseableIterator(final Iterator<? extends E> iterator) {
			this.iterator = iterator;
		}

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

	private static final class CloseableIteratorFromCloseableEnumeration<E>
			implements ICloseableIterator<E> {

		private final ICloseableEnumeration<? extends E> enumeration;

		private CloseableIteratorFromCloseableEnumeration(final ICloseableEnumeration<? extends E> enumeration) {
			this.enumeration = enumeration;
		}

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
