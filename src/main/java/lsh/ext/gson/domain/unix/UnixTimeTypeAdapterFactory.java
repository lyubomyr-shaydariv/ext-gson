package lsh.ext.gson.domain.unix;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
@SuppressWarnings({ "UseOfObsoleteDateTimeApi", "UnnecessaryFullyQualifiedName" })
public final class UnixTimeTypeAdapterFactory {

	@Getter
	private static final ITypeAdapterFactory<java.util.Date> defaultForJavaUtilDate = forJavaUtilDate(UnixTimeTypeAdapter.getDefaultForJavaUtilDate());

	public static ITypeAdapterFactory<java.util.Date> forJavaUtilDate(final TypeAdapter<java.util.Date> typeAdapter) {
		return ITypeAdapterFactory.forClass(java.util.Date.class, typeResolver -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<java.sql.Date> defaultForJavaSqlDate = forJavaSqlDate(UnixTimeTypeAdapter.getDefaultForJavaSqlDate());

	public static ITypeAdapterFactory<java.sql.Date> forJavaSqlDate(final TypeAdapter<java.sql.Date> typeAdapter) {
		return ITypeAdapterFactory.forClass(java.sql.Date.class, typeResolver -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<java.sql.Time> defaultForJavaSqlTime = forJavaSqlTime(UnixTimeTypeAdapter.getDefaultForJavaSqlTime());

	public static ITypeAdapterFactory<java.sql.Time> forJavaSqlTime(final TypeAdapter<java.sql.Time> typeAdapter) {
		return ITypeAdapterFactory.forClass(java.sql.Time.class, typeResolver -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<java.sql.Timestamp> defaultForJavaSqlTimestamp = forJavaSqlTimestamp(UnixTimeTypeAdapter.getDefaultForJavaSqlTimestamp());

	public static ITypeAdapterFactory<java.sql.Timestamp> forJavaSqlTimestamp(final TypeAdapter<java.sql.Timestamp> typeAdapter) {
		return ITypeAdapterFactory.forClass(java.sql.Timestamp.class, typeResolver -> typeAdapter);
	}

}
