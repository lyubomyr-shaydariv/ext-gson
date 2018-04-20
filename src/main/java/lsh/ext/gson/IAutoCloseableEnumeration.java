package lsh.ext.gson;

import java.util.Enumeration;

public interface IAutoCloseableEnumeration<E>
		extends Enumeration<E>, AutoCloseable {
}
