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

/**
 * Implements a Google Guava module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link BiMapTypeAdapterFactory}</li>
 * <li>{@link MultimapTypeAdapterFactory}</li>
 * <li>{@link MultisetTypeAdapterFactory}</li>
 * <li>{@link TableTypeAdapterFactory}</li>
 * </ul>
 */
public final class GuavaCollectModule
		extends AbstractModule {

	/**
	 * Provides a default bidirectional map object.
	 */
	// TODO improve
	public static final IInstanceFactory.IProvider<BiMap<String, Object>> defaultBiMapFactoryProvider = typeToken -> {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends BiMap> rawType = (Class<? extends BiMap>) typeToken.getRawType();
		if ( rawType == HashBiMap.class ) {
			return HashBiMap::create;
		}
		return HashBiMap::create;
	};

	/**
	 * Provides a default multimap object.
	 */
	// TODO improve
	public static final IInstanceFactory.IProvider<Multimap<String, Object>> defaultMultimapFactoryProvider = typeToken -> {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends Multimap> rawType = (Class<? extends Multimap>) typeToken.getRawType();
		if ( rawType == ArrayListMultimap.class ) {
			return ArrayListMultimap::create;
		}
		return LinkedHashMultimap::create;
	};

	/**
	 * Provides a default multiset object.
	 */
	// TODO improve
	public static final IInstanceFactory.IProvider<? extends Multiset<Object>> defaultMultisetFactoryProvider = typeToken -> {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<? extends Multiset> rawType = (Class<? extends Multiset>) typeToken.getRawType();
		if ( rawType == LinkedHashMultiset.class ) {
			return LinkedHashMultiset::create;
		}
		return LinkedHashMultiset::create;
	};

	/**
	 * Provides a default table object.
	 */
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

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<? extends BiMap<?, ?>> biMapTypeAdapterFactory = BiMapTypeAdapterFactory.getInstance(defaultBiMapFactoryProvider, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

		@Setter
		private ITypeAdapterFactory<? extends Multimap<?, ?>> multimapTypeAdapterFactory = MultimapTypeAdapterFactory.getInstance(defaultMultimapFactoryProvider, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

		@Setter
		private ITypeAdapterFactory<? extends Multiset<?>> multisetTypeAdapterFactory = MultisetTypeAdapterFactory.getInstance(defaultMultisetFactoryProvider);

		@Setter
		private ITypeAdapterFactory<? extends Table<?, ?, ?>> tableTypeAdapterFactory = TableTypeAdapterFactory.getInstance(defaultTableFactoryProvider, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

		/**
		 * @return A new module instance.
		 */
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
