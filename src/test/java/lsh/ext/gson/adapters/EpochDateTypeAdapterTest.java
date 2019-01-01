package lsh.ext.gson.adapters;

import java.util.Date;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import org.junit.jupiter.params.provider.Arguments;

public final class EpochDateTypeAdapterTest
		extends AbstractTypeAdapterTest<Date> {

	public EpochDateTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Date value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(EpochDateTypeAdapter.get(), "1488929283", (Supplier<?>) () -> new Date(1488929283000L))
		);
	}

}
