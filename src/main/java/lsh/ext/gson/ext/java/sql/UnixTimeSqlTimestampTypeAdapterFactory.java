package lsh.ext.gson.ext.java.sql;

import java.sql.Timestamp;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ext.java.util.AbstractUnixTimeDateTypeAdapterFactory;

public final class UnixTimeSqlTimestampTypeAdapterFactory
		extends AbstractUnixTimeDateTypeAdapterFactory<Timestamp>
		implements ITypeAdapterFactory<Timestamp> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<Timestamp> instance = new UnixTimeSqlTimestampTypeAdapterFactory(Adapter.getInstance());

	private UnixTimeSqlTimestampTypeAdapterFactory(final TypeAdapter<Timestamp> dateTypeAdapter) {
		super(Timestamp.class, dateTypeAdapter);
	}

	/**
	 * Represents the epoch to {@link Timestamp} and vice versa type adapter.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends AbstractAdapter<Timestamp> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Timestamp> instance = new Adapter()
				.nullSafe();

		@Override
		protected Timestamp createInstance(final long date) {
			return new Timestamp(date);
		}

	}

}
