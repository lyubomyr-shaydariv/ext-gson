package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public abstract class AbstractOptionalTypeAdapterFactoryTest<O, T> {

	private static final Gson gson = new Gson();

	protected abstract TypeAdapterFactory createUnit();

	@Nonnull
	protected abstract TypeToken<T> getValueTypeToken();

	@Nonnull
	protected abstract TypeToken<O> getOptionalTypeToken();

	protected abstract TypeToken<? extends TypeAdapter<O>> getOptionalTypeAdapterTypeToken();

	@Test
	public final void testCreate() {
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<?> optionalTypeAdapter = unit.create(gson, getOptionalTypeToken());
		MatcherAssert.assertThat(optionalTypeAdapter, CoreMatchers.instanceOf(getOptionalTypeAdapterTypeToken().getRawType()));
	}

	@Test
	public final void testCreateMustReturnNullForNonOptional() {
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<?> optionalTypeAdapter = unit.create(gson, getValueTypeToken());
		MatcherAssert.assertThat(optionalTypeAdapter, CoreMatchers.nullValue());
	}

}
