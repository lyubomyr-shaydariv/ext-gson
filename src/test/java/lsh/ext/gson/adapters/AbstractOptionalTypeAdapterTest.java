package lsh.ext.gson.adapters;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public abstract class AbstractOptionalTypeAdapterTest<O, T> {

	private static final Gson gson = new Gson();

	@Nonnull
	protected abstract TypeToken<T> getTypeToken();

	@Nonnull
	protected abstract TypeAdapter<O> createUnit(@Nonnull TypeAdapter<T> valueTypeAdapter);

	@Nonnull
	protected abstract O wrap(@Nullable T value);

	@Nonnull
	protected abstract T getSingleValue();

	@Nonnull
	protected abstract String getSingleValueJson();

	@Test
	public final void testRead()
			throws IOException {
		final TypeAdapter<T> valueTypeAdapter = gson.getAdapter(getTypeToken());
		final TypeAdapter<O> unit = createUnit(valueTypeAdapter);
		MatcherAssert.assertThat(unit.fromJson("null"), CoreMatchers.is(wrap(null)));
		MatcherAssert.assertThat(unit.fromJson(getSingleValueJson()), CoreMatchers.is(wrap(getSingleValue())));
	}

	@Test
	public final void testWrite() {
		final TypeAdapter<T> valueTypeAdapter = gson.getAdapter(getTypeToken());
		final TypeAdapter<O> unit = createUnit(valueTypeAdapter);
		MatcherAssert.assertThat(unit.toJson(wrap(null)), CoreMatchers.is("null"));
		MatcherAssert.assertThat(unit.toJson(wrap(getSingleValue())), CoreMatchers.is(getSingleValueJson()));
	}

}
