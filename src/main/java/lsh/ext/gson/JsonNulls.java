package lsh.ext.gson;

import com.google.gson.JsonNull;
import lombok.experimental.UtilityClass;

/**
 * Provides miscellaneous {@link JsonNull} utility methods.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class JsonNulls {

	/**
	 * @return {@link JsonNull#INSTANCE}.
	 */
	public static JsonNull of() {
		return JsonNull.INSTANCE;
	}

}
