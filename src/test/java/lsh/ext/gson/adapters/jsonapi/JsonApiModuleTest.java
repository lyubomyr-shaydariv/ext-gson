package lsh.ext.gson.adapters.jsonapi;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.json.JsonValue;

import com.google.common.collect.ImmutableList;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;

public final class JsonApiModuleTest
		extends AbstractModuleTest {

	private static final Collection<TypeToken<?>> supportedTypeTokens = ImmutableList.of(
			new TypeToken<JsonValue>() {}
	);

	@Nonnull
	@Override
	protected String getExpectedName() {
		return "Java JSON API";
	}

	@Nonnull
	@Override
	protected IModule createUnit() {
		return JsonApiModule.get();
	}

	@Nonnull
	@Override
	protected Collection<TypeToken<?>> getSupportedTypeTokens() {
		return supportedTypeTokens;
	}

}
