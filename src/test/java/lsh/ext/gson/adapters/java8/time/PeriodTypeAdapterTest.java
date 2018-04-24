package lsh.ext.gson.adapters.java8.time;

import java.time.Period;
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
public class PeriodTypeAdapterTest
		extends AbstractTypeAdapterTest<Period> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends Period>> parameters() {
		return ImmutableList.of(
				parameterize(Period.of(1, 8, 20), "\"P1Y8M20D\"")
						.get()
		);
	}

	public PeriodTypeAdapterTest(final TestWith<? extends Period> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Period> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return PeriodTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Period value) {
		return value;
	}

}
