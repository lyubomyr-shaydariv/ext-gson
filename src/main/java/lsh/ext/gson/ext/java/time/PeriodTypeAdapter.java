package lsh.ext.gson.ext.java.time;

import java.time.Period;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractStringTypeAdapter;

/**
 * A type adapter for {@link Period}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PeriodTypeAdapter
		extends AbstractStringTypeAdapter<Period> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<Period> instance = new PeriodTypeAdapter();

	@Override
	protected String toString(final Period period) {
		return period.toString();
	}

	@Override
	protected Period fromString(final String text) {
		return Period.parse(text);
	}

}
