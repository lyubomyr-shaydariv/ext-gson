package lsh.ext.gson.adapters.guava;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.base.Optional;
import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class GuavaModuleTest
		extends AbstractModuleTest {

	public GuavaModuleTest() {
		super("Google Guava");
	}

	@Nonnull
	@Override
	protected IModule createUnit() {
		return GuavaModule.get();
	}

	@Nonnull
	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Optional.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(BiMap.class, String.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Multiset.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Multimap.class, String.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Table.class, String.class, String.class, Integer.class))
		);
	}

}
