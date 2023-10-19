package lsh.ext.gson.ext.java.util;

import java.util.Enumeration;
import java.util.Iterator;

import lombok.experimental.UtilityClass;

@UtilityClass
final class Enumerations {

	static <E> Enumeration<E> from(final Iterator<? extends E> iterator) {
		return new Enumeration<>() {
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public E nextElement() {
				return iterator.next();
			}
		};
	}

}
