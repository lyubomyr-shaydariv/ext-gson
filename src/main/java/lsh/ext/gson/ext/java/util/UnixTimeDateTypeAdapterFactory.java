package lsh.ext.gson.ext.java.util;

import java.util.Date;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ITypeAdapterFactory;

public final class UnixTimeDateTypeAdapterFactory
		extends AbstractUnixTimeDateTypeAdapterFactory<Date>
		implements ITypeAdapterFactory<Date> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<Date> instance = new UnixTimeDateTypeAdapterFactory(Adapter.getInstance());

	private UnixTimeDateTypeAdapterFactory(final TypeAdapter<Date> dateTypeAdapter) {
		super(Date.class, dateTypeAdapter);
	}

	/**
	 * Represents the epoch to {@link Date} and vice versa type adapter.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends AbstractAdapter<Date> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Date> instance = new Adapter()
				.nullSafe();

		@Override
		protected Date createInstance(final long date) {
			return new Date(date);
		}

	}

}
