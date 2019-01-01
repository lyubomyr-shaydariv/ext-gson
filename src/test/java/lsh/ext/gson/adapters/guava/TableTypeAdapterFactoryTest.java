package lsh.ext.gson.adapters.guava;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.Table;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class TableTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public TableTypeAdapterFactoryTest() {
		super(false);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return TableTypeAdapterFactory.get();
	}

	@Nonnull
	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(new TypeToken<Table<String, String, String>>() {}),
				Arguments.of(new TypeToken<Table<Float, Integer, Object>>() {}),
				Arguments.of(new TypeToken<Table<Character, List<Object>, Integer>>() {})
		);
	}

	@Nonnull
	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(new TypeToken<Map<Float, Integer>>() {}),
				Arguments.of(new TypeToken<Set<String>>() {})
		);
	}

}
