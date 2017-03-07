package lsh.ext.gson.adapters;

import java.util.Iterator;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static lsh.ext.gson.adapters.CloseableIterators.forEachAndTryClose;
import static lsh.ext.gson.adapters.CloseableIterators.tryClose;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public final class CloseableIteratorsTest {

	@Test
	public void testForEachAndTryCloseWhenIteratorIsNotAutoCloseable()
			throws Exception {
		final Iterator<String> iterator = ImmutableList.of("foo", "bar", "baz").iterator();
		@SuppressWarnings("unchecked")
		final Consumer<String> mockConsumer = mock(Consumer.class);
		forEachAndTryClose(iterator, mockConsumer);
		verify(mockConsumer).accept("foo");
		verify(mockConsumer).accept("bar");
		verify(mockConsumer).accept("baz");
		verifyZeroInteractions(mockConsumer);
	}

	@Test
	@SuppressWarnings("resource")
	public void testForEachAndTryCloseWhenIteratorIsAutoCloseable()
			throws Exception {
		final AutoCloseable mockAutoCloseable = mock(AutoCloseable.class);
		final AutoCloseableIterator<String> iterator = new AutoCloseableIterator<>(ImmutableList.of("foo", "bar", "baz").iterator(), mockAutoCloseable);
		@SuppressWarnings("unchecked")
		final Consumer<String> mockConsumer = mock(Consumer.class);
		forEachAndTryClose(iterator, mockConsumer);
		verify(mockConsumer).accept("foo");
		verify(mockConsumer).accept("bar");
		verify(mockConsumer).accept("baz");
		verify(mockAutoCloseable).close();
		verifyZeroInteractions(mockConsumer, mockAutoCloseable);
	}

	@Test
	@SuppressWarnings("resource")
	public void testTryClose()
			throws Exception {
		final AutoCloseable mockAutoCloseable = mock(AutoCloseable.class);
		tryClose(mockAutoCloseable);
		verify(mockAutoCloseable).close();
		verifyZeroInteractions(mockAutoCloseable);
	}

	@Test
	public void testTryCloseNull()
			throws Exception {
		tryClose(null);
	}

	@Test
	public void testTryToCloseNotAutoCloseable()
			throws Exception {
		final Runnable mockRunnable = mock(Runnable.class);
		tryClose(mockRunnable);
		verifyZeroInteractions(mockRunnable);
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
