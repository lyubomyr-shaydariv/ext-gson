package lsh.ext.gson;

import com.google.gson.JsonNull;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public final class JsonNullsTest {

	@Test
	public void testJsonNull() {
		MatcherAssert.assertThat(JsonNulls.of(), CoreMatchers.is(JsonNull.INSTANCE));
	}

}
