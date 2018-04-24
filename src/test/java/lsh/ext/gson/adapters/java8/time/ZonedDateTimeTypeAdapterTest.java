package lsh.ext.gson.adapters.java8.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
public final class ZonedDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<ZonedDateTime> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends ZonedDateTime>> parameters() {
		return ImmutableList.of(
				parameterize(ZonedDateTime.of(2018, 03, 20, 12, 34, 56, 666, ZoneId.of("UTC")), "\"2018-03-20T12:34:56.000000666Z[UTC]\"")
						.get(),
				parameterize(ZonedDateTime.of(2018, 03, 20, 12, 34, 56, 666, ZoneId.of("UTC")), "\"2018-03-20T12:34:56.000000666Z[UTC]\"")
						.with((BiFunction<Gson, TypeToken<ZonedDateTime>, TypeAdapter<ZonedDateTime>>) (gson, typeToken) -> ZonedDateTimeTypeAdapter.get(DateTimeFormatter.ISO_ZONED_DATE_TIME))
						.get()
		);
	}

	public ZonedDateTimeTypeAdapterTest(final TestWith<? extends ZonedDateTime> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends ZonedDateTime> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return ZonedDateTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final ZonedDateTime value) {
		return value;
	}

}
