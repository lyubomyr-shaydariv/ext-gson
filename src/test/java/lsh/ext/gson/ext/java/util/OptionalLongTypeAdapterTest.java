package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.OptionalLong;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

	@Test
	public void testTristate() {
		final TypeAdapter<OptionalLong> unit = OptionalLongTypeAdapter.getInstance();
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapter(OptionalLong.class, unit)
				.create();
		final Record record = gson.fromJson("{\"value\":-1,\"noValue\":null}", Record.class);
		Assertions.assertEquals(OptionalLong.of(-1), record.value);
		Assertions.assertEquals(OptionalLong.empty(), record.noValue);
		Assertions.assertNull(record.undefined);
	}

	@SuppressWarnings({ "InstanceVariableMayNotBeInitialized", "OptionalUsedAsFieldOrParameterType" })
	private static final class Record {

		@SuppressWarnings("unused")
		private OptionalLong value, noValue, undefined;

	}

}
