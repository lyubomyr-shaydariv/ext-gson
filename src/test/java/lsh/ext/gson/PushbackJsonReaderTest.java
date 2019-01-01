package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.gson.stream.JsonToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PushbackJsonReaderTest {

	@Test
	public void testBeginArray()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[")) ) {
			unit.beginArray();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.arrayBegin()));
			unit.pushback();
			unit.beginArray();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testEndArray()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
			unit.beginArray();
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayEnd()));
			unit.pushback();
			unit.beginArray();
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testBeginObject()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{")) ) {
			unit.beginObject();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.objectBegin()));
			unit.pushback();
			unit.beginObject();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testEndObject()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{}")) ) {
			unit.beginObject();
			unit.endObject();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.objectBegin(), ValuedJsonToken.objectEnd()));
			unit.pushback();
			unit.beginObject();
			unit.endObject();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testHasNextForArrays()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[0]")) ) {
			unit.beginArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(0));
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(0), ValuedJsonToken.arrayEnd()));
			unit.pushback();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
			unit.beginArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(0));
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testHasNextForEmptyArrays()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
			unit.beginArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayEnd()));
			unit.pushback();
			unit.beginArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testHasNextForObjects()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{\"key\":\"value\"}")) ) {
			unit.beginObject();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
			MatcherAssert.assertThat(unit.nextName(), CoreMatchers.is("key"));
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
			MatcherAssert.assertThat(unit.nextString(), CoreMatchers.is("value"));
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endObject();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.objectBegin(), ValuedJsonToken.name("key"), ValuedJsonToken.value("value"), ValuedJsonToken.objectEnd()));
			unit.pushback();
			unit.beginObject();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
			MatcherAssert.assertThat(unit.nextName(), CoreMatchers.is("key"));
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
			MatcherAssert.assertThat(unit.nextString(), CoreMatchers.is("value"));
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endObject();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testHasNextForEmptyObjects()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{}")) ) {
			unit.beginObject();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endObject();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.objectBegin(), ValuedJsonToken.objectEnd()));
			unit.pushback();
			unit.beginObject();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endObject();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testHasNextForNestedArrays()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[[]]")) ) {
			unit.beginArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
			unit.beginArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endArray();
			unit.pushback();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayEnd(), ValuedJsonToken.arrayEnd()));
			unit.beginArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
			unit.beginArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endArray();
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testPeek()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
			MatcherAssert.assertThat(unit.peek(), CoreMatchers.is(JsonToken.BEGIN_ARRAY));
			unit.beginArray();
			MatcherAssert.assertThat(unit.peek(), CoreMatchers.is(JsonToken.END_ARRAY));
			unit.endArray();
			MatcherAssert.assertThat(unit.peek(), CoreMatchers.is(JsonToken.END_DOCUMENT));
			unit.pushback();
			MatcherAssert.assertThat(unit.peek(), CoreMatchers.is(JsonToken.BEGIN_ARRAY));
			unit.beginArray();
			MatcherAssert.assertThat(unit.peek(), CoreMatchers.is(JsonToken.END_ARRAY));
			unit.endArray();
			MatcherAssert.assertThat(unit.peek(), CoreMatchers.is(JsonToken.END_DOCUMENT));
		}
	}

	@Test
	public void testNextName()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{\"key\":\"value\"}")) ) {
			unit.beginObject();
			MatcherAssert.assertThat(unit.nextName(), CoreMatchers.is("key"));
			MatcherAssert.assertThat(unit.nextString(), CoreMatchers.is("value"));
			unit.endObject();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.objectBegin(), ValuedJsonToken.name("key"), ValuedJsonToken.value("value"), ValuedJsonToken.objectEnd()));
			unit.pushback();
			unit.beginObject();
			MatcherAssert.assertThat(unit.nextName(), CoreMatchers.is("key"));
			MatcherAssert.assertThat(unit.nextString(), CoreMatchers.is("value"));
			unit.endObject();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testNextString()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("\"value\"")) ) {
			MatcherAssert.assertThat(unit.nextString(), CoreMatchers.is("value"));
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.value("value")));
			unit.pushback();
			MatcherAssert.assertThat(unit.nextString(), CoreMatchers.is("value"));
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testNextBoolean()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("true")) ) {
			MatcherAssert.assertThat(unit.nextBoolean(), CoreMatchers.is(true));
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.value(true)));
			unit.pushback();
			MatcherAssert.assertThat(unit.nextBoolean(), CoreMatchers.is(true));
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testNextNull()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("null")) ) {
			unit.nextNull();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.value()));
			unit.pushback();
			unit.nextNull();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testNextDouble()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("3.1415926")) ) {
			MatcherAssert.assertThat(unit.nextDouble(), CoreMatchers.is(3.1415926D));
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.value(3.1415926D)));
			unit.pushback();
			MatcherAssert.assertThat(unit.nextDouble(), CoreMatchers.is(3.1415926D));
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testNextDoubleFromString()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("\"3.1415926\"")) ) {
			MatcherAssert.assertThat(unit.nextString(), CoreMatchers.is("3.1415926"));
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.value("3.1415926")));
			unit.pushback();
			MatcherAssert.assertThat(unit.nextDouble(), CoreMatchers.is(3.1415926D));
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testNextLong()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("31415926")) ) {
			MatcherAssert.assertThat(unit.nextLong(), CoreMatchers.is(31415926L));
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.value(31415926L)));
			unit.pushback();
			MatcherAssert.assertThat(unit.nextLong(), CoreMatchers.is(31415926L));
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testNextLongFromString()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("\"31415926\"")) ) {
			MatcherAssert.assertThat(unit.nextString(), CoreMatchers.is("31415926"));
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.value("31415926")));
			unit.pushback();
			MatcherAssert.assertThat(unit.nextLong(), CoreMatchers.is(31415926L));
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testNextInt()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("31415926")) ) {
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(31415926));
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.value(31415926)));
			unit.pushback();
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(31415926));
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void textNextIntFromString()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("\"31415926\"")) ) {
			MatcherAssert.assertThat(unit.nextString(), CoreMatchers.is("31415926"));
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.value("31415926")));
			unit.pushback();
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(31415926));
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testClose()
			throws IOException {
		@SuppressWarnings("resource")
		final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[0]"));
		try {
			unit.beginArray();
			unit.nextInt();
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(0), ValuedJsonToken.arrayEnd()));
		} finally {
			unit.close();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testSkipValue()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[0,1,2,[8],[1000,2000,3000]]")) ) {
			unit.beginArray();
			unit.skipValue();
			unit.skipValue();
			unit.skipValue();
			unit.skipValue();
			unit.skipValue();
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback(ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(0D), ValuedJsonToken.value(1D), ValuedJsonToken.value(2D), ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(8D), ValuedJsonToken.arrayEnd(), ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(1000D), ValuedJsonToken.value(2000D), ValuedJsonToken.value(3000D), ValuedJsonToken.arrayEnd(), ValuedJsonToken.arrayEnd()));
			unit.pushback();
			unit.beginArray();
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(0));
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(1));
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(2));
			unit.skipValue();
			unit.beginArray();
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(1000));
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(2000));
			MatcherAssert.assertThat(unit.nextInt(), CoreMatchers.is(3000));
			unit.endArray();
			unit.endArray();
			MatcherAssert.assertThat(unit, mustPushback());
		}
	}

	@Test
	public void testToStringForRecordingIsNotImplementedYet() {
		Assertions.assertThrows(NotImplementedYetException.class, () -> {
			try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
				@SuppressWarnings("unused")
				final String s = unit.toString();
			}
		});
	}

	@Test
	public void testToStringForReplayingIsNotImplementedYet() {
		Assertions.assertThrows(NotImplementedYetException.class, () -> {
			try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
				unit.pushback();
				@SuppressWarnings("unused")
				final String s = unit.toString();
			}
		});
	}

	@Test
	public void testToStringForReplayed()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[1,2,3]")) ) {
			unit.beginArray();
			unit.nextInt();
			unit.nextInt();
			unit.pushback();
			unit.beginArray();
			unit.nextInt();
			unit.nextInt();
			MatcherAssert.assertThat(unit.toString(), CoreMatchers.is("PushbackJsonReader at line 1 column 5 path $[2]"));
		}
	}

	@Test
	public void testGetPathForRecordingIsNotImplementedYet() {
		Assertions.assertThrows(NotImplementedYetException.class, () -> {
			try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
				unit.getPath();
			}
		});
	}

	@Test
	public void testGetPathForReplayingIsNotImplementedYet() {
		Assertions.assertThrows(NotImplementedYetException.class, () -> {
			try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
				unit.pushback();
				unit.getPath();
			}
		});
	}

	@Test
	public void testGetPathForReplayed()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[1,2,3]")) ) {
			unit.beginArray();
			unit.nextInt();
			unit.nextInt();
			unit.pushback();
			unit.beginArray();
			unit.nextInt();
			unit.nextInt();
			MatcherAssert.assertThat(unit.getPath(), CoreMatchers.is("$[2]"));
		}
	}

	private static Matcher<PushbackJsonReader> mustPushback(final ValuedJsonToken<?>... tokens) {
		final Iterable<ValuedJsonToken<?>> tokensCopy = ImmutableList.copyOf(tokens);
		return new TypeSafeMatcher<PushbackJsonReader>() {
			@Override
			protected boolean matchesSafely(final PushbackJsonReader pushbackJsonReader) {
				return Iterators.elementsEqual(tokensCopy.iterator(), pushbackJsonReader.iterator());
			}

			@Override
			public void describeTo(final Description description) {
				description.appendValue(tokensCopy);
			}
		};
	}

}
