package lsh.ext.gson.ext.java.sql;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.UnmodifiableIterable;
import lsh.ext.gson.ext.java.util.UnixTimeDateTypeAdapter;

public final class JavaSqlModule
		extends AbstractModule {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final IModule instance = Builder.create()
			.build();

	private JavaSqlModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	public static IBuilder0<java.util.Date> createSqlDateTypesFactory(final TypeToken<java.util.Date> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? extends java.util.Date> rawType = (Class<? extends java.util.Date>) typeToken.getRawType();
		if ( rawType == Date.class ) {
			return () -> new Date(0);
		}
		if ( rawType == Time.class ) {
			return () -> new Time(0);
		}
		if ( rawType == Timestamp.class ) {
			return () -> new Timestamp(0);
		}
		return UnixTimeDateTypeAdapter.Factory.createFactory(typeToken);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private TypeAdapterFactory unixTimeDateTypeAdapterFactory = UnixTimeDateTypeAdapter.Factory.getInstance(JavaSqlModule::createSqlDateTypesFactory);

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new JavaSqlModule(UnmodifiableIterable.copyOf(
					unixTimeDateTypeAdapterFactory
			));
		}

	}

}
