package lsh.ext.gson;

import org.junit.Test;

import static lsh.ext.gson.Numbers.tryParseLongOrDouble;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public final class NumbersTest {

	@Test
	public void testTryParseLongOrDouble() {
		assertThat(tryParseLongOrDouble("2"), is(2L));
		assertThat(tryParseLongOrDouble("3.14"), is(3.14));
		assertThat(tryParseLongOrDouble("foo"), nullValue());
	}

}
