package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;

public final class MultimapTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest<Multimap<String, String>> {

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return MultimapTypeAdapterFactory.get();
	}

	@Nonnull
	@Override
	protected TypeToken<Multimap<String, String>> getTypeToken() {
		return new TypeToken<Multimap<String, String>>() {
		};
	}

}
