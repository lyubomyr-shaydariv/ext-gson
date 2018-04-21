package lsh.ext.gson;

import java.io.IOException;

public final class CloseableEnumerations {

	private CloseableEnumerations() {
	}

	public static <E> ICloseableEnumeration<E> from(final ICloseableIterator<? extends E> iterator) {
		return new CloseableEnumerationFromCloseableIterator<>(iterator);
	}

	private static final class CloseableEnumerationFromCloseableIterator<E>
			implements ICloseableEnumeration<E> {

		private final ICloseableIterator<? extends E> iterator;

		private CloseableEnumerationFromCloseableIterator(final ICloseableIterator<? extends E> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasMoreElements() {
			return iterator.hasNext();
		}

		@Override
		public E nextElement() {
			return iterator.next();
		}

		@Override
		public void close()
				throws IOException {
			CloseableIterators.tryClose(iterator);
		}

	}

}
