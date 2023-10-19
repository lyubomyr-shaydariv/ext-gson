package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractStringTypeAdapter;

/**
 * Implements a type adapter factory for {@link DayOfWeek}.
 */
public final class DayOfWeekTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<DayOfWeek> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapterFactory instance = new DayOfWeekTypeAdapterFactory(Adapter.getInstance());

	private DayOfWeekTypeAdapterFactory(final TypeAdapter<DayOfWeek> typeAdapter) {
		super(DayOfWeek.class, typeAdapter);
	}

	/**
	 * A type adapter for {@link DayOfWeek}.
	 */
	public static final class Adapter
			extends AbstractStringTypeAdapter<DayOfWeek> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<DayOfWeek> instance = new Adapter()
				.nullSafe();

		@Override
		protected DayOfWeek fromString(final String text) {
			return DayOfWeek.valueOf(text);
		}

		@Override
		protected String toString(final DayOfWeek value) {
			return value.toString();
		}

	}

}
