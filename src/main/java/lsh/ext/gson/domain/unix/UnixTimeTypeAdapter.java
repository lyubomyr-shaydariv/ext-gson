package lsh.ext.gson.domain.unix;

import java.util.function.LongFunction;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.JsonPrimitiveLongTypeAdapter;

@UtilityClass
@SuppressWarnings({ "UseOfObsoleteDateTimeApi", "UnnecessaryFullyQualifiedName" })
public final class UnixTimeTypeAdapter {

	@Getter
	private static TypeAdapter<java.util.Date> defaultForJavaUtilDate = JsonPrimitiveLongTypeAdapter.getInstance(
			UnixTimeTypeAdapter::format,
			timestamp -> parse(timestamp, java.util.Date::new)
	);

	@Getter
	private static TypeAdapter<java.sql.Date> defaultForJavaSqlDate = JsonPrimitiveLongTypeAdapter.getInstance(
			UnixTimeTypeAdapter::format,
			timestamp -> parse(timestamp, java.sql.Date::new)
	);

	@Getter
	private static TypeAdapter<java.sql.Time> defaultForJavaSqlTime = JsonPrimitiveLongTypeAdapter.getInstance(
			UnixTimeTypeAdapter::format,
			timestamp -> parse(timestamp, java.sql.Time::new)
	);

	@Getter
	private static TypeAdapter<java.sql.Timestamp> defaultForJavaSqlTimestamp = JsonPrimitiveLongTypeAdapter.getInstance(
			UnixTimeTypeAdapter::format,
			timestamp -> parse(timestamp, java.sql.Timestamp::new)
	);

	private static <T extends java.util.Date> long format(final T date) {
		return date.getTime() / 1000;
	}

	private static <T extends java.util.Date> T parse(final long timestamp, final LongFunction<? extends T> construct) {
		return construct.apply(timestamp * 1000);
	}

}
