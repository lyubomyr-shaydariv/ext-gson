package lsh.ext.gson.ext.java.time;

import java.time.Month;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractStringTypeAdapter;

/**
 * A type adapter for {@link Month}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MonthTypeAdapter
		extends AbstractStringTypeAdapter<Month> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<Month> instance = new MonthTypeAdapter()
			.nullSafe();

	@Override
	protected String toString(final Month month) {
		return month.toString();
	}

	@Override
	protected Month fromString(final String text) {
		return Month.valueOf(text);
	}

}
