package lsh.ext.gson;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Provides closeable iterators utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @see lsh.ext.gson.adapters.AutoCloseableIteratorTypeAdapter
 * @see lsh.ext.gson.adapters.AutoCloseableIteratorTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class AutoCloseableIterators {

	private AutoCloseableIterators() {
	}

	/**
	 * @param iterator Iterator
	 * @param <E>      Iterator element type
	 *
	 * @return An iterator parameter being a more efficient overload for {@link #asAutoCloseable(Iterator)}.
	 *
	 * @see #asAutoCloseable(Iterator)
	 */
	public static <E> IAutoCloseableIterator<E> asAutoCloseable(final IAutoCloseableIterator<E> iterator) {
		return iterator;
	}

	/**
	 * @param iterator Iterator
	 * @param <E>      Iterator element type
	 *
	 * @return A new iterator if the iterator is not {@link IAutoCloseableIterator}, otherwise self.
	 *
	 * @see #asAutoCloseable(IAutoCloseableIterator)
	 */
	public static <E> IAutoCloseableIterator<E> asAutoCloseable(final Iterator<E> iterator) {
		if ( iterator instanceof IAutoCloseableIterator ) {
			@SuppressWarnings("unchecked")
			final IAutoCloseableIterator<E> autoCloseableIterator = (IAutoCloseableIterator<E>) iterator;
			return autoCloseableIterator;
		}
		return new AutoCloseableIterator<>(iterator);
	}

	/**
	 * Performs an action for each iterator element and tries to close the iterator using {@link #tryClose(Object)}.
	 *
	 * @param iterator Iterator elements to iterate over
	 * @param consumer An action to be performed for each element
	 * @param <E>      Iterator element type
	 *
	 * @throws Exception If an exception during {@link AutoCloseable#close()} occurs.
	 */
	public static <E> void forEachAndTryClose(final Iterator<? extends E> iterator, final Consumer<? super E> consumer)
			throws Exception {
		try {
			while ( iterator.hasNext() ) {
				consumer.accept(iterator.next());
			}
		} finally {
			tryClose(iterator);
		}
	}

	/**
	 * Tries to close the given object if it's a {@link AutoCloseable}.
	 *
	 * @param object Any object. If the object is {@link AutoCloseable}, {@link AutoCloseable#close()} is invoked upon the object. Otherwise ignored.
	 *
	 * @throws Exception If an exception during {@link AutoCloseable#close()} occurs.
	 */
	public static void tryClose(final Object object)
			throws Exception {
		if ( object instanceof AutoCloseable ) {
			((AutoCloseable) object).close();
		}
	}

	private static final class AutoCloseableIterator<E>
			implements IAutoCloseableIterator<E> {

		private final Iterator<E> iterator;

		private AutoCloseableIterator(final Iterator<E> iterator) {
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
				throws Exception {
			tryClose(iterator);
		}

	}

}
