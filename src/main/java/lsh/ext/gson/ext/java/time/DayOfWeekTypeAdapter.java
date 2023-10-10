package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractStringTypeAdapter;

/**
 * A type adapter for {@link DayOfWeek}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DayOfWeekTypeAdapter
		extends AbstractStringTypeAdapter<DayOfWeek> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<DayOfWeek> instance = new DayOfWeekTypeAdapter()
			.nullSafe();

	@Override
	protected String toString(final DayOfWeek dayOfWeek) {
		return dayOfWeek.toString();
	}

	@Override
	protected DayOfWeek fromString(final String text) {
		return DayOfWeek.valueOf(text);
	}

}
