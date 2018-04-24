package lsh.ext.gson.adapters.java8.time;

import java.time.LocalTime;
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
public final class LocalTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalTime> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends LocalTime>> parameters() {
		return ImmutableList.of(
				parameterize(LocalTime.of(10, 10, 0), "\"10:10\"")
						.get(),
				parameterize(LocalTime.of(19, 23, 33), "\"19:23:33\"")
						.with((BiFunction<Gson, TypeToken<LocalTime>, TypeAdapter<LocalTime>>) (gson, typeToken) -> LocalTimeTypeAdapter.get(DateTimeFormatter.ISO_TIME))
						.get()
		);
	}

	public LocalTimeTypeAdapterTest(final TestWith<? extends LocalTime> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends LocalTime> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return LocalTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final LocalTime value) {
		return value;
	}

}
