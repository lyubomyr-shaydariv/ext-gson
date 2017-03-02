package lsh.ext.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;

final class MoreMatchers {

	private MoreMatchers() {
	}

	static Matcher<Object> refersNone(final Object o1, final Object o2) {
		return allOf(not(sameInstance(o1)), not(sameInstance(o2)));
	}

	static Matcher<Object> refersFirst(final Object o1, final Object o2) {
		return allOf(sameInstance(o1), not(sameInstance(o2)));
	}

	static Matcher<JsonObject> hasProperty(final String key, final JsonElement value) {
		return new TypeSafeMatcher<JsonObject>() {
			@Override
			protected boolean matchesSafely(final JsonObject jsonObject) {
				return jsonObject.get(key).equals(value);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("has property ");
				description.appendValue(key);
				description.appendText(" with value ");
				description.appendValue(value);
			}
		};
	}

}
