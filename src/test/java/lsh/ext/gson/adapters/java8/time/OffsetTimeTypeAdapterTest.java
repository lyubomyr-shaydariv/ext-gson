package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class OffsetTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<OffsetTime> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends OffsetTime>> parameters() {
		return ImmutableList.of(
				parameterize(OffsetTime.of(12, 34, 56, 0, ZoneOffset.UTC), "\"12:34:56Z\"")
						.get(),
				parameterize(OffsetTime.of(12, 34, 56, 0, ZoneOffset.UTC), "\"12:34:56Z\"")
						.with((BiFunction<Gson, TypeToken<OffsetTime>, TypeAdapter<OffsetTime>>) (gson, typeToken) -> OffsetTimeTypeAdapter.get(DateTimeFormatter.ISO_OFFSET_TIME))
						.get()
		);
	}

	public OffsetTimeTypeAdapterTest(final TestWith<? extends OffsetTime> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends OffsetTime> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return OffsetTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final OffsetTime value) {
		return value;
	}

}
