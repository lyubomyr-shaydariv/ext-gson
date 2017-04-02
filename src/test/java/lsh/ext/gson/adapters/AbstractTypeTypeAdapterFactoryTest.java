package lsh.ext.gson.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.junit.Test;

import static lsh.ext.gson.adapters.AbstractTypeTypeAdapterFactory.getAbstractTypeTypeAdapterFactory;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public final class AbstractTypeTypeAdapterFactoryTest {

	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapterFactory(getAbstractTypeTypeAdapterFactory("$T", "$V"))
			.create();

	@Test
	public void testWrite() {
		final Value foo = new Value(new Foo());
		assertThat(gson.toJson(foo), is("{\"value\":{\"$T\":\"" + Foo.class.getTypeName() + "\",\"$V\":{}}}"));
	}

	@Test
	public void testRead() {
		final String json = "{\"value\":{\"$T\":\"" + Bar.class.getTypeName() + "\",\"$V\":{}}}";
		final Value value = gson.fromJson(json, Value.class);
		assertThat(value.value, instanceOf(Bar.class));
	}

	@Test(expected = JsonSyntaxException.class)
	public void testReadForNotExistingClass() {
		final String json = "{\"value\":{\"$T\":\"not-existing-type\",\"$V\":{}}}";
		gson.fromJson(json, Value.class);
	}

	@Test(expected = JsonSyntaxException.class)
	public void testReadForSwappedFields() {
		final String json = "{\"value\":{\"$V\":{}\"$T\":\"" + Bar.class.getTypeName() + "\"}}";
		gson.fromJson(json, Value.class);
	}

	private static final class Value {

		private final Abstract value;

		private Value(final Abstract value) {
			this.value = value;
		}

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