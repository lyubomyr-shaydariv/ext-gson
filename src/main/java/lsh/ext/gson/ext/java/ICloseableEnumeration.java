package lsh.ext.gson.ext.java;

import java.io.Closeable;
import java.util.Enumeration;

public interface ICloseableEnumeration<E>
		extends Closeable, Enumeration<E> {
}
