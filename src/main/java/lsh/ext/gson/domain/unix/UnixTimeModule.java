package lsh.ext.gson.domain.unix;

import java.time.Instant;
import java.time.OffsetDateTime;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

public final class UnixTimeModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private UnixTimeModule(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
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

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new UnixTimeModule(
					javaUtilDateTypeAdapterFactory,
					javaSqlDateTypeAdapterFactory,
					javaSqlTimeTypeAdapterFactory,
					javaSqlTimestampTypeAdapterFactory,
					javaTimeInstant,
					javaTimeOffsetDateTime
			);
		}

	}

}
