package lsh.ext.gson.ext;

import javax.annotation.Nullable;

import com.google.common.base.Converter;
import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;
import lsh.ext.gson.ext.com.google.common.base.OptionalTypeAdapterFactory;
import lsh.ext.gson.ext.com.google.common.collect.BiMapTypeAdapterFactory;
import lsh.ext.gson.ext.com.google.common.collect.MultimapTypeAdapterFactory;
import lsh.ext.gson.ext.com.google.common.collect.MultisetTypeAdapterFactory;
import lsh.ext.gson.ext.com.google.common.collect.TableTypeAdapterFactory;

/**
 * Implements a Google Guava module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link BiMapTypeAdapterFactory}</li>
 * <li>{@link MultimapTypeAdapterFactory}</li>
 * <li>{@link MultisetTypeAdapterFactory}</li>
 * <li>{@link OptionalTypeAdapterFactory}</li>
 * <li>{@link TableTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
 */
public final class GuavaModule
		extends AbstractModule {

	private static final IModule instance = build()
			.done();

	private GuavaModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Google Guava", typeAdapterFactories);
	}

	/**
	 * @return The default instance of the module with the default type adapter factories settings.
	 */
	public static IModule getInstance() {
		return instance;
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder build() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Builder {

		@Nullable
		private Supplier<? extends BiMap<?, ?>> newBiMapFactory;

		@Nullable
		private Converter<?, String> biMapKeyConverter;

		@Nullable
		private Supplier<? extends Multiset<?>> newMultisetFactory;

		@Nullable
		private Supplier<? extends Multimap<?, ?>> newMultimapFactory;

		@Nullable
		private Converter<?, String> multimapKeyConverter;

		@Nullable
		private Supplier<? extends Table<String, String, Object>> newTableFactory;

		@Nullable
		private Converter<?, String> tableRowKeyConverter;

		@Nullable
		private Converter<?, String> tableColumnKeyConverter;

		/**
		 * Sets a new bidirectional map factory used in {@link BiMapTypeAdapterFactory#getInstance(Supplier, Converter)}
		 *
		 * @param newBiMapFactory A supplier to return a new bidirectional map
		 *
		 * @return Self.
		 */
		public Builder withNewBiMapFactory(final Supplier<? extends BiMap<?, ?>> newBiMapFactory) {
			this.newBiMapFactory = newBiMapFactory;
			return this;
		}

		/**
		 * Sets a bidirectional map key converter used in {@link BiMapTypeAdapterFactory#getInstance(Supplier, Converter)}
		 *
		 * @param biMapKeyConverter A converter to convert a bidirectional map key to string and vice versa
		 *
		 * @return Self.
		 */
		public Builder withBiMapKeyConverter(final Converter<?, String> biMapKeyConverter) {
			this.biMapKeyConverter = biMapKeyConverter;
			return this;
		}

		/**
		 * Sets a new multiset factory used in {@link MultisetTypeAdapterFactory#getInstance(Supplier)}
		 *
		 * @param newMultisetFactory A supplier to return a new multiset
		 *
		 * @return Self.
		 */
		public Builder withNewMultisetFactory(final Supplier<? extends Multiset<?>> newMultisetFactory) {
			this.newMultisetFactory = newMultisetFactory;
			return this;
		}

		/**
		 * Sets a new multimap factory used in {@link MultimapTypeAdapterFactory#getInstance(Supplier, Converter)}
		 *
		 * @param newMultimapFactory A supplier to return a new multimap
		 *
		 * @return Self.
		 */
		public Builder withNewMultimapFactory(final Supplier<? extends Multimap<?, ?>> newMultimapFactory) {
			this.newMultimapFactory = newMultimapFactory;
			return this;
		}

		/**
		 * Sets a multimap key converter used in {@link MultimapTypeAdapterFactory#getInstance(Supplier, Converter)}
		 *
		 * @param multimapKeyConverter A converter to convert a multimap key to string and vice versa
		 *
		 * @return Self.
		 */
		public Builder withMultimapKeyConverter(final Converter<?, String> multimapKeyConverter) {
			this.multimapKeyConverter = multimapKeyConverter;
			return this;
		}

		/**
		 * Sets a new table factory use in {@link TableTypeAdapterFactory#getInstance(Supplier, Converter, Converter)}
		 *
		 * @param newTableFactory A supplier to return a new table
		 *
		 * @return Self.
		 */
		public Builder withNewTableFactory(final Supplier<? extends Table<String, String, Object>> newTableFactory) {
			this.newTableFactory = newTableFactory;
			return this;
		}

		/**
		 * Sets a table row key converter used in {@link TableTypeAdapterFactory#getInstance(Supplier, Converter, Converter)}
		 *
		 * @param tableRowKeyConverter A converter to convert a table row key to a string and vice versa
		 *
		 * @return Self.
		 */
		public Builder withTableRowKeyConverter(final Converter<?, String> tableRowKeyConverter) {
			this.tableRowKeyConverter = tableRowKeyConverter;
			return this;
		}

		/**
		 * Sets a table column key converter used in {@link TableTypeAdapterFactory#getInstance(Supplier, Converter, Converter)}
		 *
		 * @param tableColumnKeyConverter A converter to convert a table column key to a string and vice versa
		 *
		 * @return Self.
		 */
		public Builder withTableColumnKeyConverter(final Converter<?, String> tableColumnKeyConverter) {
			this.tableColumnKeyConverter = tableColumnKeyConverter;
			return this;
		}

		/**
		 * @return A new module instance.
		 */
		public IModule done() {
			@SuppressWarnings("unchecked")
			final Supplier<? extends BiMap<String, Object>> castNewBiMapFactory = (Supplier<? extends BiMap<String, Object>>) newBiMapFactory;
			@SuppressWarnings("unchecked")
			final Converter<String, String> castBiMapKeyConverter = (Converter<String, String>) biMapKeyConverter;
			@SuppressWarnings("unchecked")
			final Supplier<? extends Multimap<Object, Object>> castNewMultimapFactory = (Supplier<? extends Multimap<Object, Object>>) newMultimapFactory;
			@SuppressWarnings("unchecked")
			final Converter<Object, String> castMultimapKeyConverter = (Converter<Object, String>) multimapKeyConverter;
			@SuppressWarnings("unchecked")
			final Supplier<? extends Multiset<Object>> castNewMultisetFactory = (Supplier<? extends Multiset<Object>>) newMultisetFactory;
			@SuppressWarnings("unchecked")
			final Converter<String, String> castTableRowKeyConverter = (Converter<String, String>) tableRowKeyConverter;
			@SuppressWarnings("unchecked")
			final Converter<String, String> castTableColumnKeyConverter = (Converter<String, String>) tableColumnKeyConverter;
			final Iterable<TypeAdapterFactory> typeAdapterFactories = ImmutableList.<TypeAdapterFactory>builder()
					.add(OptionalTypeAdapterFactory.getInstance())
					.add(BiMapTypeAdapterFactory.getInstance(castNewBiMapFactory, castBiMapKeyConverter))
					.add(MultimapTypeAdapterFactory.getInstance(castNewMultimapFactory, castMultimapKeyConverter))
					.add(MultisetTypeAdapterFactory.getInstance(castNewMultisetFactory))
					.add(TableTypeAdapterFactory.getInstance(newTableFactory, castTableRowKeyConverter, castTableColumnKeyConverter))
					.build();
			return new GuavaModule(typeAdapterFactories);
		}

	}

}
