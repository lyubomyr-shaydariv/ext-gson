package lsh.ext.gson.ext.java;

import java.io.Closeable;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public final class CloseableIteratorsTest {

	@Test
	@SuppressWarnings("resource")
	public void testAsCloseableDispatcherForCloseableIterator() {
		final Iterator<?> mockIterator = Mockito.mock(ICloseableIterator.class);
		Assertions.assertSame(mockIterator, CloseableIterators.asCloseable(mockIterator));
		Mockito.verifyNoMoreInteractions(mockIterator);
	}

	@Test
	@SuppressWarnings("resource")
	public void testAsCloseableDispatcherForIterator()
			throws Exception {
		final Iterator<?> mockIterator = Mockito.mock(Iterator.class);
		final ICloseableIterator<?> closeableIterator = CloseableIterators.asCloseable(mockIterator);
		Assertions.assertInstanceOf(Iterator.class, closeableIterator);
		Assertions.assertInstanceOf(Closeable.class, closeableIterator);
		closeableIterator.hasNext();
		Mockito.verify(mockIterator).hasNext();
		closeableIterator.next();
		Mockito.verify(mockIterator).next();
		closeableIterator.close();
		Mockito.verifyNoMoreInteractions(mockIterator);
	}

}
