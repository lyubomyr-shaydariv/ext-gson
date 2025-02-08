package lsh.ext.gson.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Classes {

	public static Iterator<Class<?>> forHierarchyDown(final Class<?> klass) {
		final List<Class<?>> classes = new ArrayList<>();
		for ( Class<?> c = klass; c != null; c = c.getSuperclass() ) {
			classes.add(0, c);
		}
		return UnmodifiableIterator.of(classes.iterator());
	}

}
