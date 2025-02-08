package lsh.ext.gson.adapters;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class MethodHandlesTypeAdapterTest {

	private static final Gson gson = Gsons.getNormalized();
	private static final InstanceCreator<DataBag> dataBagInstanceCreator = type -> new DataBag();
	private static final MethodHandlesTypeAdapter.IDeserializer deserializer = new MethodHandlesTypeAdapter.IDeserializer() {
		@Override
		public <T> T read(final Type type, final JsonReader jsonReader) {
			return gson.fromJson(jsonReader, type);
		}
	};

	@Test
	public void testFull()
			throws IOException {
		final TypeAdapter<DataBag> unit = MethodHandlesTypeAdapter.getInstance(DataBag.class, dataBagInstanceCreator, deserializer);
		final DataBag actual = unit.read(new JsonReader(new StringReader("{\"foo\":\"value\",\"bar\":10,\"baz\":3.14}")));
		final DataBag expected = new DataBag("value"/*, 10, 3.14F*/);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testPartial()
			throws IOException {
		final TypeAdapter<DataBag> unit = MethodHandlesTypeAdapter.getInstance(DataBag.class, dataBagInstanceCreator, deserializer);
		final DataBag actual = unit.read(new JsonReader(new StringReader("{\"bar\":10,\"baz\":3.14}")));
		final DataBag expected = new DataBag(DataBag.DEFAULT_FOO/*, 10, 3.14F*/);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testAlternate()
			throws IOException {
		final TypeAdapter<DataBag> unit = MethodHandlesTypeAdapter.getInstance(DataBag.class, dataBagInstanceCreator, deserializer);
		final DataBag actual = unit.read(new JsonReader(new StringReader("{\"FOO\":\"value\"}")));
		final DataBag expected = new DataBag("value"/*, DataBag.DEFAULT_BAR, DataBag.DEFAULT_BAZ*/);
		Assertions.assertEquals(expected, actual);
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@EqualsAndHashCode
	@ToString
	@SuppressWarnings("QuestionableName")
	private static final class DataBag {

		private static final String DEFAULT_FOO = "DEFAULT";
//		private static final int DEFAULT_BAR = -1;
//		private static final float DEFAULT_BAZ = Float.NaN;

		private String foo = DEFAULT_FOO;
//		private int bar = DEFAULT_BAR;
//		private float baz = DEFAULT_BAZ;

		@SerializedName(value = "foo", alternate = "FOO")
		public void setFoo(@Nullable final String foo) {
			this.foo = foo;
		}
//
//		@SerializedName("bar")
//		public void setBar(final int bar) {
//			this.bar = bar;
//		}
//
//		@SerializedName("baz")
//		public void setBaz(final float baz) {
//			this.baz = baz;
//		}

	}

}
