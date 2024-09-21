package lsh.ext.gson.test;

import lombok.experimental.UtilityClass;
import org.mockito.stubbing.Answer;

// see also https://stackoverflow.com/questions/54328867/spying-a-lambda-with-mockito/54328868#54328868
@UtilityClass
public final class MoreMockito {

	public static Answer<?> assertionError() {
		return invocationOnMock -> {
			throw new AssertionError(invocationOnMock);
		};
	}

}
