package lsh.ext.gson.domain.unix;

import java.time.Instant;
import java.time.OffsetDateTime;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class UnixTimeTypeAdapterFactory {

	@SuppressWarnings("UseOfObsoleteDateTimeApi")
	public static final ITypeAdapterFactory<java.util.Date> defaultForJavaUtilDate = forJavaUtilDate(UnixTimeTypeAdapter.defaultForJavaUtilDate);

	@SuppressWarnings("UseOfObsoleteDateTimeApi")
	public static ITypeAdapterFactory<java.util.Date> forJavaUtilDate(final TypeAdapter<java.util.Date> typeAdapter) {
		return ITypeAdapterFactory.forClass(java.util.Date.class, provider -> typeAdapter);
	}
	public static final ITypeAdapterFactory<java.sql.Date> defaultForJavaSqlDate = forJavaSqlDate(UnixTimeTypeAdapter.defaultForJavaSqlDate);

	public static ITypeAdapterFactory<java.sql.Date> forJavaSqlDate(final TypeAdapter<java.sql.Date> typeAdapter) {
		return ITypeAdapterFactory.forClass(java.sql.Date.class, provider -> typeAdapter);
	}

	@SuppressWarnings("UnnecessaryFullyQualifiedName")
	public static final ITypeAdapterFactory<java.sql.Time> defaultForJavaSqlTime = forJavaSqlTime(UnixTimeTypeAdapter.defaultForJavaSqlTime);

	@SuppressWarnings("UnnecessaryFullyQualifiedName")
	public static ITypeAdapterFactory<java.sql.Time> forJavaSqlTime(final TypeAdapter<java.sql.Time> typeAdapter) {
		return ITypeAdapterFactory.forClass(java.sql.Time.class, provider -> typeAdapter);
	}

	@SuppressWarnings("UnnecessaryFullyQualifiedName")
	public static final ITypeAdapterFactory<java.sql.Timestamp> defaultForJavaSqlTimestamp = forJavaSqlTimestamp(UnixTimeTypeAdapter.defaultForJavaSqlTimestamp);

	@SuppressWarnings("UnnecessaryFullyQualifiedName")
	public static ITypeAdapterFactory<java.sql.Timestamp> forJavaSqlTimestamp(final TypeAdapter<java.sql.Timestamp> typeAdapter) {
		return ITypeAdapterFactory.forClass(java.sql.Timestamp.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<Instant> defaultForJavaTimeInstant = forJavaTimeInstant(UnixTimeTypeAdapter.forJavaTimeInstant);

	public static ITypeAdapterFactory<Instant> forJavaTimeInstant(final TypeAdapter<Instant> typeAdapter) {
		return ITypeAdapterFactory.forClass(Instant.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<OffsetDateTime> defaultForOffsetDateTime = forJavaTimeOffsetDateTime(UnixTimeTypeAdapter.forJavaTimeOffsetDateTime);

	public static ITypeAdapterFactory<OffsetDateTime> forJavaTimeOffsetDateTime(final TypeAdapter<OffsetDateTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(OffsetDateTime.class, provider -> typeAdapter);
	}

}
