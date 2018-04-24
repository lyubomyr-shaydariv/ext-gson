package lsh.ext.gson.adapters.java8.time;

import java.time.Instant;
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
public final class InstantTypeAdapterTest
		extends AbstractTypeAdapterTest<Instant> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends Instant>> parameters() {
		return ImmutableList.of(
				parameterize(Instant.ofEpochMilli(0), "\"1970-01-01T00:00:00Z\"")
						.get(),
				parameterize(Instant.ofEpochMilli(2891237832837L), "\"2061-08-14T09:37:12.837Z\"")
						.get()
		);
	}

	public InstantTypeAdapterTest(final TestWith<? extends Instant> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Instant> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return InstantTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Instant value) {
		return value;
	}

}
