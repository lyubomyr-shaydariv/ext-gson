package lsh.ext.gson;



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
	public static JsonNull of() {
		return JsonNull.INSTANCE;
	}

}
