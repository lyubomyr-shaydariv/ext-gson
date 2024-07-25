package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.OptionalLong;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalLongTypeAdapterTest
		extends AbstractTypeAdapterTest<OptionalLong, OptionalLong> {

	private static final TypeAdapter<OptionalLong> unit = OptionalLongTypeAdapter.getInstance();

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	protected OptionalLong normalize(@Nullable final OptionalLong value) {
		return value != null ? value : OptionalLong.empty();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						unit,
						"9223372036854775807",
						OptionalLong.of(Long.MAX_VALUE)
				),
				makeTestCase(
						unit,
						"null",
						OptionalLong.empty()
				)
		);
	}

}
