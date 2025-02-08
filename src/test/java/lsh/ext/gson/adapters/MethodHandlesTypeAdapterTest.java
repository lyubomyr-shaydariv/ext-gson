package lsh.ext.gson.adapters;

import java.io.IOException;
import java.io.StringReader;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lsh.ext.gson.Gsons;
import lsh.ext.gson.JsonObjects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public final class MethodHandlesTypeAdapterTest {

	private static final Gson gson = Gsons.getNormalized();
	private static final InstanceCreator<DataBag> dataBagInstanceCreator = type -> new DataBag();

	@Test
	public void testGetAndWriteFull() {
		final TypeAdapter<DataBag> unit = MethodHandlesTypeAdapter.getInstance(DataBag.class, dataBagInstanceCreator, gson);
		final JsonElement actual = unit.toJsonTree(new DataBag("value", 10, 3.14));
		final JsonObject expected = JsonObjects.of(
				"foo", new JsonPrimitive("value"),
				"bar", new JsonPrimitive(10),
				"baz", new JsonPrimitive(3.14)
		);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testReadAndSetFull()
			throws IOException {
		final DataBag spy = Mockito.spy(new DataBag());
		final InstanceCreator<DataBag> dataBagInstanceCreator = type -> spy;
		final TypeAdapter<DataBag> unit = MethodHandlesTypeAdapter.getInstance(DataBag.class, dataBagInstanceCreator, gson);
		final DataBag actual = unit.read(new JsonReader(new StringReader("{\"foo\":\"value\",\"bar\":10,\"baz\":3.14}")));
		final DataBag expected = new DataBag("value", 10, 3.14);
		Assertions.assertEquals(expected, actual);
		Mockito.verify(spy)
				.setFoo("value");
		Mockito.verify(spy)
				.setBar(10);
		Mockito.verify(spy)
				.setBaz(3.14);
	}

	@Test
	public void testReadAndSetPartial()
			throws IOException {
		final TypeAdapter<DataBag> unit = MethodHandlesTypeAdapter.getInstance(DataBag.class, dataBagInstanceCreator, gson);
		final DataBag actual = unit.read(new JsonReader(new StringReader("{\"bar\":10,\"baz\":3.14}")));
		final DataBag expected = new DataBag(DataBag.DEFAULT_FOO, 10, 3.14);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testReadAndSetAlternate()
			throws IOException {
		final TypeAdapter<DataBag> unit = MethodHandlesTypeAdapter.getInstance(DataBag.class, dataBagInstanceCreator, gson);
		final DataBag actual = unit.read(new JsonReader(new StringReader("{\"FOO\":\"value\"}")));
		final DataBag expected = new DataBag("value", DataBag.DEFAULT_BAR, DataBag.DEFAULT_BAZ);
		Assertions.assertEquals(expected, actual);
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	@ToString
	@SuppressWarnings("QuestionableName")
	private static final class DataBag {

		private static final String DEFAULT_FOO = "DEFAULT";
		private static final int DEFAULT_BAR = -1;
		private static final double DEFAULT_BAZ = Double.NaN;

		private String foo = DEFAULT_FOO;
		private int bar = DEFAULT_BAR;
		private double baz = DEFAULT_BAZ;

		@Nullable
		@SerializedName("foo")
		public String getFoo() {
			return foo;
		}

		@SerializedName(value = "foo", alternate = "FOO")
		public void setFoo(@Nullable final String foo) {
			this.foo = foo;
		}

		@SerializedName("bar")
		public int getBar() {
			return bar;
		}

		@SerializedName("bar")
		public void setBar(final int bar) {
			this.bar = bar;
		}

		@SerializedName("baz")
		public double getBaz() {
			return baz;
		}

		@SerializedName("baz")
		public void setBaz(final double baz) {
			this.baz = baz;
		}

	}

}
