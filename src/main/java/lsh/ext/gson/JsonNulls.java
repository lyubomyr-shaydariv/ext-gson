package lsh.ext.gson;

import com.google.gson.JsonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonNulls {

	public static JsonNull of() {
		return JsonNull.INSTANCE;
	}

}
