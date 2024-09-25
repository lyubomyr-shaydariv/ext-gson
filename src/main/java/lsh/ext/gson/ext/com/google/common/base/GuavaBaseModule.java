package lsh.ext.gson.ext.com.google.common.base;

import com.google.common.base.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class GuavaBaseModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<? extends Optional<?>> optionalTypeAdapterFactory = OptionalTypeAdapterFactory.defaultForOptional;

	@Override
	public IModule build() {
		return IModule.from(
				optionalTypeAdapterFactory
		);
	}

}
