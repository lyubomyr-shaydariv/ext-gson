package lsh.ext.gson.ext.jakarta.json;

import jakarta.json.JsonValue;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class JakartaJsonApiModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<? extends JsonValue> jsonValueTypeAdapterFactory = JakartaJsonTypeAdapterFactory.defaultForJsonValue;

	@Override
	public IModule build() {
		return IModule.from(
				jsonValueTypeAdapterFactory
		);
	}

}
