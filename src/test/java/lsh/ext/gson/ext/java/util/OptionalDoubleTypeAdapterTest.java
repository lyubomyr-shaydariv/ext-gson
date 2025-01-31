package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.OptionalDouble;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

	@Test
	public void testTristate() {
		final TypeAdapter<OptionalDouble> unit = OptionalDoubleTypeAdapter.getInstance();
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapter(OptionalDouble.class, unit)
				.create();
		final Record record = gson.fromJson("{\"value\":3.14,\"noValue\":null}", Record.class);
		Assertions.assertEquals(OptionalDouble.of(3.14), record.value);
		Assertions.assertEquals(OptionalDouble.empty(), record.noValue);
		Assertions.assertNull(record.undefined);
	}

	@SuppressWarnings({ "InstanceVariableMayNotBeInitialized", "OptionalUsedAsFieldOrParameterType" })
	private static final class Record {

		@SuppressWarnings("unused")
		private OptionalDouble value, noValue, undefined;

	}

}
