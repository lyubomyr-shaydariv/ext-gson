package lsh.ext.gson;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class NumberParsersTest {

	@Test
	public void testForInteger() {
		final INumberParser<Integer> unit = NumberParsers.forInteger();
		MatcherAssert.assertThat(unit.parseNumber("300"), CoreMatchers.is(300));
		MatcherAssert.assertThat(unit.parseNumber(""), CoreMatchers.nullValue());
	}

	@Test
	public void testForLong() {
		final INumberParser<Long> unit = NumberParsers.forLong();
		MatcherAssert.assertThat(unit.parseNumber("300"), CoreMatchers.is(300L));
		MatcherAssert.assertThat(unit.parseNumber(""), CoreMatchers.nullValue());
	}

	@Test
	public void testForDouble() {
		final INumberParser<Double> unit = NumberParsers.forDouble();
		MatcherAssert.assertThat(unit.parseNumber("300"), CoreMatchers.is(300D));
		MatcherAssert.assertThat(unit.parseNumber(""), CoreMatchers.nullValue());
	}

	@Test
	public void testForBigInteger() {
		final INumberParser<BigInteger> unit = NumberParsers.forBigInteger();
		MatcherAssert.assertThat(unit.parseNumber("300"), CoreMatchers.is(new BigInteger("300")));
		MatcherAssert.assertThat(unit.parseNumber(""), CoreMatchers.nullValue());
	}

	@Test
	public void testForBigDecimal() {
		final INumberParser<BigDecimal> unit = NumberParsers.forBigDecimal();
		MatcherAssert.assertThat(unit.parseNumber("300"), CoreMatchers.is(new BigDecimal("300")));
		MatcherAssert.assertThat(unit.parseNumber(""), CoreMatchers.nullValue());
	}

	@Test
	public void testForLongOrDouble() {
		final INumberParser<? extends Number> unit = NumberParsers.forLongOrDouble();
		MatcherAssert.assertThat(unit.parseNumber("300"), CoreMatchers.is(300L));
		MatcherAssert.assertThat(unit.parseNumber("300.0"), CoreMatchers.is(300D));
		MatcherAssert.assertThat(unit.parseNumber(""), CoreMatchers.nullValue());
	}

	@Test
	public void testForIntegerOrLongOrDouble() {
		final INumberParser<? extends Number> unit = NumberParsers.forIntegerOrLongOrDouble();
		MatcherAssert.assertThat(unit.parseNumber("300"), CoreMatchers.is(300));
		MatcherAssert.assertThat(unit.parseNumber("300000000000"), CoreMatchers.is(300000000000L));
		MatcherAssert.assertThat(unit.parseNumber("300.0"), CoreMatchers.is(300D));
		MatcherAssert.assertThat(unit.parseNumber(""), CoreMatchers.nullValue());
	}

	@Test
	public void testForIntegerOrLongOrBigDecimal() {
		final INumberParser<? extends Number> unit = NumberParsers.forIntegerOrLongOrBigDecimal();
		MatcherAssert.assertThat(unit.parseNumber("300"), CoreMatchers.is(300));
		MatcherAssert.assertThat(unit.parseNumber("300000000000"), CoreMatchers.is(300000000000L));
		MatcherAssert.assertThat(unit.parseNumber("3.141592653589793238462643383279"), CoreMatchers.is(new BigDecimal("3.141592653589793238462643383279")));
		MatcherAssert.assertThat(unit.parseNumber(""), CoreMatchers.nullValue());
	}

}
