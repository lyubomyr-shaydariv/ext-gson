package lsh.ext.gson.ext.com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Supplier;
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
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
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
	private static final IModule instance = builder(
			HashBiMap::create, Functions.identity(), Functions.identity(),
			LinkedHashMultiset::create,
			HashMultimap::create, Functions.identity(), Functions.identity(),
			HashBasedTable::create, Functions.identity(), Functions.identity(), Functions.identity(), Functions.identity()
	)
			.build();

	private GuavaCollectModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static <BMK, BMV, MSE, MMK, MMV, TTR, TTC, TTV> Builder<BMK, BMV, MSE, MMK, MMV, TTR, TTC, TTV> builder(
			@SuppressWarnings("Guava") final Supplier<? extends BiMap<BMK, BMV>> newBiMapFactory, @SuppressWarnings("Guava") final Function<? super BMK, String> biMapKeyMapper, @SuppressWarnings("Guava") final Function<? super String, ? extends BMK> biMapKeyReverseMapper,
			@SuppressWarnings("Guava") final Supplier<? extends Multiset<MSE>> newMultisetFactory,
			@SuppressWarnings("Guava") final Supplier<? extends Multimap<MMK, MMV>> newMultimapFactory, @SuppressWarnings("Guava") final Function<? super MMK, String> multimapKeyMapper, @SuppressWarnings("Guava") final Function<? super String, ? extends MMK> multimapKeyReverseMapper,
			@SuppressWarnings("Guava") final Supplier<? extends Table<TTR, TTC, TTV>> newTableFactory,
			@SuppressWarnings("Guava") final Function<? super TTR, String> tableRowKeyMapper,
			@SuppressWarnings("Guava") final Function<? super String, ? extends TTR> tableRowKeyReverseMapper,
			@SuppressWarnings("Guava") final Function<? super TTC, String> tableColumnKeyMapper,
			@SuppressWarnings("Guava") final Function<? super String, ? extends TTC> tableColumnKeyReverseMapper
	) {
		return new Builder<>(newBiMapFactory, biMapKeyMapper, biMapKeyReverseMapper, newMultisetFactory, newMultimapFactory, multimapKeyMapper, multimapKeyReverseMapper, newTableFactory, tableRowKeyMapper, tableRowKeyReverseMapper, tableColumnKeyMapper, tableColumnKeyReverseMapper);
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true, prefix = "with")
	public static final class Builder<BMK, BMV, MSE, MMK, MMV, TTR, TTC, TTV> {

		@SuppressWarnings("Guava")
		private final Supplier<? extends BiMap<BMK, BMV>> newBiMapFactory;
		@SuppressWarnings("Guava")
		private final Function<? super BMK, String> biMapKeyMapper;
		@SuppressWarnings("Guava")
		private final Function<? super String, ? extends BMK> biMapKeyReverseMapper;
		@SuppressWarnings("Guava")
		private final Supplier<? extends Multiset<MSE>> newMultisetFactory;
		@SuppressWarnings("Guava")
		private final Supplier<? extends Multimap<MMK, MMV>> newMultimapFactory;
		@SuppressWarnings("Guava")
		private final Function<? super MMK, String> multimapKeyMapper;
		@SuppressWarnings("Guava")
		private final Function<? super String, ? extends MMK> multimapKeyReverseMapper;
		@SuppressWarnings("Guava")
		private final Supplier<? extends Table<TTR, TTC, TTV>> newTableFactory;
		@SuppressWarnings("Guava")
		private final Function<? super TTR, String> tableRowKeyMapper;
		@SuppressWarnings("Guava")
		private final Function<? super String, ? extends TTR> tableRowKeyReverseMapper;
		@SuppressWarnings("Guava")
		private final Function<? super TTC, String> tableColumnKeyMapper;
		@SuppressWarnings("Guava")
		private final Function<? super String, ? extends TTC> tableColumnKeyReverseMapper;

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new GuavaCollectModule(UnmodifiableIterable.copyOf(
					BiMapTypeAdapterFactory.getInstance(newBiMapFactory, biMapKeyMapper, biMapKeyReverseMapper),
					MultimapTypeAdapterFactory.getInstance(newMultimapFactory, multimapKeyMapper, multimapKeyReverseMapper),
					MultisetTypeAdapterFactory.getInstance(newMultisetFactory),
					TableTypeAdapterFactory.getInstance(newTableFactory, tableRowKeyMapper, tableRowKeyReverseMapper, tableColumnKeyMapper, tableColumnKeyReverseMapper)
			));
		}

	}

}
