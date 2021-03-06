package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * <p>A type adapter for {@link DayOfWeek}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class DayOfWeekTypeAdapter
		extends AbstractToStringStringTypeAdapter<DayOfWeek> {

	private static final TypeAdapter<DayOfWeek> defaultInstance = new DayOfWeekTypeAdapter();

	private DayOfWeekTypeAdapter() {
	}

	/**
	 * @return An instance of {@link DayOfWeekTypeAdapter}.
	 */
	public static TypeAdapter<DayOfWeek> getDefaultInstance() {
		return defaultInstance;
	}

	@Nonnull
	@Override
	protected DayOfWeek fromString(@Nonnull final String string) {
		return DayOfWeek.valueOf(string);
	}

}
