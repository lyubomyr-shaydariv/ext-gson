package lsh.ext.gson.test;

import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

@UtilityClass
public final class MoreAssertions {

	public static <EX extends Throwable> void assertThrows(final Class<EX> expectedClass, final String expectedMessage, final Executable executable) {
		final EX ex = Assertions.assertThrows(expectedClass, executable);
		Assertions.assertEquals(expectedMessage, ex.getMessage());
	}

}
