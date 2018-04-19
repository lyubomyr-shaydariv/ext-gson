package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;

import com.google.common.collect.Multiset;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;

public final class MultisetTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest<Multiset<String>> {

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return MultisetTypeAdapterFactory.get();
	}

	@Nonnull
	@Override
	protected TypeToken<Multiset<String>> getTypeToken() {
		return new TypeToken<Multiset<String>>() {
		};
	}

}
