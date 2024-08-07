package lsh.ext.gson;

import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class JsonObjectsTest {

	private static final Class<?> modifiableMapClass = new JsonObject()
			.asMap()
			.getClass();

	private static final String[] ks = {
			"foo", "bar", "baz", "qux", "quux"
	};

	private static final JsonPrimitive[] vs = IntStream.range(0, ks.length)
			.mapToObj(JsonPrimitive::new)
			.toArray(JsonPrimitive[]::new);

	private static final JsonPrimitive l = new JsonPrimitive("L");
	private static final JsonPrimitive r = new JsonPrimitive("R");

	@Test
	public void testJsonObject() {
		Assertions.assertEquals(
				new JsonObject(),
				JsonObjects.of()
		);
	}

	@Test
	public void testJsonObject1() {
		Assertions.assertEquals(
				generateJsonObject(1),
				JsonObjects.of(ks[0], vs[0])
		);
		Assertions.assertEquals(
				generateNullJsonObject(1),
				JsonObjects.of(ks[0], null)
		);
	}

	@Test
	@SuppressWarnings("DataFlowIssue")
	public void testJsonObject1AsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonObjects.of(null, new JsonPrimitive(ks[0])));
	}

	@Test
	public void testJsonObject2() {
		Assertions.assertEquals(
				generateJsonObject(2),
				JsonObjects.of(ks[0], vs[0], ks[1], vs[1])
		);
		Assertions.assertEquals(
				generateNullJsonObject(2),
				JsonObjects.of(ks[0], null, ks[1], null)
		);
	}

	@Test
	@SuppressWarnings("DataFlowIssue")
	public void testJsonObject2AsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonObjects.of(null, new JsonPrimitive(ks[0]), null, new JsonPrimitive(ks[1])));
	}

	@Test
	public void testJsonObject3() {
		Assertions.assertEquals(
				generateJsonObject(3),
				JsonObjects.of(ks[0], vs[0], ks[1], vs[1], ks[2], vs[2])
		);
		Assertions.assertEquals(
				generateNullJsonObject(3),
				JsonObjects.of(ks[0], null, ks[1], null, ks[2], null)
		);
	}

	@Test
	@SuppressWarnings("DataFlowIssue")
	public void testJsonObject3AsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonObjects.of(null, new JsonPrimitive(ks[0]), null, new JsonPrimitive(ks[1]), null, new JsonPrimitive(ks[2])));
	}

	@Test
	public void testJsonObject4() {
		Assertions.assertEquals(
				generateJsonObject(4),
				JsonObjects.of(ks[0], vs[0], ks[1], vs[1], ks[2], vs[2], ks[3], vs[3])
		);
		Assertions.assertEquals(
				generateNullJsonObject(4),
				JsonObjects.of(ks[0], null, ks[1], null, ks[2], null, ks[3], null)
		);
	}

	@Test
	@SuppressWarnings("DataFlowIssue")
	public void testJsonObject4AsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonObjects.of(null, new JsonPrimitive(ks[0]), null, new JsonPrimitive(ks[1]), null, new JsonPrimitive(ks[2]), null, new JsonPrimitive(ks[3])));
	}

	@Test
	public void testFrom() {
		final Map<String, ? extends JsonElement> map = Map.of(ks[0], vs[0], ks[1], vs[1], ks[2], vs[2]);
		final JsonObject jsonObject = JsonObjects.from(map);
		Assertions.assertEquals(map.size(), jsonObject.size());
		Assertions.assertSame(vs[0], jsonObject.get(ks[0]));
		Assertions.assertSame(vs[1], jsonObject.get(ks[1]));
		Assertions.assertSame(vs[2], jsonObject.get(ks[2]));
	}

	@Test
	public void testMergeIntoNewWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoNew(left, right);
		assertRefersNone(result, left, right);
		assertHasValues(result, r, r, r);
	}

	@Test
	public void testMergeIntoNew() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObjects.IMergePredicate predicateMock = Mockito.mock(JsonObjects.IMergePredicate.class);
		Mockito.when(predicateMock.canReplace(ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(false);
		final JsonObject result = JsonObjects.mergeIntoNew(left, right, predicateMock);
		assertRefersNone(result, left, right);
		Mockito.verify(predicateMock)
				.canReplace(ks[0], left, l, right, r);
		Mockito.verify(predicateMock)
				.canReplace(ks[1], left, l, right, r);
		Mockito.verify(predicateMock)
				.canReplace(ks[2], left, l, right, r);
		Mockito.verifyNoMoreInteractions(predicateMock);
	}

	@Test
	public void testMergeIntoNewWithReplace() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject actual = JsonObjects.mergeIntoNew(left, right, JsonObjects.IMergePredicate.replace);
		assertRefersNone(actual, left, right);
		assertHasValues(actual, r, r, r);
	}

	@Test
	public void testMergeIntoNewWithRetain() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject actual = JsonObjects.mergeIntoNew(left, right, JsonObjects.IMergePredicate.retain);
		assertRefersNone(actual, left, right);
		assertHasValues(actual, l, l, l);
	}

	@Test
	public void testMergeIntoNewWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject actual = JsonObjects.mergeIntoNew(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(ks[0]));
		assertRefersNone(actual, left, right);
		assertHasValues(actual, l, r, r);
	}

	@Test
	public void testMergeIntoLeftWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject actual = JsonObjects.mergeIntoLeft(left, right);
		assertRefersFirst(actual, left, right);
		assertHasValues(actual, r, r, r);
	}

	@Test
	public void testMergeIntoLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObjects.IMergePredicate predicateMock = Mockito.mock(JsonObjects.IMergePredicate.class);
		Mockito.when(predicateMock.canReplace(ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(false);
		final JsonObject actual = JsonObjects.mergeIntoLeft(left, right, predicateMock);
		assertRefersFirst(actual, left, right);
		Mockito.verify(predicateMock)
				.canReplace(ks[0], left, l, right, r);
		Mockito.verify(predicateMock)
				.canReplace(ks[1], left, l, right, r);
		Mockito.verify(predicateMock)
				.canReplace(ks[2], left, l, right, r);
		Mockito.verifyNoMoreInteractions(predicateMock);
	}

	@Test
	public void testMergeIntoLeftWithAlwaysReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject actual = JsonObjects.mergeIntoLeft(left, right, JsonObjects.IMergePredicate.replace);
		assertRefersFirst(actual, left, right);
		assertHasValues(actual, r, r, r);
	}

	@Test
	public void testMergeIntoLeftWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject actual = JsonObjects.mergeIntoLeft(left, right, JsonObjects.IMergePredicate.retain);
		assertRefersFirst(actual, left, right);
		assertHasValues(actual, l, l, l);
	}

	@Test
	public void testMergeIntoLeftWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject actual = JsonObjects.mergeIntoLeft(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(ks[0]));
		assertRefersFirst(actual, left, right);
		assertHasValues(actual, l, r, r);
	}

	private static Stream<Arguments> testObjects() {
		return Stream.of(
						JsonObjects.of(),
						JsonObjects.of(ks[0], l),
						JsonObjects.of(ks[0], l, ks[1], r)
				)
				.map(Arguments::of);
	}

	@ParameterizedTest
	@MethodSource("testObjects")
	public void testAsModifiableMap(final JsonObject jsonObject) {
		final Class<?> actualClass = JsonObjects.asModifiableMap(jsonObject)
				.getClass();
		Assertions.assertSame(modifiableMapClass, actualClass);
	}

	@ParameterizedTest
	@MethodSource("testObjects")
	public void testAsUnmodifiableMap(final JsonObject jsonObject) {
		final Class<?> actualClass = JsonObjects.asUnmodifiableMap(jsonObject)
				.getClass();
		Assertions.assertSame(Types.jdkUnmodifiableMapClass, actualClass);
	}

	private static JsonObject generateNullJsonObject(final int size) {
		final JsonObject jsonObject = new JsonObject();
		for ( int i = 0; i < size; i++ ) {
			jsonObject.add(ks[i], null);
		}
		return jsonObject;
	}

	private static JsonObject generateJsonObject(final int size) {
		final JsonObject jsonObject = new JsonObject();
		for ( int i = 0; i < size; i++ ) {
			jsonObject.add(ks[i], vs[i]);
		}
		return jsonObject;
	}

	private static JsonObject createLeftObject() {
		return JsonObjects.of(ks[0], l, ks[1], l, ks[2], l);
	}

	private static JsonObject createRightObject() {
		return JsonObjects.of(ks[0], r, ks[1], r, ks[2], r);
	}

	private static void assertHasValues(final JsonObject o, final JsonElement e1, final JsonElement e2, final JsonElement e3) {
		Assertions.assertEquals(e1, o.get(ks[0]));
		Assertions.assertEquals(e2, o.get(ks[1]));
		Assertions.assertEquals(e3, o.get(ks[2]));
	}

	private static void assertRefersNone(final JsonElement o, final JsonElement... es) {
		for ( final JsonElement e : es ) {
			Assertions.assertNotSame(o, e);
		}
	}

	private static void assertRefersFirst(final JsonElement o, final JsonElement e1, final JsonElement... es) {
		Assertions.assertSame(o, e1);
		assertRefersNone(o, es);
	}

}
