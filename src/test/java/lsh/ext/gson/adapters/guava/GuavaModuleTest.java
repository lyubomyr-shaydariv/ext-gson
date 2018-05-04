package lsh.ext.gson.adapters.guava;

import java.util.Collection;
import javax.annotation.Nonnull;

import com.google.common.base.Optional;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;

public final class GuavaModuleTest
		extends AbstractModuleTest {

	private static final Collection<TypeToken<?>> supportedTypeTokens = ImmutableList.of(
			new TypeToken<Optional<?>>() {},
			new TypeToken<BiMap<String, ?>>() {},
			new TypeToken<Multiset<?>>() {},
			new TypeToken<Multimap<String, ?>>() {},
			new TypeToken<Table<String, String, ?>>() {}
	);

	@Nonnull
	@Override
	protected String getExpectedName() {
		return "Google Guava";
	}

	@Nonnull
	@Override
	protected IModule createUnit() {
		return GuavaModule.get();
	}

	@Nonnull
	@Override
	protected Collection<TypeToken<?>> getSupportedTypeTokens() {
		return supportedTypeTokens;
	}

}
