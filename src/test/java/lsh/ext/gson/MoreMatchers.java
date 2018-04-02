package lsh.ext.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public final class MoreMatchers {

	private MoreMatchers() {
	}

	public static Matcher<Object> refersNone(final Object o1, final Object o2) {
		return CoreMatchers.allOf(CoreMatchers.not(CoreMatchers.sameInstance(o1)), CoreMatchers.not(CoreMatchers.sameInstance(o2)));
	}

	public static Matcher<Object> refersFirst(final Object o1, final Object o2) {
		return CoreMatchers.allOf(CoreMatchers.sameInstance(o1), CoreMatchers.not(CoreMatchers.sameInstance(o2)));
	}

	public static Matcher<ParameterizedType> hasRawType(final Type rawType) {
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

	public static Matcher<ParameterizedType> hasActualTypeArguments(final Type... actualTypeArguments) {
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

	public static Matcher<ParameterizedType> isParameterizedType(final Type rawType, final Type... actualTypeArguments) {
		return CoreMatchers.allOf(hasRawType(rawType), hasActualTypeArguments(actualTypeArguments));
	}

	public static Matcher<JsonObject> hasProperty(final String key, final JsonElement value) {
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

	public static Matcher<Date> isDate(final int year, final int month, final int day) {
		return new TypeSafeMatcher<Date>() {
			@Override
			@SuppressWarnings("deprecation")
			protected boolean matchesSafely(final Date date) {
				final TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
				final Calendar calendar = Calendar.getInstance(utcTimeZone);
				calendar.setTime(date);
				return year == calendar.get(Calendar.YEAR)
						&& month == calendar.get(Calendar.MONTH) + 1
						&& day == calendar.get(Calendar.DAY_OF_MONTH);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendValue(year);
				description.appendText("-");
				description.appendValue(month);
				description.appendText("-");
				description.appendValue(day);
			}
		};
	}

	public static Matcher<Date> isTime(final int hours, final int minutes, final int seconds) {
		return new TypeSafeMatcher<Date>() {
			@Override
			@SuppressWarnings("deprecation")
			protected boolean matchesSafely(final Date date) {
				final TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
				final Calendar calendar = Calendar.getInstance(utcTimeZone);
				calendar.setTime(date);
				return hours == calendar.get(Calendar.HOUR_OF_DAY)
						&& minutes == calendar.get(Calendar.MINUTE)
						&& seconds == calendar.get(Calendar.SECOND);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendValue(hours);
				description.appendText(":");
				description.appendValue(minutes);
				description.appendText(":");
				description.appendValue(seconds);
			}
		};
	}

	public static Matcher<Date> isDateTime(final int year, final int month, final int day, final int hours, final int minutes, final int seconds) {
		final Matcher<Date> isDate = isDate(year, month, day);
		final Matcher<Date> isTime = isTime(hours, minutes, seconds);
		return new TypeSafeMatcher<Date>() {
			@Override
			protected boolean matchesSafely(final Date date) {
				return isDate.matches(date) && isTime.matches(date);
			}

			@Override
			public void describeTo(final Description description) {
				isDate.describeTo(description);
				description.appendText(" ");
				isTime.describeTo(description);
			}
		};
	}

}
