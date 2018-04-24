package lsh.ext.gson.adapters.java8.time;

import java.time.YearMonth;
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
public final class YearMonthTypeAdapterTest
		extends AbstractTypeAdapterTest<YearMonth> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends YearMonth>> parameters() {
		return ImmutableList.of(
				parameterize(YearMonth.of(1999, 9), "\"1999-09\"")
						.get(),
				parameterize(YearMonth.of(1999, 9), "\"9.1999\"")
						.with((BiFunction<Gson, TypeToken<YearMonth>, TypeAdapter<YearMonth>>) (gson, typeToken) -> YearMonthTypeAdapter.get(DateTimeFormatter.ofPattern("M.y")))
						.get()
		);
	}

	public YearMonthTypeAdapterTest(final TestWith<? extends YearMonth> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends YearMonth> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return YearMonthTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final YearMonth value) {
		return value;
	}

}
