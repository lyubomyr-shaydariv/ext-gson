package lsh.ext.gson.adapters;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public abstract class AbstractTypeAdapterTest<T> {

	private static final Gson gson = new Gson();

	@Nonnull
	protected abstract TypeAdapter<T> createUnit(@Nonnull Gson gson);

	@Nullable
	protected abstract T nullValue();

	@Nonnull
	protected abstract T getValue();

	@Nonnull
	protected abstract Object finalizeValue(@Nonnull T value);

	@Nonnull
	protected abstract String getValueJson();

	@Test
	public final void testWrite()
			throws IOException {
		final TypeAdapter<T> unit = createUnit(gson);
		MatcherAssert.assertThat(unit.fromJson("null"), CoreMatchers.is(nullValue()));
		MatcherAssert.assertThat(finalizeValue(unit.fromJson(getValueJson())), CoreMatchers.is(finalizeValue(getValue())));
	}

	@Test
	public final void testRead() {
		final TypeAdapter<T> unit = createUnit(gson);
		MatcherAssert.assertThat(unit.toJson(nullValue()), CoreMatchers.is("null"));
		MatcherAssert.assertThat(unit.toJson(getValue()), CoreMatchers.is(getValueJson()));
	}

}
