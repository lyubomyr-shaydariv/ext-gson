package lsh.ext.gson.adapters;

import java.util.Iterator;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.mockito.Mockito;

public final class CloseableIteratorsTest {

	@Test
	public void testForEachAndTryCloseWhenIteratorIsNotAutoCloseable()
			throws Exception {
		final Iterator<String> iterator = ImmutableList.of("foo", "bar", "baz").iterator();
		@SuppressWarnings("unchecked")
		final Consumer<String> mockConsumer = Mockito.mock(Consumer.class);
		CloseableIterators.forEachAndTryClose(iterator, mockConsumer);
		Mockito.verify(mockConsumer).accept("foo");
		Mockito.verify(mockConsumer).accept("bar");
		Mockito.verify(mockConsumer).accept("baz");
		Mockito.verifyZeroInteractions(mockConsumer);
	}

	@Test
	@SuppressWarnings("resource")
	public void testForEachAndTryCloseWhenIteratorIsAutoCloseable()
			throws Exception {
		final AutoCloseable mockAutoCloseable = Mockito.mock(AutoCloseable.class);
		final AutoCloseableIterator<String> iterator = new AutoCloseableIterator<>(ImmutableList.of("foo", "bar", "baz").iterator(), mockAutoCloseable);
		@SuppressWarnings("unchecked")
		final Consumer<String> mockConsumer = Mockito.mock(Consumer.class);
		CloseableIterators.forEachAndTryClose(iterator, mockConsumer);
		Mockito.verify(mockConsumer).accept("foo");
		Mockito.verify(mockConsumer).accept("bar");
		Mockito.verify(mockConsumer).accept("baz");
		Mockito.verify(mockAutoCloseable).close();
		Mockito.verifyZeroInteractions(mockConsumer, mockAutoCloseable);
	}

	@Test
	@SuppressWarnings("resource")
	public void testTryClose()
			throws Exception {
		final AutoCloseable mockAutoCloseable = Mockito.mock(AutoCloseable.class);
		CloseableIterators.tryClose(mockAutoCloseable);
		Mockito.verify(mockAutoCloseable).close();
		Mockito.verifyZeroInteractions(mockAutoCloseable);
	}

	@Test
	public void testTryCloseNull()
			throws Exception {
		CloseableIterators.tryClose(null);
	}

	@Test
	public void testTryToCloseNotAutoCloseable()
			throws Exception {
		final Runnable mockRunnable = Mockito.mock(Runnable.class);
		CloseableIterators.tryClose(mockRunnable);
		Mockito.verifyZeroInteractions(mockRunnable);
	}

	private static final class AutoCloseableIterator<T>
			implements Iterator<T>, AutoCloseable {

		private final Iterator<T> iterator;
		private final AutoCloseable autoCloseable;

		private AutoCloseableIterator(final Iterator<T> iterator, final AutoCloseable autoCloseable) {
			this.iterator = iterator;
			this.autoCloseable = autoCloseable;
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public T next() {
			return iterator.next();
		}

		@Override
		public void close()
				throws Exception {
			autoCloseable.close();
		}

	}

}
