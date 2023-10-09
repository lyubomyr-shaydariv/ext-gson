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

}
