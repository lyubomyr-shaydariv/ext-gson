package lsh.ext.gson;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class NumbersTest {

	@Test
	public void testTryParseLongOrDouble() {
		MatcherAssert.assertThat(Numbers.tryParseLongOrDouble("2"), CoreMatchers.is(2L));
		MatcherAssert.assertThat(Numbers.tryParseLongOrDouble("3.14"), CoreMatchers.is(3.14));
		MatcherAssert.assertThat(Numbers.tryParseLongOrDouble("foo"), CoreMatchers.nullValue());
	}

}
