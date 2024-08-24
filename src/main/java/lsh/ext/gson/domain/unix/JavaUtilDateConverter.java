package lsh.ext.gson.domain.unix;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.function.LongFunction;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("UseOfObsoleteDateTimeApi")
public final class JavaUtilDateConverter<T extends java.util.Date>
		implements UnixTimeTypeAdapter.IConverter<T> {

	public static final UnixTimeTypeAdapter.IConverter<java.util.Date> javaUtilDateConverter = new JavaUtilDateConverter<>(java.util.Date::new);
	public static final UnixTimeTypeAdapter.IConverter<java.sql.Date> javaSqlDateConverter = new JavaUtilDateConverter<>(java.sql.Date::new);
	public static final UnixTimeTypeAdapter.IConverter<Time> javaSqlTimeConverter = new JavaUtilDateConverter<>(Time::new);
	public static final UnixTimeTypeAdapter.IConverter<Timestamp> javaSqlTimestampConverter = new JavaUtilDateConverter<>(Timestamp::new);

	private final LongFunction<? extends T> fromMilliseconds;

	public static <T extends java.util.Date> UnixTimeTypeAdapter.IConverter<T> getInstance(final LongFunction<? extends T> fromMilliseconds) {
		return new JavaUtilDateConverter<>(fromMilliseconds);
	}

	@Override
	public long toSeconds(final T date) {
		return date.getTime() / 1000;
	}

	@Override
	public T fromSeconds(final long s) {
		return fromMilliseconds.apply(s * 1000);
	}

}
