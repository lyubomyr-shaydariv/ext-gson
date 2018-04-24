package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDateTime;
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
public final class LocalDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalDateTime> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends LocalDateTime>> parameters() {
		return ImmutableList.of(
				parameterize(LocalDateTime.of(2018, 01, 01, 15, 31), "\"2018-01-01T15:31\"")
						.get(),
				parameterize(LocalDateTime.of(2018, 01, 01, 15, 31), "\"2018-01-01T15:31:00\"")
						.with((BiFunction<Gson, TypeToken<LocalDateTime>, TypeAdapter<LocalDateTime>>) (gson, typeToken) -> LocalDateTimeTypeAdapter.get(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
						.get()
		);
	}

	public LocalDateTimeTypeAdapterTest(final TestWith<? extends LocalDateTime> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends LocalDateTime> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return LocalDateTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final LocalDateTime value) {
		return value;
	}

}
