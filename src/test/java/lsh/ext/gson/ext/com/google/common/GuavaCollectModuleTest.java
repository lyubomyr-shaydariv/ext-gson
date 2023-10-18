package lsh.ext.gson.ext.com.google.common;

import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import lsh.ext.gson.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class GuavaCollectModuleTest
		extends AbstractModuleTest {

	@Override
	protected IModule createUnit() {
		return GuavaCollectModule.getInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(BiMap.class, String.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Multiset.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Multimap.class, String.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Table.class, String.class, String.class, Integer.class))
		);
	}

}
