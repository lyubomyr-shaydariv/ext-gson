package lsh.ext.gson.adapters;

import java.util.Date;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import org.junit.jupiter.params.provider.Arguments;

public final class EpochDateTypeAdapterTest
		extends AbstractTypeAdapterTest<Date, Date> {

	@Nullable
	@Override
	protected Date finalize(@Nullable final Date value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						EpochDateTypeAdapter.getInstance(),
						"1488929283",
						() -> new Date(1488929283000L)
				)
		);
	}

}
