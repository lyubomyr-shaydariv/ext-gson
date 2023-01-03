package lsh.ext.gson.ext.java.time;

import java.time.Period;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * A type adapter for {@link Period}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PeriodTypeAdapter
		extends AbstractToStringStringTypeAdapter<Period> {

	private static final TypeAdapter<Period> instance = new PeriodTypeAdapter();

	/**
	 * @return An instance of {@link PeriodTypeAdapter}.
	 */
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<Period> getInstance() {
		return instance;
	}

	@Override
	protected Period fromString(final String string) {
		return Period.parse(string);
	}

}
