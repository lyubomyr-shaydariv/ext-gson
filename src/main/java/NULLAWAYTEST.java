import javax.annotation.Nonnull;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class NULLAWAYTEST {

	public static void main(final String[] args) {
		acceptNonNull(null);
	}

	private static void acceptNonNull(@Nonnull final String s) {
		System.out.println(s);
	}

}
