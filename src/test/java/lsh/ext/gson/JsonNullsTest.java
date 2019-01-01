package lsh.ext.gson;

import com.google.gson.JsonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonNullsTest {

	@Test
	public void testJsonNull() {
		Assertions.assertSame(JsonNull.INSTANCE, JsonNulls.of());
	}

}
