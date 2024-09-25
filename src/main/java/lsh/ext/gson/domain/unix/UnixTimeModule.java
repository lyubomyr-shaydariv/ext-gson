package lsh.ext.gson.domain.unix;

import java.time.Instant;
import java.time.OffsetDateTime;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class UnixTimeModule
		implements IBuilder0<IModule> {

	@Setter
	@SuppressWarnings("UseOfObsoleteDateTimeApi")
	private ITypeAdapterFactory<? extends java.util.Date> javaUtilDateTypeAdapterFactory = UnixTimeTypeAdapterFactory.defaultForJavaUtilDate;

	@Setter
	private ITypeAdapterFactory<? extends java.sql.Date> javaSqlDateTypeAdapterFactory = UnixTimeTypeAdapterFactory.defaultForJavaSqlDate;

	@Setter
	@SuppressWarnings("UnnecessaryFullyQualifiedName")
	private ITypeAdapterFactory<? extends java.sql.Time> javaSqlTimeTypeAdapterFactory = UnixTimeTypeAdapterFactory.defaultForJavaSqlTime;

	@Setter
	@SuppressWarnings("UnnecessaryFullyQualifiedName")
	private ITypeAdapterFactory<? extends java.sql.Timestamp> javaSqlTimestampTypeAdapterFactory = UnixTimeTypeAdapterFactory.defaultForJavaSqlTimestamp;

	@Setter
	private ITypeAdapterFactory<Instant> javaTimeInstant = UnixTimeTypeAdapterFactory.defaultForJavaTimeInstant;

	@Setter
	private ITypeAdapterFactory<OffsetDateTime> javaTimeOffsetDateTime = UnixTimeTypeAdapterFactory.defaultForOffsetDateTime;

	@Override
	public IModule build() {
		return IModule.from(
				javaUtilDateTypeAdapterFactory,
				javaSqlDateTypeAdapterFactory,
				javaSqlTimeTypeAdapterFactory,
				javaSqlTimestampTypeAdapterFactory,
				javaTimeInstant,
				javaTimeOffsetDateTime
		);
	}

}
