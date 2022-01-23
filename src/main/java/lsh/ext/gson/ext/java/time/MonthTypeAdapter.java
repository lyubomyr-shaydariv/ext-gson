package lsh.ext.gson.ext.java.time;

import java.time.Month;

import com.google.gson.TypeAdapter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * <p>A type adapter for {@link Month}.</p>
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MonthTypeAdapter
		extends AbstractToStringStringTypeAdapter<Month> {

	private static final TypeAdapter<Month> instance = new MonthTypeAdapter();

	/**
	 * @return An instance of {@link MonthTypeAdapter}.
	 */
	public static TypeAdapter<Month> getInstance() {
		return instance;
	}

	@Override
	protected Month fromString(final String string) {
		return Month.valueOf(string);
	}

}
