package lsh.ext.gson.ext.com.google.common.collect;

import java.util.function.Function;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IBuilder3;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class GuavaCollectTypeAdapterFactory {

	public static final ITypeAdapterFactory<BiMap<String, Object>> defaultForBiMap = forBiMap(
			typeToken -> {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				final Class<? extends BiMap> rawType = (Class<? extends BiMap>) typeToken.getRawType();
				if ( rawType == BiMap.class || ImmutableBiMap.class.isAssignableFrom(rawType) ) {
					return ImmutableBuilder::forBiMap;
				}
				return () -> {
					throw new UnsupportedOperationException(String.format("The default builder for %s does not support %s", BiMap.class, typeToken));
				};
			}
	);

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
		return ITypeAdapterFactory.forClassHierarchy(BiMap.class, provider -> GuavaCollectTypeAdapter.forBiMap(
				provider.getTypeAdapter(1),
				lookup.lookup(provider.getTypeToken()),
				encodeKey,
				decodeKey
		));
	}

	public static final ITypeAdapterFactory<Multimap<String, Object>> defaultForMultimap = forMultimap(
			typeToken -> {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				final Class<? extends Multimap> rawType = (Class<? extends Multimap>) typeToken.getRawType();
				if ( rawType == Multimap.class || ImmutableMultimap.class.isAssignableFrom(rawType) ) {
					return ImmutableBuilder::forMultimap;
				}
				return () -> {
					throw new UnsupportedOperationException(String.format("The default builder for %s does not support %s", Multimap.class, typeToken));
				};
			}
	);

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
		return ITypeAdapterFactory.forClassHierarchy(Multimap.class, provider -> GuavaCollectTypeAdapter.forMultimap(
				provider.getTypeAdapter(1),
				lookup.lookup(provider.getTypeToken()),
				encodeKey,
				decodeKey
		));
	}

	public static final ITypeAdapterFactory<Multiset<Object>> defaultForMultiset = forMultiset(
			typeToken -> {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				final Class<? extends Multiset> rawType = (Class<? extends Multiset>) typeToken.getRawType();
				if ( rawType == Multiset.class || ImmutableMultiset.class.isAssignableFrom(rawType) ) {
					return ImmutableBuilder::forMultiset;
				}
				return () -> {
					throw new UnsupportedOperationException(String.format("The default builder for %s does not support %s", Multiset.class, typeToken));
				};
			}
	);

	public static <E> ITypeAdapterFactory<Multiset<E>> forMultiset(
			final IBuilder1.ILookup<? super E, ? extends Multiset<E>> lookup
	) {
		return ITypeAdapterFactory.forClassHierarchy(Multiset.class, provider -> GuavaCollectTypeAdapter.forMultiset(
				provider.getTypeAdapter(0),
				lookup.lookup(provider.getTypeToken())
		));
	}

	public static final ITypeAdapterFactory<Table<String, String, Object>> defaultForTable = forTable(
			typeToken -> {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				final Class<? extends Table> rawType = (Class<? extends Table>) typeToken.getRawType();
				if ( rawType == Table.class || ImmutableTable.class.isAssignableFrom(rawType) ) {
					return ImmutableBuilder::forTable;
				}
				return () -> {
					throw new UnsupportedOperationException(String.format("The default builder for %s does not support %s", Table.class, typeToken));
				};
			},
			Function.identity(), Function.identity(), Function.identity(), Function.identity()
	);

	public static <R, C, V> ITypeAdapterFactory<Table<R, C, V>> forTable(
			final IBuilder3.ILookup<? super R, ? super C, ? super V, ? extends Table<R, C, V>> lookup,
			final Function<? super R, String> encodeRowKey,
			final Function<? super String, ? extends R> decodeRowKey,
			final Function<? super C, String> encodeColumnKey,
			final Function<? super String, ? extends C> decodeColumnKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(Table.class, provider -> TableTypeAdapter.getInstance(
				provider.getTypeAdapter(0),
				lookup.lookup(provider.getTypeToken()),
				encodeRowKey,
				decodeRowKey,
				encodeColumnKey,
				decodeColumnKey
		));
	}

}
