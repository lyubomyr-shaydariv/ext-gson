package lsh.ext.gson.ext.com.google.common.cache;

import com.google.common.cache.Cache;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class GuavaCacheModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<? extends Cache<?, ?>> cacheTypeAdapterFactory = GuavaCacheTypeAdapterFactory.defaultForCache;

	@Override
	public IModule build() {
		return IModule.from(
				cacheTypeAdapterFactory
		);
	}

}
