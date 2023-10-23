package lsh.ext.gson.ext.java.sql;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.UnmodifiableIterable;

/**
 * Implements a Java SQL module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link UnixTimeSqlDateTypeAdapterFactory}</li>
 * <li>{@link UnixTimeSqlTimeTypeAdapterFactory}</li>
 * <li>{@link UnixTimeSqlTimestampTypeAdapterFactory}</li>
 * </ul>
 */
public final class JavaSqlModule
		extends AbstractModule {

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

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<Date> unixTimeSqlDateTypeAdapterFactory = UnixTimeSqlDateTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<Timestamp> unixTimeSqlTimestampTypeAdapterFactory = UnixTimeSqlTimestampTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<Time> unixTimeSqlTimeTypeAdapterFactory = UnixTimeSqlTimeTypeAdapterFactory.getInstance();

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new JavaSqlModule(UnmodifiableIterable.copyOf(
					unixTimeSqlDateTypeAdapterFactory,
					unixTimeSqlTimestampTypeAdapterFactory,
					unixTimeSqlTimeTypeAdapterFactory
			));
		}

	}

}
