package lsh.ext.gson.domain.unix;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.function.LongFunction;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.LiteralPrimitiveLongTypeAdapter;

@UtilityClass
public final class UnixTimeTypeAdapter {

	private static final int MS_PER_S = 1000;

	@SuppressWarnings("UseOfObsoleteDateTimeApi")
	public static TypeAdapter<java.util.Date> defaultForJavaUtilDate = forJavaUtilDate(java.util.Date::new);

	public static TypeAdapter<java.sql.Date> defaultForJavaSqlDate = forJavaUtilDate(java.sql.Date::new);

	@SuppressWarnings("UnnecessaryFullyQualifiedName")
	public static TypeAdapter<java.sql.Time> defaultForJavaSqlTime = forJavaUtilDate(java.sql.Time::new);

	@SuppressWarnings("UnnecessaryFullyQualifiedName")
	public static TypeAdapter<java.sql.Timestamp> defaultForJavaSqlTimestamp = forJavaUtilDate(java.sql.Timestamp::new);

	@SuppressWarnings("UseOfObsoleteDateTimeApi")
	public static <T extends java.util.Date> TypeAdapter<T> forJavaUtilDate(final LongFunction<? extends T> construct) {
		return LiteralPrimitiveLongTypeAdapter.getInstance(date -> date.getTime() / MS_PER_S, timestamp -> construct.apply(timestamp * MS_PER_S));
	}

	public static TypeAdapter<Instant> forJavaTimeInstant = LiteralPrimitiveLongTypeAdapter.getInstance(Instant::getEpochSecond, Instant::ofEpochSecond);

	public static TypeAdapter<OffsetDateTime> forJavaTimeOffsetDateTime = LiteralPrimitiveLongTypeAdapter.getInstance(
			OffsetDateTime::toEpochSecond,
			seconds -> {
				throw new UnsupportedOperationException(String.valueOf(seconds));
			}
	);

}
