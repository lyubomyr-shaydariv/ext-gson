package lsh.ext.gson.ext.com.google.common.collect;

import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IBuilder3;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class GuavaCollectTypeAdapterFactory {

	public static final ITypeAdapterFactory<BiMap<String, Object>> defaultForBiMap = forBiMap(GuavaCollectTypeAdapterFactory::defaultBuilderFactoryForBiMap);

	public static <V> ITypeAdapterFactory<BiMap<String, V>> forBiMap(
			final IBuilder2.ILookup<? super String, ? super V, ? extends BiMap<String, V>> lookup
	) {
		return forBiMap(lookup, Function.identity(), Function.identity());
	}

	public static <K, V> ITypeAdapterFactory<BiMap<K, V>> forBiMap(
			final IBuilder2.ILookup<? super K, ? super V, ? extends BiMap<K, V>> lookup,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(BiMap.class, typeResolver -> GuavaCollectTypeAdapter.forBiMap(
				typeResolver.getTypeAdapter(1),
				lookup.lookup(typeResolver.getTypeToken()),
				encodeKey,
				decodeKey
		));
	}

	// TODO handle all known implementations
	public static <K, V> Supplier<IBuilder2<K, V, BiMap<K, V>>> defaultBuilderFactoryForBiMap(final TypeToken<? super BiMap<K, V>> typeToken) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends BiMap> rawType = (Class<? extends BiMap>) typeToken.getRawType();
		if ( rawType == HashBiMap.class ) {
			return () -> IBuilder2.of(HashBiMap.create());
		}
		if ( ImmutableBiMap.class.isAssignableFrom(rawType) ) {
			return ImmutableBuilder::forBiMap;
		}
		return () -> IBuilder2.of(HashBiMap.create());
	}

	public static final ITypeAdapterFactory<Multimap<String, Object>> defaultForMultimap = forMultimap(GuavaCollectTypeAdapterFactory::defaultBuilderFactoryForMultimap);

	public static <V> ITypeAdapterFactory<Multimap<String, V>> forMultimap(
			final IBuilder2.ILookup<? super String, ? super V, ? extends Multimap<String, V>> lookup
	) {
		return forMultimap(lookup, Function.identity(), Function.identity());
	}

	public static <K, V> ITypeAdapterFactory<Multimap<K, V>> forMultimap(
			final IBuilder2.ILookup<? super K, ? super V, ? extends Multimap<K, V>> lookup,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(Multimap.class, typeResolver -> GuavaCollectTypeAdapter.forMultimap(
				typeResolver.getTypeAdapter(1),
				lookup.lookup(typeResolver.getTypeToken()),
				encodeKey,
				decodeKey
		));
	}

	// TODO handle all known implementations
	public static <K, V> Supplier<IBuilder2<K, V, Multimap<K, V>>> defaultBuilderFactoryForMultimap(final TypeToken<? super Multimap<K, V>> typeToken) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends Multimap> rawType = (Class<? extends Multimap>) typeToken.getRawType();
		if ( rawType == HashMultimap.class ) {
			return () -> Builder.forMultimap(HashMultimap.create());
		}
		if ( rawType == LinkedHashMultimap.class ) {
			return () -> Builder.forMultimap(LinkedHashMultimap.create());
		}
		if ( ImmutableMultimap.class.isAssignableFrom(rawType) ) {
			return ImmutableBuilder::forMultimap;
		}
		return () -> Builder.forMultimap(HashMultimap.create());
	}

	public static final ITypeAdapterFactory<Multiset<Object>> defaultForMultiset = forMultiset(GuavaCollectTypeAdapterFactory::defaultBuilderFactoryForMultiset);

	public static <E> ITypeAdapterFactory<Multiset<E>> forMultiset(
			final IBuilder1.ILookup<? super E, ? extends Multiset<E>> lookup
	) {
		return ITypeAdapterFactory.forClassHierarchy(Multiset.class, typeResolver -> GuavaCollectTypeAdapter.forMultiset(
				typeResolver.getTypeAdapter(0),
				lookup.lookup(typeResolver.getTypeToken())
		));
	}

	// TODO handle all known implementations
	public static <E> Supplier<IBuilder1<E, Multiset<E>>> defaultBuilderFactoryForMultiset(final TypeToken<? super Multiset<E>> typeToken) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends Multiset> rawType = (Class<? extends Multiset>) typeToken.getRawType();
		if ( rawType == HashMultiset.class ) {
			return () -> IBuilder1.of(HashMultiset.create());
		}
		if ( rawType == LinkedHashMultiset.class ) {
			return () -> IBuilder1.of(LinkedHashMultiset.create());
		}
		if ( ImmutableMultiset.class.isAssignableFrom(rawType) ) {
			return ImmutableBuilder::forMultiset;
		}
		return () -> IBuilder1.of(HashMultiset.create());
	}

	public static final ITypeAdapterFactory<Table<String, String, Object>> defaultForTable = forTable(GuavaCollectTypeAdapterFactory::defaultBuilderFactoryForTable, Function.identity(), Function.identity(), Function.identity(), Function.identity());

	public static <R, C, V> ITypeAdapterFactory<Table<R, C, V>> forTable(
			final IBuilder3.ILookup<? super R, ? super C, ? super V, ? extends Table<R, C, V>> builderLookup,
			final Function<? super R, String> encodeRowKey,
			final Function<? super String, ? extends R> decodeRowKey,
			final Function<? super C, String> encodeColumnKey,
			final Function<? super String, ? extends C> decodeColumnKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(Table.class, typeResolver -> TableTypeAdapter.getInstance(
				typeResolver.getTypeAdapter(0),
				builderLookup.lookup(typeResolver.getTypeToken()),
				encodeRowKey,
				decodeRowKey,
				encodeColumnKey,
				decodeColumnKey
		));
	}

	// TODO handle all known implementations
	public static <V> Supplier<IBuilder3<String, String, V, Table<String, String, V>>> defaultBuilderFactoryForTable(final TypeToken<? super Table<String, String, V>> typeToken) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends Table> rawType = (Class<? extends Table>) typeToken.getRawType();
		// TODO why is HashBasedTable not final?
		if ( HashBasedTable.class.isAssignableFrom(rawType) ) {
			return () -> Builder.forTable(HashBasedTable.create());
		}
		if ( ImmutableTable.class.isAssignableFrom(rawType) ) {
			return ImmutableBuilder::forTable;
		}
		return () -> Builder.forTable(HashBasedTable.create());
	}

}
