package lsh.ext.gson.ext.java.sql;

import java.sql.Time;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ext.java.util.AbstractUnixTimeDateTypeAdapterFactory;

public final class UnixTimeSqlTimeTypeAdapterFactory
		extends AbstractUnixTimeDateTypeAdapterFactory<Time> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapterFactory instance = new UnixTimeSqlTimeTypeAdapterFactory(Adapter.getInstance());

	private UnixTimeSqlTimeTypeAdapterFactory(final TypeAdapter<Time> dateTypeAdapter) {
		super(Time.class, dateTypeAdapter);
	}

	/**
	 * Represents the epoch to {@link Time} and vice versa type adapter.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends AbstractAdapter<Time> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Time> instance = new Adapter()
				.nullSafe();

		@Override
		protected Time createInstance(final long date) {
			return new Time(date);
		}

	}

}
