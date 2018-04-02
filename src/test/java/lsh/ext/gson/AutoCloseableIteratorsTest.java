package lsh.ext.gson;

import java.util.Iterator;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

public final class AutoCloseableIteratorsTest {

	@Test
	@SuppressWarnings("resource")
	public void testAsAutoCloseableForAutoCloseableIterator() {
		final IAutoCloseableIterator<?> autoCloseableIterator = Mockito.mock(IAutoCloseableIterator.class);
		MatcherAssert.assertThat(AutoCloseableIterators.asAutoCloseable(autoCloseableIterator), CoreMatchers.sameInstance(autoCloseableIterator));
	}

	@Test
	@SuppressWarnings("resource")
	public void testAsAutoCloseableDispatcherForAutoCloseableIterator() {
		final Iterator<?> iterator = Mockito.mock(IAutoCloseableIterator.class);
		MatcherAssert.assertThat(AutoCloseableIterators.asAutoCloseable(iterator), CoreMatchers.sameInstance(iterator));
	}

	@Test
	@SuppressWarnings("resource")
	public void testAsAutoCloseableDispatcherForIterator()
			throws Exception {
		final Iterator<?> iterator = Mockito.mock(Iterator.class);
		final IAutoCloseableIterator<?> autoCloseableIterator = AutoCloseableIterators.asAutoCloseable(iterator);
		MatcherAssert.assertThat(autoCloseableIterator, CoreMatchers.isA(Iterator.class));
		MatcherAssert.assertThat(autoCloseableIterator, CoreMatchers.isA(AutoCloseable.class));
		autoCloseableIterator.hasNext();
		Mockito.verify(iterator).hasNext();
		autoCloseableIterator.next();
		Mockito.verify(iterator).next();
		autoCloseableIterator.close();
		Mockito.verifyNoMoreInteractions(iterator);
	}

	@Test
	public void testForEachAndTryCloseWhenIteratorIsNotAutoCloseable()
			throws Exception {
		final Iterator<String> iterator = ImmutableList.of("foo", "bar", "baz").iterator();
		@SuppressWarnings("unchecked")
		final Consumer<String> mockConsumer = Mockito.mock(Consumer.class);
		AutoCloseableIterators.forEachAndTryClose(iterator, mockConsumer);
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
		AutoCloseableIterators.forEachAndTryClose(iterator, mockConsumer);
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
		AutoCloseableIterators.tryClose(mockAutoCloseable);
		Mockito.verify(mockAutoCloseable).close();
		Mockito.verifyZeroInteractions(mockAutoCloseable);
	}

	@Test
	public void testTryCloseNull()
			throws Exception {
		AutoCloseableIterators.tryClose(null);
	}

	@Test
	public void testTryToCloseNotAutoCloseable()
			throws Exception {
		final Runnable mockRunnable = Mockito.mock(Runnable.class);
		AutoCloseableIterators.tryClose(mockRunnable);
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
