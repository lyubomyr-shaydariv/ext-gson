package lsh.ext.gson.ext.com.google.common.collect;

import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class GuavaCollectModuleTest
		extends AbstractModuleTest {

	private static final TypeToken<BiMap<String, Integer>> stringToIntegerBiMapTypeToken = Types.typeTokenOf(BiMap.class, String.class, Integer.class);
	private static final TypeToken<Multiset<Integer>> integerMultisetTypeToken = Types.typeTokenOf(Multiset.class, Integer.class);
	private static final TypeToken<Multimap<String, Integer>> stringToIntegerMultiMapTypeToken = Types.typeTokenOf(Multimap.class, String.class, Integer.class);
	private static final TypeToken<Table<String, String, Integer>> stringToStringToIntegerTableTypeToken = Types.typeTokenOf(Table.class, String.class, String.class, Integer.class);

	public GuavaCollectModuleTest() {
		super(GuavaCollectModule.create().build());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(stringToIntegerBiMapTypeToken),
				Arguments.of(integerMultisetTypeToken),
				Arguments.of(stringToIntegerMultiMapTypeToken),
				Arguments.of(stringToStringToIntegerTableTypeToken)
		);
	}

}
