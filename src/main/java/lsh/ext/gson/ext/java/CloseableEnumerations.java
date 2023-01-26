package lsh.ext.gson.ext.java;

import java.io.IOException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class CloseableEnumerations {

	public static <E> ICloseableEnumeration<E> from(final ICloseableIterator<? extends E> iterator) {
		return new CloseableEnumerationFromCloseableIterator<>(iterator);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class CloseableEnumerationFromCloseableIterator<E>
			implements ICloseableEnumeration<E> {

		private final ICloseableIterator<? extends E> iterator;

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
			iterator.close();
		}

	}

}
