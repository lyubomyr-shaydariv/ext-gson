package lsh.ext.gson;

import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

/**
 * Contains methods to maintain {@link GsonBuilder} instances.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class GsonBuilders {

	/**
	 * Creates a new "canonical" {@link GsonBuilder} instance excluding some default options from the default instance:
	 *
	 * <ul>
	 *     <li>{@link GsonBuilder#disableInnerClassSerialization()}</li>
	 *     <li>{@link GsonBuilder#disableHtmlEscaping()}</li>
	 * </ul>
	 */
	public static GsonBuilder createCanonical() {
		return new GsonBuilder()
				.disableInnerClassSerialization()
				.disableHtmlEscaping();
	}

}
