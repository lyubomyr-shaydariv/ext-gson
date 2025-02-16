package lsh.ext.gson.ext.java.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class UtilTypeAdapterFactory {

	public static <E> ITypeAdapterFactory<Iterator<E>> forIterator(final boolean useBeginEnd) {
		return ITypeAdapterFactory.forClassHierarchy(Iterator.class, provider -> UtilTypeAdapter.forIterator(provider.getTypeAdapter(0), useBeginEnd));
	}

	public static <E> ITypeAdapterFactory<Enumeration<E>> forEnumeration(final boolean useBeginEnd) {
		return ITypeAdapterFactory.forClassHierarchy(Enumeration.class, provider -> UtilTypeAdapter.forEnumeration(provider.getTypeAdapter(0), useBeginEnd));
	}

	public static ITypeAdapterFactory<Collection<Object>> defaultForCoercedCollection = forCoercedCollection(UtilTypeAdapterFactory::defaultBuilderFactoryForCoercedCollection);

	public static <E> ITypeAdapterFactory<Collection<E>> forCoercedCollection(final IBuilder1.ILookup<? super E, ? extends Collection<E>> builderLookup) {
		return ITypeAdapterFactory.forClassHierarchy(Collection.class, provider -> CoercedCollectionTypeAdapter.getInstance(provider.getTypeAdapter(0), builderLookup.lookup(provider.getTypeToken())));
	}

	public static <E> Supplier<IBuilder1<E, Collection<E>>> defaultBuilderFactoryForCoercedCollection(final TypeToken<? super Collection<E>> typeToken) {
		final Class<? super Collection<E>> rawType = typeToken.getRawType();
		if ( List.class.isAssignableFrom(rawType) ) {
			return () -> IBuilder1.fromCollection(new ArrayList<>());
		}
		if ( Set.class.isAssignableFrom(rawType) ) {
			return () -> IBuilder1.fromCollection(new HashSet<>());
		}
		return () -> IBuilder1.fromCollection(new ArrayList<>());
	}

}
