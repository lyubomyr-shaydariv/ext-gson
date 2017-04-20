package lsh.ext.gson.stategies;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import lsh.ext.gson.annotations.DynamicSerializedName;
import org.junit.Test;

import static lsh.ext.gson.stategies.DynamicSerializedNameFieldNamingStrategy.getDynamicSerializedNameFieldNamingStrategy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public final class DynamicSerializedNameFieldNamingStrategyTest {

	@Test
	public void testTranslateNameForStaticMappings()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = getDynamicSerializedNameFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategyTest::resolve);
		assertThat(unit.translateName(StaticFooBar.class.getDeclaredField("foo")), is("foo"));
		assertThat(unit.translateName(StaticFooBar.class.getDeclaredField("bar")), is("bar"));
	}

	@Test
	public void testTranslateNameForDynamicMappings()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = getDynamicSerializedNameFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategyTest::resolve);
		assertThat(unit.translateName(DynamicFooBar.class.getDeclaredField("foo")), is("FOO1"));
		assertThat(unit.translateName(DynamicFooBar.class.getDeclaredField("bar")), is("BAR2"));
	}

	@Test
	public void testTranslateForDynamicMappingsIfNull()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = getDynamicSerializedNameFieldNamingStrategy(value -> null);
		assertThat(unit.translateName(DynamicFooBar.class.getDeclaredField("foo")), is("foo"));
		assertThat(unit.translateName(DynamicFooBar.class.getDeclaredField("bar")), is("bar"));
	}

	@Test
	public void testTranslateNameForStaticMappingsIntegration() {
		final Gson gson = new GsonBuilder()
				.setFieldNamingStrategy(getDynamicSerializedNameFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategyTest::resolve))
				.create();
		final StaticFooBar staticFooBar = gson.fromJson("{\"foo\":\"1\",\"bar\":\"2\"}", StaticFooBar.class);
		assertThat(staticFooBar.foo, is("1"));
		assertThat(staticFooBar.bar, is("2"));
	}

	@Test
	public void testTranslateNameForDynamicMappingsIntegration() {
		final Gson gson = new GsonBuilder()
				.setFieldNamingStrategy(getDynamicSerializedNameFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategyTest::resolve))
				.create();
		final DynamicFooBar dynamicFooBar = gson.fromJson("{\"FOO1\":\"1\",\"BAR2\":\"2\"}", DynamicFooBar.class);
		assertThat(dynamicFooBar.foo, is("1"));
		assertThat(dynamicFooBar.bar, is("2"));
	}

	@Test
	public void testTranslateNameForDynamicMappingsIntegrationWithSerializedNameThatHasHigherPriority() {
		final IFieldNamingResolver mockNameResolver = mock(IFieldNamingResolver.class);
		final Gson gson = new GsonBuilder()
				.setFieldNamingStrategy(getDynamicSerializedNameFieldNamingStrategy(mockNameResolver))
				.create();
		final MixedFooBar mixedFooBar = gson.fromJson("{\"staticFoo\":\"1\",\"staticBar\":\"2\"}", MixedFooBar.class);
		assertThat(mixedFooBar.foo, is("1"));
		assertThat(mixedFooBar.bar, is("2"));
		verifyZeroInteractions(mockNameResolver);
	}

	private static String resolve(final String value) {
		switch ( value ) {
		case "#foo":
			return "FOO1";
		case "#bar":
			return "BAR2";
		default:
			throw new AssertionError(value);
		}
	}

	private static final class StaticFooBar {

		private final String foo = null;
		private final String bar = null;

	}

	private static final class DynamicFooBar {

		@DynamicSerializedName("#foo")
		private final String foo = null;

		@DynamicSerializedName("#bar")
		private final String bar = null;

	}

	private static final class MixedFooBar {

		@SerializedName("staticFoo")
		@DynamicSerializedName("#foo")
		private final String foo = null;

		@SerializedName("staticBar")
		@DynamicSerializedName("#bar")
		private final String bar = null;

	}

}
