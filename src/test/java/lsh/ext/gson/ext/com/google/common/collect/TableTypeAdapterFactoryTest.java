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

	@SuppressWarnings("unchecked")
	private static final TypeToken<Table<String, String, String>> stringToStringToStringTableTypeToken = (TypeToken<Table<String, String, String>>) TypeToken.getParameterized(Table.class, String.class, String.class, String.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Table<Float, Integer, Object>> floatToIntegerToObjectTableTypeToken = (TypeToken<Table<Float, Integer, Object>>) TypeToken.getParameterized(Table.class, Float.class, Integer.class, Object.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Table<Character, List<?>, Integer>> characterToListToIntegerTableTypeToken = (TypeToken<Table<Character, List<?>, Integer>>) TypeToken.getParameterized(Table.class, Character.class, List.class, Integer.class);

	public TableTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return TableTypeAdapter.Factory.getInstance();
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
				Arguments.of(Types.voidTypeToken),
				Arguments.of(Types.rawMapTypeToken),
				Arguments.of(Types.rawSetTypeToken)
		);
	}

}
