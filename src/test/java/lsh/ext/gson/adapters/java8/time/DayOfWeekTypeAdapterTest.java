package lsh.ext.gson.adapters.java8.time;

import java.time.DayOfWeek;
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
public final class DayOfWeekTypeAdapterTest
		extends AbstractTypeAdapterTest<DayOfWeek> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends DayOfWeek>> parameters() {
		return ImmutableList.of(
				parameterize(DayOfWeek.MONDAY, "\"MONDAY\"")
						.get(),
				parameterize(DayOfWeek.FRIDAY, "\"FRIDAY\"")
						.get()
		);
	}

	public DayOfWeekTypeAdapterTest(final TestWith<? extends DayOfWeek> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends DayOfWeek> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return DayOfWeekTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final DayOfWeek value) {
		return value;
	}

}
