package lsh.ext.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Gsons {

	@Getter
	private static final Gson normalized = Builders.createNormalized()
			.create();

	@UtilityClass
	public static final class Builders {

		public static GsonBuilder createNormalized() {
			return new GsonBuilder()
					.disableInnerClassSerialization()
					.disableHtmlEscaping();
		}

	}

}
