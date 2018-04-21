package lsh.ext.gson;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

public final class CloseableIteratorsTest {

	@Test
	@SuppressWarnings("resource")
	public void testAsCloseableForCloseableIterator() {
		final ICloseableIterator<?> closeableIterator = Mockito.mock(ICloseableIterator.class);
		MatcherAssert.assertThat(CloseableIterators.asCloseable(closeableIterator), CoreMatchers.sameInstance(closeableIterator));
	}

	@Test
	@SuppressWarnings("resource")
	public void testAsCloseableDispatcherForCloseableIterator() {
		final Iterator<?> iterator = Mockito.mock(ICloseableIterator.class);
		MatcherAssert.assertThat(CloseableIterators.asCloseable(iterator), CoreMatchers.sameInstance(iterator));
	}

	@Test
	@SuppressWarnings("resource")
	public void testAsCloseableDispatcherForIterator()
			throws Exception {
		final Iterator<?> iterator = Mockito.mock(Iterator.class);
		final ICloseableIterator<?> closeableIterator = CloseableIterators.asCloseable(iterator);
		MatcherAssert.assertThat(closeableIterator, CoreMatchers.isA(Iterator.class));
		MatcherAssert.assertThat(closeableIterator, CoreMatchers.isA(Closeable.class));
		closeableIterator.hasNext();
		Mockito.verify(iterator).hasNext();
		closeableIterator.next();
		Mockito.verify(iterator).next();
		closeableIterator.close();
		Mockito.verifyNoMoreInteractions(iterator);
	}

	@Test
	public void testForEachAndTryCloseWhenIteratorIsNotCloseable()
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
	public void testForEachAndTryCloseWhenIteratorIsCloseable()
			throws Exception {
		final Closeable mockCloseable = Mockito.mock(Closeable.class);
		final Iterator<String> iterator = new CloseableIterator<>(ImmutableList.of("foo", "bar", "baz").iterator(), mockCloseable);
		@SuppressWarnings("unchecked")
		final Consumer<String> mockConsumer = Mockito.mock(Consumer.class);
		CloseableIterators.forEachAndTryClose(iterator, mockConsumer);
		Mockito.verify(mockConsumer).accept("foo");
		Mockito.verify(mockConsumer).accept("bar");
		Mockito.verify(mockConsumer).accept("baz");
		Mockito.verify(mockCloseable).close();
		Mockito.verifyZeroInteractions(mockConsumer, mockCloseable);
	}

	@Test
	@SuppressWarnings("resource")
	public void testTryClose()
			throws Exception {
		final Closeable mockCloseable = Mockito.mock(Closeable.class);
		CloseableIterators.tryClose(mockCloseable);
		Mockito.verify(mockCloseable).close();
		Mockito.verifyZeroInteractions(mockCloseable);
	}

	@Test
	public void testTryCloseNull()
			throws Exception {
		CloseableIterators.tryClose(null);
	}

	@Test
	public void testTryToCloseNotCloseable()
			throws Exception {
		final Runnable mockRunnable = Mockito.mock(Runnable.class);
		CloseableIterators.tryClose(mockRunnable);
		Mockito.verifyZeroInteractions(mockRunnable);
	}

	private static final class CloseableIterator<T>
			implements Iterator<T>, Closeable {

		private final Iterator<T> iterator;
		private final Closeable closeable;

		private CloseableIterator(final Iterator<T> iterator, final Closeable closeable) {
			this.iterator = iterator;
			this.closeable = closeable;
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
				throws IOException {
			closeable.close();
		}

	}

}
