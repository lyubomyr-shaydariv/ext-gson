package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetDateTime;
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
public final class OffsetDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<OffsetDateTime> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends OffsetDateTime>> parameters() {
		return ImmutableList.of(
				parameterize(OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC), "\"1996-10-18T12:34:45Z\"")
						.get(),
				parameterize(OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC), "\"1996-10-18T12:34:45Z\"")
						.with((BiFunction<Gson, TypeToken<OffsetDateTime>, TypeAdapter<OffsetDateTime>>) (gson, typeToken) -> OffsetDateTimeTypeAdapter.get(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
						.get()
		);
	}

	public OffsetDateTimeTypeAdapterTest(final TestWith<? extends OffsetDateTime> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends OffsetDateTime> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return OffsetDateTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final OffsetDateTime value) {
		return value;
	}

}
