package lsh.ext.gson;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public final class DynamicSerializedNameFieldNamingStrategyTest {

	private static final String FOO_NAME = "#foo";
	private static final String BAR_NAME = "#bar";

	@Test
	public void testTranslateNameForStaticMappings()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.getInstance(DynamicSerializedNameFieldNamingStrategyTest::translate);
		Assertions.assertEquals("foo", unit.translateName(StaticFooBar.class.getDeclaredField("foo")));
		Assertions.assertEquals("bar", unit.translateName(StaticFooBar.class.getDeclaredField("bar")));
	}

	@Test
	public void testTranslateNameForDynamicMappings()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.getInstance(DynamicSerializedNameFieldNamingStrategyTest::translate);
		Assertions.assertEquals("FOO1", unit.translateName(DynamicFooBar.class.getDeclaredField("foo")));
		Assertions.assertEquals("BAR2", unit.translateName(DynamicFooBar.class.getDeclaredField("bar")));
	}

	@Test
	public void testTranslateForDynamicMappingsIfNull()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.getInstance(name -> null);
		Assertions.assertEquals("foo", unit.translateName(DynamicFooBar.class.getDeclaredField("foo")));
		Assertions.assertEquals("bar", unit.translateName(DynamicFooBar.class.getDeclaredField("bar")));
	}

	@Test
	public void testTranslateNameForStaticMappingsIntegration() {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.getInstance(DynamicSerializedNameFieldNamingStrategyTest::translate);
		final Gson gson = Gsons.Builders.createNormalized()
				.setFieldNamingStrategy(unit)
				.create();
		final StaticFooBar staticFooBar = gson.fromJson("{\"foo\":\"1\",\"bar\":\"2\"}", StaticFooBar.class);
		Assertions.assertEquals("1", staticFooBar.foo);
		Assertions.assertEquals("2", staticFooBar.bar);
	}

	@Test
	public void testTranslateNameForDynamicMappingsIntegration() {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.getInstance(DynamicSerializedNameFieldNamingStrategyTest::translate);
		final Gson gson = Gsons.Builders.createNormalized()
				.setFieldNamingStrategy(unit)
				.create();
		final DynamicFooBar dynamicFooBar = gson.fromJson("{\"FOO1\":\"1\",\"BAR2\":\"2\"}", DynamicFooBar.class);
		Assertions.assertEquals("1", dynamicFooBar.foo);
		Assertions.assertEquals("2", dynamicFooBar.bar);
	}

	@Test
	public void testTranslateNameForDynamicMappingsIntegrationWithSerializedNameThatHasHigherPriority() {
		final INamingStrategy namingStrategyMock = Mockito.mock(INamingStrategy.class);
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.getInstance(namingStrategyMock);
		final Gson gson = Gsons.Builders.createNormalized()
				.setFieldNamingStrategy(unit)
				.create();
		final MixedFooBar mixedFooBar = gson.fromJson("{\"staticFoo\":\"1\",\"staticBar\":\"2\"}", MixedFooBar.class);
		Assertions.assertEquals("1", mixedFooBar.foo);
		Assertions.assertEquals("2", mixedFooBar.bar);
		Mockito.verifyNoInteractions(namingStrategyMock);
	}

	private static String translate(final String name) {
		return switch ( name ) {
			case FOO_NAME -> "FOO1";
			case BAR_NAME -> "BAR2";
			default -> throw new AssertionError(name);
		};
	}

	private record StaticFooBar(
			String foo,
			String bar
	) {
	}

	private record DynamicFooBar(
			@DynamicSerializedName(FOO_NAME) String foo,
			@DynamicSerializedName(BAR_NAME) String bar
	) {
	}

	private record MixedFooBar(
			@SerializedName("staticFoo") @DynamicSerializedName(FOO_NAME) String foo,
			@SerializedName("staticBar") @DynamicSerializedName(BAR_NAME) String bar
	) {
	}

}
