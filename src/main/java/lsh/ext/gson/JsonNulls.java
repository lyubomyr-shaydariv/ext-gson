package lsh.ext.gson;

import javax.annotation.Nonnull;

import com.google.gson.JsonNull;

/**
 * Provides miscellaneous {@link JsonNull} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class JsonNulls {

	private JsonNulls() {
	}

	/**
	 * @return {@link JsonNull#INSTANCE}.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonNull of() {
		return JsonNull.INSTANCE;
	}

}
