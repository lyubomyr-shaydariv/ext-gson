package lsh.ext.gson.adapters;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class JsonFailSafeTypeAdapterTest
		extends AbstractTypeAdapterTest<Object> {

	@Parameterized.Parameters
	public static Iterable<TestWith<?>> parameters() {
		return ImmutableList.of(
				parameterize(ImmutableList.of("foo", "bar"), "[\"foo\",\"bar\"]")
						.with(new TypeToken<List<String>>() {})
						.get(),
				parameterize((List<Integer>) null, "1000")
						.with(new TypeToken<List<Integer>>() {})
						.withoutWrite()
						.get()
		);
	}

	public JsonFailSafeTypeAdapterTest(final TestWith<?> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<?> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return JsonFailSafeTypeAdapterFactory.get().create(gson, typeToken);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Object value) {
		return value;
	}

}
