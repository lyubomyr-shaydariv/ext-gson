package lsh.ext.gson;

import java.io.Closeable;
import java.util.Enumeration;

public interface ICloseableEnumeration<E>
		extends Closeable, Enumeration<E> {
}
