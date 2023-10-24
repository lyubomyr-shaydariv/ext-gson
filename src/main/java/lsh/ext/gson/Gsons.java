package lsh.ext.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * Contains methods to maintain {@link Gson} instances.
 */
@UtilityClass
public final class Gsons {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final Gson normalized = Builders.builder()
			.create();

	/**
	 * Contains methods to maintain {@link GsonBuilder} instances.
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
		 *
		 * @return Normalized Gson instance builder with disabled defaults.
		 */
		public static GsonBuilder builder() {
			return new GsonBuilder()
					.disableInnerClassSerialization()
					.disableHtmlEscaping();
		}

	}

}
