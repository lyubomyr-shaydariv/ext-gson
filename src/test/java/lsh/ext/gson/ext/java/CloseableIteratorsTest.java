package lsh.ext.gson.ext.java;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ICloseableIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public final class CloseableIteratorsTest {

	@Test
	@SuppressWarnings("resource")
	public void testAsCloseableForCloseableIterator() {
		final ICloseableIterator<?> closeableIterator = Mockito.mock(ICloseableIterator.class);
		Assertions.assertSame(closeableIterator, CloseableIterators.asCloseable(closeableIterator));
		Mockito.verifyNoMoreInteractions(closeableIterator);
	}

	@Test
	@SuppressWarnings("resource")
	public void testAsCloseableDispatcherForCloseableIterator() {
		final Iterator<?> iterator = Mockito.mock(ICloseableIterator.class);
		Assertions.assertSame(iterator, CloseableIterators.asCloseable(iterator));
		Mockito.verifyNoMoreInteractions(iterator);
	}

	@Test
	@SuppressWarnings("resource")
	public void testAsCloseableDispatcherForIterator()
			throws Exception {
		final Iterator<?> iterator = Mockito.mock(Iterator.class);
		final ICloseableIterator<?> closeableIterator = CloseableIterators.asCloseable(iterator);
		Assertions.assertInstanceOf(Iterator.class, closeableIterator);
		Assertions.assertInstanceOf(Closeable.class, closeableIterator);
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
		final Iterator<String> iterator = List.of("foo", "bar", "baz").iterator();
		@SuppressWarnings("unchecked")
		final Consumer<String> mockConsumer = Mockito.mock(Consumer.class);
		CloseableIterators.forEachAndTryClose(iterator, mockConsumer);
		Mockito.verify(mockConsumer).accept("foo");
		Mockito.verify(mockConsumer).accept("bar");
		Mockito.verify(mockConsumer).accept("baz");
		Mockito.verifyNoMoreInteractions(mockConsumer);
	}

	@Test
	@SuppressWarnings("resource")
	public void testForEachAndTryCloseWhenIteratorIsCloseable()
			throws Exception {
		final Closeable mockCloseable = Mockito.mock(Closeable.class);
		final Iterator<String> iterator = new CloseableIterator<>(List.of("foo", "bar", "baz").iterator(), mockCloseable);
		@SuppressWarnings("unchecked")
		final Consumer<String> mockConsumer = Mockito.mock(Consumer.class);
		CloseableIterators.forEachAndTryClose(iterator, mockConsumer);
		Mockito.verify(mockConsumer).accept("foo");
		Mockito.verify(mockConsumer).accept("bar");
		Mockito.verify(mockConsumer).accept("baz");
		Mockito.verify(mockCloseable).close();
		Mockito.verifyNoMoreInteractions(mockConsumer, mockCloseable);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class CloseableIterator<T>
			implements Iterator<T>, Closeable {

		private final Iterator<? extends T> iterator;
		private final Closeable closeable;

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
