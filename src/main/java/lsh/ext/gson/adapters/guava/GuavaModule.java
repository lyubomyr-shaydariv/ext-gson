package lsh.ext.gson.adapters.guava;

import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

/**
 * <p>
 * Implements a Google Guava module registering the following type adapter factories:
 * </p>
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
 * @version 0-SNAPSHOT
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
	public static IModule get() {
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
	public static final class Builder {

		@Nullable
		private Supplier<? extends BiMap<String, Object>> newBiMapFactory;

		@Nullable
		private Supplier<? extends Multiset<Object>> newMultisetFactory;

		@Nullable
		private Supplier<? extends Multimap<String, Object>> newMultimapFactory;

		@Nullable
		private Supplier<? extends Table<String, String, Object>> newTableFactory;

		private Builder() {
		}

		/**
		 * Sets a new bidirectional map factory used in {@link BiMapTypeAdapterFactory#get(Supplier)}
		 *
		 * @param newBiMapFactory A supplier to return a new bidirectional map
		 *
		 * @return Self.
		 */
		public Builder withNewBiMapFactory(final Supplier<? extends BiMap<String, Object>> newBiMapFactory) {
			this.newBiMapFactory = newBiMapFactory;
			return this;
		}

		/**
		 * Sets a new multiset factory used in {@link MultisetTypeAdapterFactory#get(Supplier)}
		 *
		 * @param newMultisetFactory A supplier to return a new multiset
		 *
		 * @return Self.
		 */
		public Builder withNewMultisetFactory(final Supplier<? extends Multiset<Object>> newMultisetFactory) {
			this.newMultisetFactory = newMultisetFactory;
			return this;
		}

		/**
		 * Sets a new multimap factory used in {@link MultimapTypeAdapterFactory#get(Supplier)}
		 *
		 * @param newMultimapFactory A supplier to return a new multimap
		 *
		 * @return Self.
		 */
		public Builder withNewMultimapFactory(final Supplier<? extends Multimap<String, Object>> newMultimapFactory) {
			this.newMultimapFactory = newMultimapFactory;
			return this;
		}

		public Builder withNewTableFactory(final Supplier<? extends Table<String, String, Object>> newTableFactory) {
			this.newTableFactory = newTableFactory;
			return this;
		}

		/**
		 * @return A new module instance.
		 */
		public IModule done() {
			final Iterable<TypeAdapterFactory> typeAdapterFactories = ImmutableList.<TypeAdapterFactory>builder()
					.add(OptionalTypeAdapterFactory.get())
					.add(BiMapTypeAdapterFactory.get(newBiMapFactory))
					.add(MultimapTypeAdapterFactory.get(newMultimapFactory))
					.add(MultisetTypeAdapterFactory.get(newMultisetFactory))
					.add(TableTypeAdapterFactory.get(newTableFactory))
					.build();
			return new GuavaModule(typeAdapterFactories);
		}

	}

}
