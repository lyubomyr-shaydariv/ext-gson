package lsh.ext.gson.ext.com.jayway.jsonpath;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class JsonPathModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<?> jsonPathTypeAdapterFactory = JsonPathTypeAdapter.Factory.getInstance(ReflectiveAccessors.getDeclaredFieldAccessors());

	@Override
	public IModule build() {
		return IModule.from(
				jsonPathTypeAdapterFactory
		);
	}

}
