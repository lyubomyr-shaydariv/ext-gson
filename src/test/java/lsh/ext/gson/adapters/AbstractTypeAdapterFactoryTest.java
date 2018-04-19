package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public abstract class AbstractTypeAdapterFactoryTest<T> {

	private static final Gson gson = new Gson();

	private static final TypeToken<Void> voidTypeToken = TypeToken.get(Void.class);

	@Nonnull
	protected abstract TypeAdapterFactory createUnit();

	@Nonnull
	protected abstract TypeToken<T> getTypeToken();

	@Test
	public final void testCreateIfSupports() {
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<T> typeAdapter = unit.create(gson, getTypeToken());
		MatcherAssert.assertThat(typeAdapter, CoreMatchers.notNullValue());
	}

	@Test
	public final void testCreateIfDoesNotSupport() {
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<?> typeAdapter = unit.create(gson, voidTypeToken);
		MatcherAssert.assertThat(typeAdapter, CoreMatchers.nullValue());
	}

}
