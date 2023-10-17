package lsh.ext.gson.ext.com.google.common.collect;

import com.google.common.base.Converter;
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
			HashBiMap::create, Converter.identity(),
			LinkedHashMultiset::create,
			HashMultimap::create, Converter.identity(),
			HashBasedTable::create, Converter.identity(), Converter.identity()
	)
			.build();

	private GuavaCollectModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static <BMK, BMV, MSE, MMK, MMV, TTR, TTC, TTV> Builder<BMK, BMV, MSE, MMK, MMV, TTR, TTC, TTV> builder(
			@SuppressWarnings("Guava") final Supplier<? extends BiMap<BMK, BMV>> newBiMapFactory, final Converter<BMK, String> biMapKeyConverter,
			@SuppressWarnings("Guava") final Supplier<? extends Multiset<MSE>> newMultisetFactory,
			@SuppressWarnings("Guava") final Supplier<? extends Multimap<MMK, MMV>> newMultimapFactory, final Converter<MMK, String> multimapKeyConverter,
			@SuppressWarnings("Guava") final Supplier<? extends Table<TTR, TTC, TTV>> newTableFactory, final Converter<TTR, String> tableRowKeyConverter, final Converter<TTC, String> tableColumnKeyConverter
	) {
		return new Builder<>(newBiMapFactory, biMapKeyConverter, newMultisetFactory, newMultimapFactory, multimapKeyConverter, newTableFactory, tableRowKeyConverter, tableColumnKeyConverter);
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true, prefix = "with")
	public static final class Builder<BMK, BMV, MSE, MMK, MMV, TTR, TTC, TTV> {

		@SuppressWarnings("Guava")
		private final Supplier<? extends BiMap<BMK, BMV>> newBiMapFactory;
		private final Converter<BMK, String> biMapKeyConverter;
		@SuppressWarnings("Guava")
		private final Supplier<? extends Multiset<MSE>> newMultisetFactory;
		@SuppressWarnings("Guava")
		private final Supplier<? extends Multimap<MMK, MMV>> newMultimapFactory;
		private final Converter<MMK, String> multimapKeyConverter;
		@SuppressWarnings("Guava")
		private final Supplier<? extends Table<TTR, TTC, TTV>> newTableFactory;
		private final Converter<TTR, String> tableRowKeyConverter;
		private final Converter<TTC, String> tableColumnKeyConverter;

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new GuavaCollectModule(UnmodifiableIterable.copyOf(
					BiMapTypeAdapterFactory.getInstance(newBiMapFactory, biMapKeyConverter),
					MultimapTypeAdapterFactory.getInstance(newMultimapFactory, multimapKeyConverter),
					MultisetTypeAdapterFactory.getInstance(newMultisetFactory),
					TableTypeAdapterFactory.getInstance(newTableFactory, tableRowKeyConverter, tableColumnKeyConverter)
			));
		}

	}

}
