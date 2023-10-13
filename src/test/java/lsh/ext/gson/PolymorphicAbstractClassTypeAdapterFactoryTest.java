package lsh.ext.gson;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PolymorphicAbstractClassTypeAdapterFactoryTest {

	private static final Gson gson = Gsons.Builders.createNormalized()
			.registerTypeAdapterFactory(PolymorphicAbstractClassTypeAdapterFactory.getInstance("$T", "$V"))
			.create();

	@Test
	public void testWrite() {
		final Value foo = new Value(new Foo());
		Assertions.assertEquals("{\"value\":{\"$T\":\"" + Foo.class.getTypeName() + "\",\"$V\":{}}}", gson.toJson(foo));
	}

	@Test
	public void testRead() {
		final String json = "{\"value\":{\"$T\":\"" + Bar.class.getTypeName() + "\",\"$V\":{}}}";
		final Value value = gson.fromJson(json, Value.class);
		Assertions.assertInstanceOf(Bar.class, value.value);
	}

	@Test
	public void testReadForNotExistingClass() {
		Assertions.assertThrows(JsonSyntaxException.class, () -> {
			final String json = "{\"value\":{\"$T\":\"not-existing-type\",\"$V\":{}}}";
			gson.fromJson(json, Value.class);
		});
	}

	@Test
	public void testReadForSwappedFields() {
		Assertions.assertThrows(JsonSyntaxException.class, () -> {
			final String json = "{\"value\":{\"$V\":{}\"$T\":\"" + Bar.class.getTypeName() + "\"}}";
			gson.fromJson(json, Value.class);
		});
	}

	private record Value(
			Abstract value
	) {
	}

	private abstract static class Abstract {
	}

	private static final class Foo
			extends Abstract {
	}

	private static final class Bar
			extends Abstract {
	}

}
