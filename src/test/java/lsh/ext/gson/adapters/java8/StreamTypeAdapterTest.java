package lsh.ext.gson.adapters.java8;

import java.util.function.Supplier;
import java.util.stream.Stream;
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
public final class StreamTypeAdapterTest
		extends AbstractTypeAdapterTest<Stream<?>> {

	@Parameterized.Parameters
	@SuppressWarnings("resource")
	public static Iterable<TestWith<? extends Stream<?>>> parameters() {
		return ImmutableList.of(
				parameterize((Supplier<? extends Stream<Integer>>) () -> ImmutableList.of(1, 2, 4, 8).stream(), "[1,2,4,8]")
						.with(TypeToken.get(Integer.class))
						.get()
		);
	}

	public StreamTypeAdapterTest(final TestWith<? extends Stream<?>> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Stream<?>> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		assert typeToken != null;
		return StreamTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Stream<?> value) {
		return value
				.collect(ImmutableList.toImmutableList());
	}

}
