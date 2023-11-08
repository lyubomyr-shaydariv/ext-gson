package lsh.ext.gson;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public final class DynamicSerializedNameFieldNamingStrategyTest {

	@Test
	public void testTranslateNameForStaticMappings()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.getInstance(DynamicSerializedNameFieldNamingStrategyTest::resolve);
		Assertions.assertEquals("foo", unit.translateName(StaticFooBar.class.getDeclaredField("foo")));
		Assertions.assertEquals("bar", unit.translateName(StaticFooBar.class.getDeclaredField("bar")));
	}

	@Test
	public void testTranslateNameForDynamicMappings()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.getInstance(DynamicSerializedNameFieldNamingStrategyTest::resolve);
		Assertions.assertEquals("FOO1", unit.translateName(DynamicFooBar.class.getDeclaredField("foo")));
		Assertions.assertEquals("BAR2", unit.translateName(DynamicFooBar.class.getDeclaredField("bar")));
	}

	@Test
	public void testTranslateForDynamicMappingsIfNull()
			throws NoSuchFieldException {
		final FieldNamingStrategy unit = DynamicSerializedNameFieldNamingStrategy.getInstance(value -> null);
		Assertions.assertEquals("foo", unit.translateName(DynamicFooBar.class.getDeclaredField("foo")));
		Assertions.assertEquals("bar", unit.translateName(DynamicFooBar.class.getDeclaredField("bar")));
	}

	@Test
	public void testTranslateNameForStaticMappingsIntegration() {
		final Gson gson = Gsons.Builders.createNormalized()
				.setFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategy.getInstance(DynamicSerializedNameFieldNamingStrategyTest::resolve))
				.create();
		final StaticFooBar staticFooBar = gson.fromJson("{\"foo\":\"1\",\"bar\":\"2\"}", StaticFooBar.class);
		Assertions.assertEquals("1", staticFooBar.foo);
		Assertions.assertEquals("2", staticFooBar.bar);
	}

	@Test
	public void testTranslateNameForDynamicMappingsIntegration() {
		final Gson gson = Gsons.Builders.createNormalized()
				.setFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategy.getInstance(DynamicSerializedNameFieldNamingStrategyTest::resolve))
				.create();
		final DynamicFooBar dynamicFooBar = gson.fromJson("{\"FOO1\":\"1\",\"BAR2\":\"2\"}", DynamicFooBar.class);
		Assertions.assertEquals("1", dynamicFooBar.foo);
		Assertions.assertEquals("2", dynamicFooBar.bar);
	}

	@Test
	public void testTranslateNameForDynamicMappingsIntegrationWithSerializedNameThatHasHigherPriority() {
		final IFieldNamingResolver resolverMock = Mockito.mock(IFieldNamingResolver.class);
		final Gson gson = Gsons.Builders.createNormalized()
				.setFieldNamingStrategy(DynamicSerializedNameFieldNamingStrategy.getInstance(resolverMock))
				.create();
		final MixedFooBar mixedFooBar = gson.fromJson("{\"staticFoo\":\"1\",\"staticBar\":\"2\"}", MixedFooBar.class);
		Assertions.assertEquals("1", mixedFooBar.foo);
		Assertions.assertEquals("2", mixedFooBar.bar);
		Mockito.verifyNoMoreInteractions(resolverMock);
	}

	private static String resolve(final String value) {
		return switch ( value ) {
			case "#foo" -> "FOO1";
			case "#bar" -> "BAR2";
			default -> throw new AssertionError(value);
		};
	}

	private record StaticFooBar(
			String foo,
			String bar
	) {
	}

	private record DynamicFooBar(
			@DynamicSerializedName("#foo") String foo,
			@DynamicSerializedName("#bar") String bar
	) {
	}

	private record MixedFooBar(
			@SerializedName("staticFoo") @DynamicSerializedName("#foo") String foo,
			@SerializedName("staticBar") @DynamicSerializedName("#bar") String bar
	) {
	}

}
