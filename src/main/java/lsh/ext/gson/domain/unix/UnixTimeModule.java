package lsh.ext.gson.domain.unix;

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
	@SuppressWarnings({ "UnnecessaryFullyQualifiedName", "UseOfObsoleteDateTimeApi" })
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<? extends java.util.Date> javaUtilDateTypeAdapterFactory = UnixTimeTypeAdapterFactory.getDefaultForJavaUtilDate();

		@Setter
		private ITypeAdapterFactory<? extends java.sql.Date> javaSqlDateTypeAdapterFactory = UnixTimeTypeAdapterFactory.getDefaultForJavaSqlDate();

		@Setter
		private ITypeAdapterFactory<? extends java.sql.Time> javaSqlTimeTypeAdapterFactory = UnixTimeTypeAdapterFactory.getDefaultForJavaSqlTime();

		@Setter
		private ITypeAdapterFactory<? extends java.sql.Timestamp> javaSqlTimestampTypeAdapterFactory = UnixTimeTypeAdapterFactory.getDefaultForJavaSqlTimestamp();

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new UnixTimeModule(
					javaUtilDateTypeAdapterFactory,
					javaSqlDateTypeAdapterFactory,
					javaSqlTimeTypeAdapterFactory,
					javaSqlTimestampTypeAdapterFactory
			);
		}

	}

}
