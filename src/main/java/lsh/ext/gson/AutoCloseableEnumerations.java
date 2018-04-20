package lsh.ext.gson;

public final class AutoCloseableEnumerations {

	private AutoCloseableEnumerations() {
	}

	public static <E> IAutoCloseableEnumeration<E> from(final IAutoCloseableIterator<? extends E> iterator) {
		return new AutoCloseableEnumerationFromAutoCloseableIterator(iterator);
	}

	private static final class AutoCloseableEnumerationFromAutoCloseableIterator<E>
			implements IAutoCloseableEnumeration<E> {

		private final IAutoCloseableIterator<? extends E> iterator;

		private AutoCloseableEnumerationFromAutoCloseableIterator(final IAutoCloseableIterator<? extends E> iterator) {
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
				throws Exception {
			iterator.close();
		}

	}

}
