package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDate;
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
public final class LocalDateTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalDate> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends LocalDate>> parameters() {
		return ImmutableList.of(
				parameterize(LocalDate.of(2018, 01, 01), "\"2018-01-01\"")
						.get(),
				parameterize(LocalDate.of(2018, 01, 01), "\"2018-01-01\"")
						.with((BiFunction<Gson, TypeToken<LocalDate>, TypeAdapter<LocalDate>>) (gson, typeToken) -> LocalDateTypeAdapter.get(DateTimeFormatter.ISO_LOCAL_DATE))
						.get()
		);
	}

	public LocalDateTypeAdapterTest(final TestWith<? extends LocalDate> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends LocalDate> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return LocalDateTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final LocalDate value) {
		return value;
	}

}
