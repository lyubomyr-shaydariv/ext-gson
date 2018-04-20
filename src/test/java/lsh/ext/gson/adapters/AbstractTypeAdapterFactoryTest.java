package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public abstract class AbstractTypeAdapterFactoryTest {

	public static final class TestWith {

		private final TypeToken<?> supportedTypeToken;
		private final TypeToken<?> unsupportedTypeToken;

		private TestWith(final TypeToken<?> supportedTypeToken, final TypeToken<?> unsupportedTypeToken) {
			this.supportedTypeToken = supportedTypeToken;
			this.unsupportedTypeToken = unsupportedTypeToken;
		}

	}

	private static final Gson gson = new Gson();

	private static final TypeToken<Void> voidTypeToken = TypeToken.get(Void.class);

	private final TestWith testWith;

	protected AbstractTypeAdapterFactoryTest(final TestWith testWith) {
		this.testWith = testWith;
	}

	protected static TestWith testWith(final TypeToken<?> supportedTypeToken) {
		return new TestWith(supportedTypeToken, voidTypeToken);
	}

	protected static TestWith testWith(final TypeToken<?> supportedTypeToken, final TypeToken<?> unsupportedTypeToken) {
		return new TestWith(supportedTypeToken, unsupportedTypeToken);
	}

	@Nonnull
	protected abstract TypeAdapterFactory createUnit();

	@Test
	public final void testCreateIfSupports() {
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<?> typeAdapter = unit.create(gson, testWith.supportedTypeToken);
		MatcherAssert.assertThat(typeAdapter, CoreMatchers.notNullValue());
	}

	@Test
	public final void testCreateIfDoesNotSupport() {
		final TypeAdapterFactory unit = createUnit();
		final TypeAdapter<?> typeAdapter = unit.create(gson, testWith.unsupportedTypeToken);
		MatcherAssert.assertThat(typeAdapter, CoreMatchers.nullValue());
	}

}
