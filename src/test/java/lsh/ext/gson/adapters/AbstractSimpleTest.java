package lsh.ext.gson.adapters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public abstract class AbstractSimpleTest {

	private static final Gson gson = new Gson();

	public static final class TestWith<T> {

		private final TypeToken<T> typeToken;
		private final T value;
		private final String json;

		private TestWith(final TypeToken<T> typeToken, final T value, final String json) {
			this.typeToken = typeToken;
			this.value = value;
			this.json = json;
		}

	}

	private final TestWith<?> testWith;

	protected AbstractSimpleTest(final TestWith<?> testWith) {
		this.testWith = testWith;
	}

	protected static <T> TestWith<T> testWith(final TypeToken<T> typeToken, final T value, final String json) {
		return new TestWith<>(typeToken, value, json);
	}

	@Test
	public final void testRead() {
		final Object object = gson.fromJson(testWith.json, testWith.typeToken.getType());
		MatcherAssert.assertThat(object, CoreMatchers.is(testWith.value));
	}

	@Test
	public final void testWrite() {
		MatcherAssert.assertThat(gson.toJson(testWith.value, testWith.typeToken.getType()), CoreMatchers.is(testWith.json));
	}

}
