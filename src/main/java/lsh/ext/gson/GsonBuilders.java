package lsh.ext.gson;

import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

/**
 * TODO
 */
@UtilityClass
public final class GsonBuilders {

	/**
	 * TODO
	 */
	public static GsonBuilder createCanonical() {
		return new GsonBuilder()
				.disableInnerClassSerialization()
				.disableHtmlEscaping();
	}

}
