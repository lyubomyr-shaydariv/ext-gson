package lsh.ext.gson.ext.com.google.common;

import javax.annotation.Nullable;

import com.google.common.base.Converter;
import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
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
 * <li>{@link TableTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
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
	@Accessors(fluent = true, chain = true, prefix = "with")
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
		 * @return A new module instance.
		 */
		public IModule build() {
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
			return new GuavaCollectModule(UnmodifiableIterable.copyOf(
					BiMapTypeAdapterFactory.getInstance(castNewBiMapFactory, castBiMapKeyConverter),
					MultimapTypeAdapterFactory.getInstance(castNewMultimapFactory, castMultimapKeyConverter),
					MultisetTypeAdapterFactory.getInstance(castNewMultisetFactory),
					TableTypeAdapterFactory.getInstance(newTableFactory, castTableRowKeyConverter, castTableColumnKeyConverter)
			));
		}

	}

}
