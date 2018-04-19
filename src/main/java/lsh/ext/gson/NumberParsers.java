package lsh.ext.gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A pack of number parsers.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class NumberParsers {

	private NumberParsers() {
	}

	/**
	 * @return A parser to parse {@link Integer}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static INumberParser<Integer> forInteger() {
		return IntegerParser.instance;
	}

	/**
	 * @return A parser to parse {@link Long}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static INumberParser<Long> forLong() {
		return LongParser.instance;
	}

	/**
	 * @return A parser to parse {@link Double}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static INumberParser<Double> forDouble() {
		return DoubleParser.instance;
	}

	/**
	 * @return A parser to parse {@link BigInteger}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static INumberParser<BigInteger> forBigInteger() {
		return BigIntegerParser.instance;
	}

	/**
	 * @return A parser to parse {@link BigDecimal}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static INumberParser<BigDecimal> forBigDecimal() {
		return BigDecimalParser.instance;
	}

	/**
	 * @return A parser to parse {@link Long} or {@link Double}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static INumberParser<? extends Number> forLongOrDouble() {
		return FirstBestNumberParser.longOrDouble;
	}

	/**
	 * @return A parser to parse {@link Integer}, {@link Long} or {@link Double}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static INumberParser<? extends Number> forIntegerOrLongOrDouble() {
		return FirstBestNumberParser.integerOrLongOrDouble;
	}

	/**
	 * @return A parser to parse {@link Integer}, {@link Long}, {@link Double}, {@link BigInteger} or {@link BigDecimal}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static INumberParser<? extends Number> forIntegerOrLongOrBigDecimal() {
		return FirstBestNumberParser.integerOrLongOrBigDecimal;
	}

	private static final class IntegerParser
			implements INumberParser<Integer> {

		private static final INumberParser<Integer> instance = new IntegerParser();

		@Nullable
		@Override
		public Integer parseNumber(@Nonnull final String value) {
			try {
				return Integer.parseInt(value);
			} catch ( final NumberFormatException ignored ) {
				return null;
			}
		}

	}

	private static final class LongParser
			implements INumberParser<Long> {

		private static final INumberParser<Long> instance = new LongParser();

		@Nullable
		@Override
		public Long parseNumber(@Nonnull final String value) {
			try {
				return Long.parseLong(value);
			} catch ( final NumberFormatException ignored ) {
				return null;
			}
		}

	}

	private static final class DoubleParser
			implements INumberParser<Double> {

		private static final INumberParser<Double> instance = new DoubleParser();

		@Nullable
		@Override
		public Double parseNumber(@Nonnull final String value) {
			try {
				return Double.parseDouble(value);
			} catch ( final NumberFormatException ignored ) {
				return null;
			}
		}

	}

	private static final class BigIntegerParser
			implements INumberParser<BigInteger> {

		private static final INumberParser<BigInteger> instance = new BigIntegerParser();

		@Nullable
		@Override
		public BigInteger parseNumber(@Nonnull final String value) {
			try {
				return new BigInteger(value);
			} catch ( final NumberFormatException ignored ) {
				return null;
			}
		}

	}

	private static final class BigDecimalParser
			implements INumberParser<BigDecimal> {

		private static final INumberParser<BigDecimal> instance = new BigDecimalParser();

		@Nullable
		@Override
		public BigDecimal parseNumber(@Nonnull final String value) {
			try {
				return new BigDecimal(value);
			} catch ( final NumberFormatException ignored ) {
				return null;
			}
		}

	}

	private static final class FirstBestNumberParser
			implements INumberParser<Number> {

		@SuppressWarnings("unchecked")
		private static final INumberParser<? extends Number> longOrDouble = new FirstBestNumberParser(
				LongParser.instance,
				DoubleParser.instance
		);

		@SuppressWarnings("unchecked")
		private static final INumberParser<Number> integerOrLongOrDouble = new FirstBestNumberParser(
				IntegerParser.instance,
				LongParser.instance,
				DoubleParser.instance
		);

		@SuppressWarnings("unchecked")
		private static final INumberParser<Number> integerOrLongOrBigDecimal = new FirstBestNumberParser(
				IntegerParser.instance,
				LongParser.instance,
				BigDecimalParser.instance
		);

		private final INumberParser<? extends Number>[] numberParsers;

		private FirstBestNumberParser(final INumberParser<? extends Number>... numberParsers) {
			this.numberParsers = numberParsers;
		}

		@Nullable
		@Override
		public Number parseNumber(@Nonnull final String value) {
			for ( final INumberParser<? extends Number> numberParser : numberParsers ) {
				@Nullable
				final Number number = numberParser.parseNumber(value);
				if ( number != null ) {
					return number;
				}
			}
			return null;
		}

	}

}
