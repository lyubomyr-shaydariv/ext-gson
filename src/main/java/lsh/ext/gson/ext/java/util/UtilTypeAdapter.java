package lsh.ext.gson.ext.java.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.function.Function;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.JsonArrayStreamTypeAdapter;

@UtilityClass
public final class UtilTypeAdapter {

	public static <E> TypeAdapter<Iterator<E>> forIterator(final TypeAdapter<E> elementTypeAdapter, final boolean useBeginEnd) {
		return JsonArrayStreamTypeAdapter.forArray(elementTypeAdapter, useBeginEnd, (jsonReader, iterator) -> iterator, Function.identity());
	}

	public static <E> TypeAdapter<Enumeration<E>> forEnumeration(final TypeAdapter<E> elementTypeAdapter, final boolean useBeginEnd) {
		return JsonArrayStreamTypeAdapter.forArray(elementTypeAdapter, useBeginEnd, (jsonReader, iterator) -> new Enumeration<>() {
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public E nextElement() {
				return iterator.next();
			}
		}, enumeration -> new Iterator<>() {
			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public E next() {
				return enumeration.nextElement();
			}
		});
	}

}
