package lsh.ext.gson.ext.java.time;

import java.time.Month;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * <p>A type adapter for {@link Month}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
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

	@Nonnull
	@Override
	protected Month fromString(@Nonnull final String string) {
		return Month.valueOf(string);
	}

}
