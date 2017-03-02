package lsh.ext.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.hamcrest.Matcher;
import org.junit.Test;

import static lsh.ext.gson.JsonElements.mergeIntoLeft;
import static lsh.ext.gson.JsonElements.mergeIntoNew;
import static lsh.ext.gson.JsonObjectBuilders.jsonObject;
import static lsh.ext.gson.JsonObjectMergePredicates.alwaysReplaceLeft;
import static lsh.ext.gson.JsonObjectMergePredicates.neverReplaceLeft;
import static lsh.ext.gson.MoreMatchers.hasProperty;
import static lsh.ext.gson.MoreMatchers.refersFirst;
import static lsh.ext.gson.MoreMatchers.refersNone;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public final class JsonElementsTest {

	private static final String K1 = "foo";
	private static final String K2 = "bar";
	private static final String K3 = "baz";

	private static final JsonPrimitive l = new JsonPrimitive("L");
	private static final JsonPrimitive r = new JsonPrimitive("R");

	@Test
	public void testMergeIntoNewWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoNew(left, right);
		assertThat(result, refersNone(left, right));
		assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoNew() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = mock(IJsonObjectMergePredicate.class);
		final JsonObject result = mergeIntoNew(left, right, mockPredicate);
		assertThat(result, refersNone(left, right));
		verify(mockPredicate).replace(K1, left, l, right, r);
		verify(mockPredicate).replace(K2, left, l, right, r);
		verify(mockPredicate).replace(K3, left, l, right, r);
		verifyNoMoreInteractions(mockPredicate);
	}

	@Test
	public void testMergeIntoNewWithAlwaysReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoNew(left, right, alwaysReplaceLeft());
		assertThat(result, refersNone(left, right));
		assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoNewWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoNew(left, right, neverReplaceLeft());
		assertThat(result, refersNone(left, right));
		assertThat(result, hasPropertiesOf(l, l, l));
	}

	@Test
	public void testMergeIntoNewWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoNew(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		assertThat(result, refersNone(left, right));
		assertThat(result, hasPropertiesOf(l, r, r));
	}

	@Test
	public void testMergeIntoLeftWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoLeft(left, right);
		assertThat(result, refersFirst(left, right));
		assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = mock(IJsonObjectMergePredicate.class);
		final JsonObject result = mergeIntoLeft(left, right, mockPredicate);
		assertThat(result, refersFirst(left, right));
		verify(mockPredicate).replace(K1, left, l, right, r);
		verify(mockPredicate).replace(K2, left, l, right, r);
		verify(mockPredicate).replace(K3, left, l, right, r);
		verifyNoMoreInteractions(mockPredicate);
	}

	@Test
	public void testMergeIntoLeftWithAlwaysReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoLeft(left, right, alwaysReplaceLeft());
		assertThat(result, refersFirst(left, right));
		assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoLeftWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoLeft(left, right, neverReplaceLeft());
		assertThat(result, refersFirst(left, right));
		assertThat(result, hasPropertiesOf(l, l, l));
	}

	@Test
	public void testMergeIntoLeftWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoLeft(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		assertThat(result, refersFirst(left, right));
		assertThat(result, hasPropertiesOf(l, r, r));
	}

	private static JsonObject createLeftObject() {
		return jsonObject(K1, l, K2, l, K3, l);
	}

	private static JsonObject createRightObject() {
		return jsonObject(K1, r, K2, r, K3, r);
	}

	private static Matcher<JsonObject> hasPropertiesOf(final JsonElement v1, final JsonElement v2, final JsonElement v3) {
		return allOf(hasProperty(K1, v1), hasProperty(K2, v2), hasProperty(K3, v3));
	}

}
