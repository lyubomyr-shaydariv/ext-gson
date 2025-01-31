package lsh.ext.gson.ext.com.google.common.base;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<?>, Optional<?>> {

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	protected Optional<?> normalize(@Nullable final Optional<?> value) {
		return value != null ? value : Optional.absent();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						OptionalTypeAdapter.getInstance(TestTypeAdapters.stringTypeAdapter),
						"\"foo\"",
						Optional.of("foo")
				)
		);
	}

	@Test
	public void testTristate() {
		final TypeAdapter<Optional<String>> unit = OptionalTypeAdapter.getInstance(TestTypeAdapters.stringTypeAdapter);
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapter(TypeToken.getParameterized(Optional.class, String.class).getType(), unit)
				.create();
		final Record record = gson.fromJson("{\"value\":\"value\",\"noValue\":null}", Record.class);
		Assertions.assertEquals(Optional.of("value"), record.value);
		Assertions.assertEquals(Optional.absent(), record.noValue);
		Assertions.assertNull(record.undefined);
	}

	@SuppressWarnings({ "InstanceVariableMayNotBeInitialized", "OptionalUsedAsFieldOrParameterType" })
	private static final class Record {

		@SuppressWarnings("unused")
		private Optional<String> value, noValue, undefined;

	}

}
