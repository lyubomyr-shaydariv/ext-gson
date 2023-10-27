package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractStringTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DurationTypeAdapter
		extends AbstractStringTypeAdapter<Duration> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<Duration> instance = new DurationTypeAdapter()
			.nullSafe();

	@Override
	protected Duration fromString(final String text) {
		return Duration.parse(text);
	}

	@Override
	protected String toString(final Duration value) {
		return value.toString();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<Duration>
			implements ITypeAdapterFactory<Duration> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final ITypeAdapterFactory<Duration> instance = new Factory(DurationTypeAdapter.getInstance());

		private Factory(final TypeAdapter<Duration> typeAdapter) {
			super(Duration.class, typeAdapter);
		}

	}

}
