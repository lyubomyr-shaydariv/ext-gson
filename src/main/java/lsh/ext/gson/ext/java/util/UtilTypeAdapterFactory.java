package lsh.ext.gson.ext.java.util;

import java.util.Enumeration;
import java.util.Iterator;

import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class UtilTypeAdapterFactory {

	public static <E> ITypeAdapterFactory<Iterator<E>> forIterator(final boolean useBeginEnd) {
		return ITypeAdapterFactory.forClassHierarchy(Iterator.class, typeResolver -> UtilTypeAdapter.forIterator(typeResolver.getTypeAdapter(0), useBeginEnd));
	}

	public static <E> ITypeAdapterFactory<Enumeration<E>> forEnumeration(final boolean useBeginEnd) {
		return ITypeAdapterFactory.forClassHierarchy(Enumeration.class, typeResolver -> UtilTypeAdapter.forEnumeration(typeResolver.getTypeAdapter(0), useBeginEnd));
	}

}
