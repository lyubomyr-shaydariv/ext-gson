package lsh.ext.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Gsons {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final Gson normalized = Builders.builder()
			.create();

	@UtilityClass
	public static final class Builders {

		public static GsonBuilder builder() {
			return new GsonBuilder()
					.disableInnerClassSerialization()
					.disableHtmlEscaping();
		}

	}

}
