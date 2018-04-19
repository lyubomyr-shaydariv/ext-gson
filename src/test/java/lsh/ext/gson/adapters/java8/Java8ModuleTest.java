package lsh.ext.gson.adapters.java8;

import java.util.Collection;
import java.util.Optional;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;

public final class Java8ModuleTest
		extends AbstractModuleTest {

	private static final Collection<TypeToken<?>> supportedTypeTokens = ImmutableList.of(
			new TypeToken<Optional<?>>() {
			}
	);

	@Nonnull
	@Override
	protected String getExpectedName() {
		return "Java 8";
	}

	@Nonnull
	@Override
	protected IModule createUnit() {
		return Java8Module.get();
	}

	@Nonnull
	@Override
	protected Collection<TypeToken<?>> getSupportedTypeTokens() {
		return supportedTypeTokens;
	}

}
