package lsh.ext.gson.ext.com.google.common.collect;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.Table;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class TableTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	private static final TypeToken<Table<String, String, String>> stringToStringToStringTableTypeToken = Types.typeTokenOf(Table.class, String.class, String.class, String.class);
	private static final TypeToken<Table<Float, Integer, Object>> floatToIntegerToObjectTableTypeToken = Types.typeTokenOf(Table.class, Float.class, Integer.class, Object.class);
	private static final TypeToken<Table<Character, List<?>, Integer>> characterToListToIntegerTableTypeToken = Types.typeTokenOf(Table.class, Character.class, List.class, Integer.class);

	public TableTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return GuavaCollectTypeAdapterFactory.defaultForTable;
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(stringToStringToStringTableTypeToken),
				Arguments.of(floatToIntegerToObjectTableTypeToken),
				Arguments.of(characterToListToIntegerTableTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.primitiveVoidTypeToken),
				Arguments.of(Types.rawMapTypeToken),
				Arguments.of(Types.rawSetTypeToken)
		);
	}

}
