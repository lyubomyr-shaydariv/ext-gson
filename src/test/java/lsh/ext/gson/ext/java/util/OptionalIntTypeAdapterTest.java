package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.OptionalInt;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

	@Test
	public void testTristate() {
		final TypeAdapter<OptionalInt> unit = OptionalIntTypeAdapter.getInstance();
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapter(OptionalInt.class, unit)
				.create();
		final Record record = gson.fromJson("{\"value\":777,\"noValue\":null}", Record.class);
		Assertions.assertEquals(OptionalInt.of(777), record.value);
		Assertions.assertEquals(OptionalInt.empty(), record.noValue);
		Assertions.assertNull(record.undefined);
	}

	@SuppressWarnings({ "InstanceVariableMayNotBeInitialized", "OptionalUsedAsFieldOrParameterType" })
	private static final class Record {

		@SuppressWarnings("unused")
		private OptionalInt value, noValue, undefined;

	}

}
