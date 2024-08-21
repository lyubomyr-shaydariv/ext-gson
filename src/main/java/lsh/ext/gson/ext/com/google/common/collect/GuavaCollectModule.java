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
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IBuilder3;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

public final class GuavaCollectModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private GuavaCollectModule(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<? extends BiMap<?, ?>> biMapTypeAdapterFactory = BiMapTypeAdapter.Factory.getInstance(IBuilder2.IFactory.unsupported());

		@Setter
		private ITypeAdapterFactory<? extends Multimap<String, ?>> multimapTypeAdapterFactory = MultimapTypeAdapter.Factory.getInstance(IBuilder2.IFactory.unsupported());

		@Setter
		private ITypeAdapterFactory<? extends Multiset<?>> multisetTypeAdapterFactory = MultisetTypeAdapter.Factory.getInstance(IBuilder1.IFactory.unsupported());

		@Setter
		private ITypeAdapterFactory<? extends Table<String, String, ?>> tableTypeAdapterFactory = TableTypeAdapter.Factory.getInstance(IBuilder3.IFactory.unsupported());

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new GuavaCollectModule(
					biMapTypeAdapterFactory,
					multimapTypeAdapterFactory,
					multisetTypeAdapterFactory,
					tableTypeAdapterFactory
			);
		}

	}

}
