package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractOptionalTypeAdapterTest;

public final class OptionalTypeAdapterTest
		extends AbstractOptionalTypeAdapterTest<Optional<String>, String> {

	private static final TypeToken<String> stringTypeToken = TypeToken.get(String.class);

	@Nonnull
	@Override
	protected TypeToken<String> getTypeToken() {
		return stringTypeToken;
	}

	@Nonnull
	@Override
	protected TypeAdapter<Optional<String>> createUnit(@Nonnull final TypeAdapter<String> valueTypeAdapter) {
		return OptionalTypeAdapter.get(valueTypeAdapter);
	}

	@Nonnull
	@Override
	protected Optional<String> wrap(@Nullable final String value) {
		return Optional.fromNullable(value);
	}

	@Nonnull
	@Override
	protected String getSingleValue() {
		return "foo";
	}

	@Nonnull
	@Override
	protected String getSingleValueJson() {
		return "\"foo\"";
	}

}
