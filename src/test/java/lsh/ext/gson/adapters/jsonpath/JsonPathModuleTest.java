package lsh.ext.gson.adapters.jsonpath;

import java.util.Collection;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;

public final class JsonPathModuleTest
		extends AbstractModuleTest {

	private static final Collection<TypeToken<?>> supportedTypeTokens = ImmutableList.of();

	@Nonnull
	@Override
	protected String getExpectedName() {
		return "Jayway JsonPath";
	}

	@Nonnull
	@Override
	protected Class<? extends IModule> getExpectedUnitClass() {
		return JsonPathModule.class;
	}

	@Nonnull
	@Override
	protected IModule createUnit() {
		return JsonPathModule.get();
	}

	@Nonnull
	@Override
	protected Collection<TypeToken<?>> getSupportedTypeTokens() {
		return supportedTypeTokens;
	}

}
