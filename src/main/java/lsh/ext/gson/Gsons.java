package lsh.ext.gson;

import com.google.gson.Gson;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * Contains methods to maintain {@link Gson} instances.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class Gsons {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final Gson normalized = GsonBuilders.createNormalized()
			.create();

}
