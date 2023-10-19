package lsh.ext.gson.ext.java.util;

import java.util.Enumeration;
import java.util.Iterator;

import lombok.experimental.UtilityClass;

@UtilityClass
final class Iterators {

	static <E> Iterator<E> from(final Enumeration<? extends E> enumeration) {
		return new Iterator<>() {
			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public E next() {
				return enumeration.nextElement();
			}
		};
	}

}
