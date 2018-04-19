package lsh.ext.gson.adapters.java8;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;

public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<String>> {

	@Nonnull
	@Override
	protected TypeAdapter<Optional<String>> createUnit(@Nonnull final Gson gson) {
		return OptionalTypeAdapter.get(gson.getAdapter(String.class));
	}

	@Nullable
	@Override
	protected Optional<String> nullValue() {
		return Optional.empty();
	}

	@Nonnull
	@Override
	protected Optional<String> getValue() {
		return Optional.of("foo");
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Optional<String> value) {
		return value;
	}

	@Nonnull
	@Override
	protected String getValueJson() {
		return "\"foo\"";
	}

}
