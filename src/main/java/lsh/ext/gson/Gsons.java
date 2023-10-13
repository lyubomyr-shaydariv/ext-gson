package lsh.ext.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	private static final Gson normalized = Builders.createNormalized()
			.create();

	/**
	 * Contains methods to maintain {@link GsonBuilder} instances.
	 *
	 * @author Lyubomyr Shaydariv
	 */
	@UtilityClass
	public static final class Builders {

		/**
		 * Creates a new normalized {@link GsonBuilder} instance excluding some default options from the default instance:
		 *
		 * <ul>
		 *     <li>{@link GsonBuilder#disableInnerClassSerialization()}</li>
		 *     <li>{@link GsonBuilder#disableHtmlEscaping()}</li>
		 * </ul>
		 */
		public static GsonBuilder createNormalized() {
			return new GsonBuilder()
					.disableInnerClassSerialization()
					.disableHtmlEscaping();
		}

	}

}
