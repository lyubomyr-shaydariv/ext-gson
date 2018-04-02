package lsh.ext.gson;

import java.util.Iterator;

public interface IAutoCloseableIterator<E>
		extends Iterator<E>, AutoCloseable {
}
