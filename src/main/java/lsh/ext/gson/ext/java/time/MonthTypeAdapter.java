package lsh.ext.gson.ext.java.time;

import java.time.Month;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * <p>A type adapter for {@link Month}.</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class MonthTypeAdapter
		extends AbstractToStringStringTypeAdapter<Month> {

	private static final TypeAdapter<Month> defaultInstance = new MonthTypeAdapter();

	private MonthTypeAdapter() {
	}

	/**
	 * @return An instance of {@link MonthTypeAdapter}.
	 */
	public static TypeAdapter<Month> getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected Month fromString(final String string) {
		return Month.valueOf(string);
	}

}
