package lsh.ext.gson.ext.com.google.common.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
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
		private ITypeAdapterFactory<? extends BiMap<?, ?>> biMapTypeAdapterFactory = BiMapTypeAdapterFactory.getInstance(HashBiMap::create, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

		@Setter
		private ITypeAdapterFactory<? extends Multimap<?, ?>> multimapTypeAdapterFactory = MultimapTypeAdapterFactory.getInstance(HashMultimap::create, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

		@Setter
		private ITypeAdapterFactory<? extends Multiset<?>> multisetTypeAdapterFactory = MultisetTypeAdapterFactory.getInstance(LinkedHashMultiset::create);

		@Setter
		private ITypeAdapterFactory<? extends Table<?, ?, ?>> tableTypeAdapterFactory = TableTypeAdapterFactory.getInstance(HashBasedTable::create, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

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
