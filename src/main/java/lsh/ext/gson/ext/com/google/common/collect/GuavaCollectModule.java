package lsh.ext.gson.ext.com.google.common.collect;

import com.google.common.collect.BiMap;
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

public final class GuavaCollectModule
		extends AbstractModule {

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
		private ITypeAdapterFactory<? extends BiMap<?, ?>> biMapTypeAdapterFactory = BiMapTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends Multimap<String, ?>> multimapTypeAdapterFactory = MultimapTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends Multiset<?>> multisetTypeAdapterFactory = MultisetTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends Table<String, String, ?>> tableTypeAdapterFactory = TableTypeAdapter.Factory.getInstance();

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
