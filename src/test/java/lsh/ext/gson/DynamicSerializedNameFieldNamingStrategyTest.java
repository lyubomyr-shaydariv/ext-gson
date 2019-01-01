package lsh.ext.gson;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public final class DynamicSerializedNameFieldNamingStrategyTest {

	@Test
	public void testTranslateNameForStaticMappings()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.get(DynamicSerializedNameFieldNamingStrategyTest::resolve);
		MatcherAssert.assertThat(unit.translateName(StaticFooBar.class.getDeclaredField("foo")), CoreMatchers.is("foo"));
		MatcherAssert.assertThat(unit.translateName(StaticFooBar.class.getDeclaredField("bar")), CoreMatchers.is("bar"));
	}

	@Test
	public void testTranslateNameForDynamicMappings()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.get(DynamicSerializedNameFieldNamingStrategyTest::resolve);
		MatcherAssert.assertThat(unit.translateName(DynamicFooBar.class.getDeclaredField("foo")), CoreMatchers.is("FOO1"));
		MatcherAssert.assertThat(unit.translateName(DynamicFooBar.class.getDeclaredField("bar")), CoreMatchers.is("BAR2"));
	}

	@Test
	public void testTranslateForDynamicMappingsIfNull()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.get(value -> null);
		MatcherAssert.assertThat(unit.translateName(DynamicFooBar.class.getDeclaredField("foo")), CoreMatchers.is("foo"));
		MatcherAssert.assertThat(unit.translateName(DynamicFooBar.class.getDeclaredField("bar")), CoreMatchers.is("bar"));
	}

	@Test
	public void testTranslateNameForStaticMappingsIntegration() {
		final Gson gson = new GsonBuilder()
				.setFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategy.get(DynamicSerializedNameFieldNamingStrategyTest::resolve))
				.create();
		final StaticFooBar staticFooBar = gson.fromJson("{\"foo\":\"1\",\"bar\":\"2\"}", StaticFooBar.class);
		MatcherAssert.assertThat(staticFooBar.foo, CoreMatchers.is("1"));
		MatcherAssert.assertThat(staticFooBar.bar, CoreMatchers.is("2"));
	}

	@Test
	public void testTranslateNameForDynamicMappingsIntegration() {
		final Gson gson = new GsonBuilder()
				.setFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategy.get(DynamicSerializedNameFieldNamingStrategyTest::resolve))
				.create();
		final DynamicFooBar dynamicFooBar = gson.fromJson("{\"FOO1\":\"1\",\"BAR2\":\"2\"}", DynamicFooBar.class);
		MatcherAssert.assertThat(dynamicFooBar.foo, CoreMatchers.is("1"));
		MatcherAssert.assertThat(dynamicFooBar.bar, CoreMatchers.is("2"));
	}

	@Test
	public void testTranslateNameForDynamicMappingsIntegrationWithSerializedNameThatHasHigherPriority() {
		final IFieldNamingResolver mockNameResolver = Mockito.mock(IFieldNamingResolver.class);
		final Gson gson = new GsonBuilder()
				.setFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategy.get(mockNameResolver))
				.create();
		final MixedFooBar mixedFooBar = gson.fromJson("{\"staticFoo\":\"1\",\"staticBar\":\"2\"}", MixedFooBar.class);
		MatcherAssert.assertThat(mixedFooBar.foo, CoreMatchers.is("1"));
		MatcherAssert.assertThat(mixedFooBar.bar, CoreMatchers.is("2"));
		Mockito.verifyZeroInteractions(mockNameResolver);
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
