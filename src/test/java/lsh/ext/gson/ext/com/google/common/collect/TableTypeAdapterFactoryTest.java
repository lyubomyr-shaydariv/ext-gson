package lsh.ext.gson.ext.com.google.common.collect;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.base.Converter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class TableTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public TableTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return TableTypeAdapterFactory.getInstance(HashBasedTable::create, Converter.identity(), Converter.identity());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Table.class, String.class, String.class, String.class)),
				Arguments.of(TypeToken.getParameterized(Table.class, Float.class, Integer.class, Object.class)),
				Arguments.of(TypeToken.getParameterized(Table.class, Character.class, List.class, Integer.class))
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Map.class, Float.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Set.class, String.class))
		);
	}

}
