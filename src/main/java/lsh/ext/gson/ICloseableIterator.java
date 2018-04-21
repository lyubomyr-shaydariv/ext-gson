package lsh.ext.gson;

import java.io.Closeable;
import java.util.Iterator;

public interface ICloseableIterator<E>
		extends Closeable, Iterator<E> {
}
