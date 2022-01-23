package lsh.ext.gson;

import javax.annotation.Nonnull;

import com.google.gson.JsonNull;

/**
 * Provides miscellaneous {@link JsonNull} utility methods.
 *
 * @author Lyubomyr Shaydariv
 */
public final class JsonNulls {

	private JsonNulls() {
	}

	/**
	 * @return {@link JsonNull#INSTANCE}.
	 */
	@Nonnull
	public static JsonNull of() {
		return JsonNull.INSTANCE;
	}

}
