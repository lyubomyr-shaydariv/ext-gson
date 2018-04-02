package lsh.ext.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

final class MoreMatchers {

	private MoreMatchers() {
	}

	static Matcher<Object> refersNone(final Object o1, final Object o2) {
		return CoreMatchers.allOf(CoreMatchers.not(CoreMatchers.sameInstance(o1)), CoreMatchers.not(CoreMatchers.sameInstance(o2)));
	}

	static Matcher<Object> refersFirst(final Object o1, final Object o2) {
		return CoreMatchers.allOf(CoreMatchers.sameInstance(o1), CoreMatchers.not(CoreMatchers.sameInstance(o2)));
	}

	static Matcher<ParameterizedType> hasRawType(final Type rawType) {
		return new TypeSafeMatcher<ParameterizedType>() {
			@Override
			protected boolean matchesSafely(final ParameterizedType type) {
				return type.getRawType().equals(rawType);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendValue(rawType);
			}
		};
	}

	static Matcher<ParameterizedType> hasActualTypeArguments(final Type... actualTypeArguments) {
		return new TypeSafeMatcher<ParameterizedType>() {
			@Override
			protected boolean matchesSafely(final ParameterizedType type) {
				return Arrays.equals(type.getActualTypeArguments(), actualTypeArguments);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendValue(actualTypeArguments);
			}
		};
	}

	static Matcher<ParameterizedType> isParameterizedType(final Type rawType, final Type... actualTypeArguments) {
		return CoreMatchers.allOf(hasRawType(rawType), hasActualTypeArguments(actualTypeArguments));
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
