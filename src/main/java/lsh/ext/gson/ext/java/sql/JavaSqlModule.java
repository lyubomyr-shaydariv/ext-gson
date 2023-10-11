package lsh.ext.gson.ext.java.sql;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IInstanceFactory;
import lsh.ext.gson.IModule;
import lsh.ext.gson.UnmodifiableIterable;
import lsh.ext.gson.ext.java.util.UnixTimeDateTypeAdapterFactory;

public final class JavaSqlModule
		extends AbstractModule {

	// TODO improve
	public static final IInstanceFactory.IProvider<Date> defaultDateFactoryProvider = JavaSqlModule::provideInstanceFactory;

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final IModule defaultInstance = builder()
			.build();

	private JavaSqlModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	public static Builder builder() {
		return new Builder();
	}

	private static IInstanceFactory<Date> provideInstanceFactory(final TypeToken<Date> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? extends Date> rawType = (Class<? extends Date>) typeToken.getRawType();
		if ( rawType == java.sql.Date.class ) {
			return () -> new java.sql.Date(0);
		}
		if ( rawType == Time.class ) {
			return () -> new Time(0);
		}
		if ( rawType == Timestamp.class ) {
			return () -> new Timestamp(0);
		}
		return UnixTimeDateTypeAdapterFactory.defaultDateFactoryProvider.provide(typeToken);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private TypeAdapterFactory unixTimeDateTypeAdapterFactory = UnixTimeDateTypeAdapterFactory.getInstance(defaultDateFactoryProvider);

		public IModule build() {
			return new JavaSqlModule(UnmodifiableIterable.copyOf(
					unixTimeDateTypeAdapterFactory
			));
		}

	}

}
