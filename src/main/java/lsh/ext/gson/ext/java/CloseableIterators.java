package lsh.ext.gson.ext.java;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

/**
 * Provides closeable iterators utility methods.
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
	 */
	public static <E> ICloseableIterator<E> asCloseable(final Iterator<? extends E> iterator) {
		if ( iterator instanceof final ICloseableIterator<?> closeableIterator ) {
			@SuppressWarnings("unchecked")
			final ICloseableIterator<E> castCloseableIterator = (ICloseableIterator<E>) closeableIterator;
			return castCloseableIterator;
		}
		return new CloseableIterator<>(iterator);
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

}
