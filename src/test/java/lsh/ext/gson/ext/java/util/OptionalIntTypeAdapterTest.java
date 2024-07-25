package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.OptionalInt;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalIntTypeAdapterTest
		extends AbstractTypeAdapterTest<OptionalInt, OptionalInt> {

	private static final TypeAdapter<OptionalInt> unit = OptionalIntTypeAdapter.getInstance();

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	protected OptionalInt normalize(@Nullable final OptionalInt value) {
		return value != null ? value : OptionalInt.empty();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						unit,
						"2147483647",
						OptionalInt.of(Integer.MAX_VALUE)
				),
				makeTestCase(
						unit,
						"null",
						OptionalInt.empty()
				)
		);
	}

}
