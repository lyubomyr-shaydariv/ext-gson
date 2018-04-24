package lsh.ext.gson.adapters.java8.time;

import java.time.Month;
import java.time.MonthDay;
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
public final class MonthDayTypeAdapterTest
		extends AbstractTypeAdapterTest<MonthDay> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends MonthDay>> parameters() {
		return ImmutableList.of(
				parameterize(MonthDay.of(Month.JULY, 13), "\"--07-13\"")
						.get(),
				parameterize(MonthDay.of(Month.JULY, 13), "\"7.13\"")
						.with((BiFunction<Gson, TypeToken<MonthDay>, TypeAdapter<MonthDay>>) (gson, typeToken) -> MonthDayTypeAdapter.get(DateTimeFormatter.ofPattern("M.d")))
						.get()
		);
	}

	public MonthDayTypeAdapterTest(final TestWith<? extends MonthDay> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends MonthDay> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return MonthDayTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final MonthDay value) {
		return value;
	}

}
