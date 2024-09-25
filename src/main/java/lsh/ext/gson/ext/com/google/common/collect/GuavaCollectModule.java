package lsh.ext.gson.ext.com.google.common.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class GuavaCollectModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<? extends BiMap<?, ?>> biMapTypeAdapterFactory = GuavaCollectTypeAdapterFactory.defaultForBiMap;

	@Setter
	private ITypeAdapterFactory<? extends Multimap<String, ?>> multimapTypeAdapterFactory = GuavaCollectTypeAdapterFactory.defaultForMultimap;

	@Setter
	private ITypeAdapterFactory<? extends Multiset<?>> multisetTypeAdapterFactory = GuavaCollectTypeAdapterFactory.defaultForMultiset;

	@Setter
	private ITypeAdapterFactory<? extends Table<String, String, ?>> tableTypeAdapterFactory = GuavaCollectTypeAdapterFactory.defaultForTable;

	@Override
	public IModule build() {
		return IModule.from(
				biMapTypeAdapterFactory,
				multimapTypeAdapterFactory,
				multisetTypeAdapterFactory,
				tableTypeAdapterFactory
		);
	}

}
