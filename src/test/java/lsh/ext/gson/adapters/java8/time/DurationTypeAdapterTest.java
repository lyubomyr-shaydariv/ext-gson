package lsh.ext.gson.adapters.java8.time;

import java.time.Duration;
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
public final class DurationTypeAdapterTest
		extends AbstractTypeAdapterTest<Duration> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends Duration>> parameters() {
		return ImmutableList.of(
				parameterize(Duration.ofMillis(555), "\"PT0.555S\"")
						.get(),
				parameterize(Duration.ofSeconds(555), "\"PT9M15S\"")
						.get(),
				parameterize(Duration.ofMinutes(555), "\"PT9H15M\"")
						.get(),
				parameterize(Duration.ofHours(555), "\"PT555H\"")
						.get(),
				parameterize(Duration.ofDays(555), "\"PT13320H\"")
						.get()
		);
	}

	public DurationTypeAdapterTest(final TestWith<? extends Duration> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Duration> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return DurationTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Duration value) {
		return value;
	}

}
