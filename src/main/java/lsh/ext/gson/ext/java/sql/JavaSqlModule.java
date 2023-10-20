package lsh.ext.gson.ext.java.sql;

import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
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
	public static final class Builder {

		private static final Iterable<? extends TypeAdapterFactory> defaultTypeAdapterFactories = UnmodifiableIterable.copyOf(
				UnixTimeSqlDateTypeAdapterFactory.getInstance(),
				UnixTimeSqlTimestampTypeAdapterFactory.getInstance(),
				UnixTimeSqlTimeTypeAdapterFactory.getInstance()
		);

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new JavaSqlModule(defaultTypeAdapterFactories);
		}

	}

}
