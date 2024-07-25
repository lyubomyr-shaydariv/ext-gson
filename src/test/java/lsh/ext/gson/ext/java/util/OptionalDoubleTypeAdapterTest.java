package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.OptionalDouble;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalDoubleTypeAdapterTest
		extends AbstractTypeAdapterTest<OptionalDouble, OptionalDouble> {

	private static final TypeAdapter<OptionalDouble> unit = OptionalDoubleTypeAdapter.getInstance();

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	protected OptionalDouble normalize(@Nullable final OptionalDouble value) {
		return value != null ? value : OptionalDouble.empty();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						unit,
						"3.1415926",
						OptionalDouble.of(3.1415926)
				),
				makeTestCase(
						unit,
						"null",
						OptionalDouble.empty()
				)
		);
	}

}
