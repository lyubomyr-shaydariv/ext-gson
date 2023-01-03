package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * A type adapter for {@link DayOfWeek}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DayOfWeekTypeAdapter
		extends AbstractToStringStringTypeAdapter<DayOfWeek> {

	private static final TypeAdapter<DayOfWeek> instance = new DayOfWeekTypeAdapter();

	/**
	 * @return An instance of {@link DayOfWeekTypeAdapter}.
	 */
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<DayOfWeek> getInstance() {
		return instance;
	}

	@Override
	protected DayOfWeek fromString(final String text) {
		return DayOfWeek.valueOf(text);
	}

}
