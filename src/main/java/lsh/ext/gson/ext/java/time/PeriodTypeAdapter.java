package lsh.ext.gson.ext.java.time;

import java.time.Period;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * <p>A type adapter for {@link Period}.</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class PeriodTypeAdapter
		extends AbstractToStringStringTypeAdapter<Period> {

	private static final TypeAdapter<Period> defaultInstance = new PeriodTypeAdapter();

	private PeriodTypeAdapter() {
	}

	/**
	 * @return An instance of {@link PeriodTypeAdapter}.
	 */
	public static TypeAdapter<Period> getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected Period fromString(final String string) {
		return Period.parse(string);
	}

}
