package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractCharSequenceTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DurationTypeAdapter
		extends AbstractCharSequenceTypeAdapter<Duration> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<Duration> instance = new DurationTypeAdapter()
			.nullSafe();

	@Override
	protected Duration fromCharSequence(final CharSequence cs) {
		return Duration.parse(cs);
	}

	@Override
	protected String toCharSequence(final Duration value) {
		return value.toString();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<Duration> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<Duration> instance = new Factory(DurationTypeAdapter.getInstance());

		private Factory(final TypeAdapter<Duration> typeAdapter) {
			super(Duration.class, typeAdapter);
		}

	}

}
