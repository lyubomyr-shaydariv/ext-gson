package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Functions;
import com.google.common.collect.BiMap;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.ITypeAdapterFactory;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.collections4.multiset.HashMultiSet;

@UtilityClass
public final class ApacheCommonsCollectionsTypeAdapterFactory {

	public static ITypeAdapterFactory<Bag<String>> defaultForBag = forBag(ApacheCommonsCollectionsTypeAdapterFactory::defaultBuilderForBag);

	public static ITypeAdapterFactory<Bag<String>> forBag(
			final IBuilder2.ILookup<? super String, ? super Integer, ? extends Bag<String>> lookup
	) {
		return forBag(lookup, Function.identity(), Function.identity());
	}

	public static <E> ITypeAdapterFactory<Bag<E>> forBag(
			final IBuilder2.ILookup<? super E, ? super Integer, ? extends Bag<E>> lookup,
			final Function<? super E, String> toKey,
			final Function<? super String, ? extends E> fromKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(Bag.class, typeResolver -> BagTypeAdapter.getInstance(lookup.lookup(typeResolver.getTypeToken()), toKey, fromKey));
	}

	// TODO handle all known implementations
	public static <E> Supplier<IBuilder2<E, Integer, Bag<E>>> defaultBuilderForBag(final TypeToken<? super Bag<E>> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? super Bag<?>> rawType = (Class<? super Bag<?>>) typeToken.getRawType();
		if ( HashBag.class.isAssignableFrom(rawType) ) {
			return () -> Builder.forBag(new HashBag<>());
		}
		return () -> Builder.forBag(new HashBag<>());
	}

	public static final ITypeAdapterFactory<BidiMap<String, Object>> defaultForBidiMap = forBidiMap(ApacheCommonsCollectionsTypeAdapterFactory::defaultBuilderForBidiMap);

	public static <V> ITypeAdapterFactory<BidiMap<String, V>> forBidiMap(
			final IBuilder2.ILookup<? super String, ? super V, ? extends BidiMap<String, V>> lookup
	) {
		return forBidiMap(lookup, Functions.identity(), Functions.identity());
	}

	public static <K, V> ITypeAdapterFactory<BidiMap<K, V>> forBidiMap(
			final IBuilder2.ILookup<? super K, ? super V, ? extends BidiMap<K, V>> lookup,
			final Function<? super K, String> toKey,
			final Function<? super String, ? extends K> fromKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(BiMap.class, typeResolver -> BidiMapTypeAdapter.getInstance(typeResolver.getTypeAdapter(1), lookup.lookup(typeResolver.getTypeToken()), toKey, fromKey));
	}

	// TODO handle all known implementations
	public static <V> Supplier<IBuilder2<String, V, BidiMap<String, V>>> defaultBuilderForBidiMap(final TypeToken<? super BidiMap<String, V>> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? extends BidiMap<?, ?>> rawType = (Class<? extends BidiMap<?, ?>>) typeToken.getRawType();
		if ( DualHashBidiMap.class.isAssignableFrom(rawType) ) {
			return () -> IBuilder2.of(new DualHashBidiMap<>());
		}
		if ( DualLinkedHashBidiMap.class.isAssignableFrom(rawType) ) {
			return () -> IBuilder2.of(new DualLinkedHashBidiMap<>());
		}
		return () -> IBuilder2.of(new DualHashBidiMap<>());
	}

	public static ITypeAdapterFactory<MultiSet<Object>> defaultForMultiSet = forMultiSet(ApacheCommonsCollectionsTypeAdapterFactory::defaultBuilderForMultiSet);

	public static <E> ITypeAdapterFactory<MultiSet<E>> forMultiSet(
			final IBuilder1.ILookup<? super E, ? extends MultiSet<E>> lookup
	) {
		return ITypeAdapterFactory.forClassHierarchy(MultiSet.class, typeResolver -> MultiSetTypeAdapter.getInstance(typeResolver.getTypeAdapter(0), lookup.lookup(typeResolver.getTypeToken())));
	}

	// TODO handle all known implementations
	public static <E> Supplier<IBuilder1<E, MultiSet<E>>> defaultBuilderForMultiSet(final TypeToken<? super MultiSet<E>> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? extends MultiSet<?>> rawType = (Class<? extends MultiSet<?>>) typeToken.getRawType();
		if ( HashMultiSet.class.isAssignableFrom(rawType) ) {
			return () -> IBuilder1.of(new HashMultiSet<>());
		}
		return () -> IBuilder1.of(new HashMultiSet<>());
	}

	public static final ITypeAdapterFactory<MultiValuedMap<String, Object>> defaultForMultiValueMap = forMultiValueMap(ApacheCommonsCollectionsTypeAdapterFactory::defaultBuilderForMultiValuedMap);

	public static <V> ITypeAdapterFactory<MultiValuedMap<String, V>> forMultiValueMap(
			final IBuilder2.ILookup<? super String, ? super V, ? extends MultiValuedMap<String, V>> builderLookup
	) {
		return forMultiValueMap(builderLookup, Function.identity(), Function.identity());
	}

	public static <K, V> ITypeAdapterFactory<MultiValuedMap<K, V>> forMultiValueMap(
			final IBuilder2.ILookup<? super K, ? super V, ? extends MultiValuedMap<K, V>> builderLookup,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(MultiValuedMap.class, typeResolver -> MultiValuedMapTypeAdapter.getInstance(typeResolver.getTypeAdapter(1), builderLookup.lookup(typeResolver.getTypeToken()), encodeKey, decodeKey));
	}

	// TODO handle all known implementations
	public static <V> Supplier<IBuilder2<String, V, MultiValuedMap<String, V>>> defaultBuilderForMultiValuedMap(final TypeToken<? super MultiValuedMap<String, V>> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? extends MultiValuedMap<?, ?>> rawType = (Class<? extends MultiValuedMap<?, ?>>) typeToken.getRawType();
		if ( ArrayListValuedHashMap.class.isAssignableFrom(rawType) ) {
			return () -> Builder.forMultiValuedMap(new ArrayListValuedHashMap<>());
		}
		if ( HashSetValuedHashMap.class.isAssignableFrom(rawType) ) {
			return () -> Builder.forMultiValuedMap(new HashSetValuedHashMap<>());
		}
		return () -> Builder.forMultiValuedMap(new ArrayListValuedHashMap<>());
	}

}
