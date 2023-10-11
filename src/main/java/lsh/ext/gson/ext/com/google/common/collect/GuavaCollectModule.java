package lsh.ext.gson.ext.com.google.common.collect;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IInstanceFactory;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.UnmodifiableIterable;

public final class GuavaCollectModule
		extends AbstractModule {

	// TODO improve
	public static final IInstanceFactory.IProvider<BiMap<String, Object>> defaultBiMapFactoryProvider = typeToken -> {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends BiMap> rawType = (Class<? extends BiMap>) typeToken.getRawType();
		if ( rawType == HashBiMap.class ) {
			return HashBiMap::create;
		}
		return HashBiMap::create;
	};

	// TODO improve
	public static final IInstanceFactory.IProvider<Multimap<String, Object>> defaultMultimapFactoryProvider = typeToken -> {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends Multimap> rawType = (Class<? extends Multimap>) typeToken.getRawType();
		if ( rawType == ArrayListMultimap.class ) {
			return ArrayListMultimap::create;
		}
		return LinkedHashMultimap::create;
	};

	// TODO improve
	public static final IInstanceFactory.IProvider<? extends Multiset<Object>> defaultMultisetFactoryProvider = typeToken -> {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends Multiset> rawType = (Class<? extends Multiset>) typeToken.getRawType();
		if ( rawType == LinkedHashMultiset.class ) {
			return LinkedHashMultiset::create;
		}
		return LinkedHashMultiset::create;
	};

	// TODO improve
	public static final IInstanceFactory.IProvider<Table<String, String, Object>> defaultTableFactoryProvider = typeToken -> {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends Table> rawType = (Class<? extends Table>) typeToken.getRawType();
		if ( rawType == HashBasedTable.class ) {
			return HashBasedTable::create;
		}
		return HashBasedTable::create;
	};

	@Getter
	private static final IModule instance = builder()
			.build();

	private GuavaCollectModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	public static Builder builder() {
		return new Builder();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<? extends BiMap<String, ?>> biMapTypeAdapterFactory = BiMapTypeAdapterFactory.getInstance(defaultBiMapFactoryProvider);

		@Setter
		private ITypeAdapterFactory<? extends Multimap<String, ?>> multimapTypeAdapterFactory = MultimapTypeAdapterFactory.getInstance(defaultMultimapFactoryProvider);

		@Setter
		private ITypeAdapterFactory<? extends Multiset<?>> multisetTypeAdapterFactory = MultisetTypeAdapterFactory.getInstance(defaultMultisetFactoryProvider);

		@Setter
		private ITypeAdapterFactory<? extends Table<String, String, ?>> tableTypeAdapterFactory = TableTypeAdapterFactory.getInstance(defaultTableFactoryProvider);

		public IModule build() {
			return new GuavaCollectModule(UnmodifiableIterable.copyOf(
					biMapTypeAdapterFactory,
					multimapTypeAdapterFactory,
					multisetTypeAdapterFactory,
					tableTypeAdapterFactory
			));
		}

	}

}
