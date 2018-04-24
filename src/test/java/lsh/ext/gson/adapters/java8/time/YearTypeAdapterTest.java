package lsh.ext.gson.adapters.java8.time;

import java.time.Month;
import java.time.Year;
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
public final class YearTypeAdapterTest
		extends AbstractTypeAdapterTest<Year> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends Year>> parameters() {
		return ImmutableList.of(
				parameterize(Year.of(2018), "\"2018\"")
						.get(),
				parameterize(Year.of(2018), "\"2018-xx-xx\"")
						.with((BiFunction<Gson, TypeToken<Year>, TypeAdapter<Year>>) (gson, typeToken) -> YearTypeAdapter.get(DateTimeFormatter.ofPattern("y'-xx-xx'")))
						.get()
		);
	}

	public YearTypeAdapterTest(final TestWith<? extends Year> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Year> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return YearTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Year value) {
		return value;
	}

}
