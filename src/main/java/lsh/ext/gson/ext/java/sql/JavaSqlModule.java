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

/**
 * Implements a Java SQL module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link UnixTimeDateTypeAdapterFactory} for {@link java.sql.Date}, {@link Time}, and {@link Timestamp}</li>
 * </ul>
 */
public final class JavaSqlModule
		extends AbstractModule {

	/**
	 * Provides a default date object depending on its type.
	 */
	// TODO improve
	public static final IInstanceFactory.IProvider<Date> defaultDateFactoryProvider = JavaSqlModule::provideInstanceFactory;

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final IModule defaultInstance = builder()
			.build();

	private JavaSqlModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
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

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private TypeAdapterFactory unixTimeDateTypeAdapterFactory = UnixTimeDateTypeAdapterFactory.getInstance(defaultDateFactoryProvider);

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new JavaSqlModule(UnmodifiableIterable.copyOf(
					unixTimeDateTypeAdapterFactory
			));
		}

	}

}
