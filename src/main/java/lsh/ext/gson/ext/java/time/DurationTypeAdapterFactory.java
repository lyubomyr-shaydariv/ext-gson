package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractStringTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class DurationTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<Duration>
		implements ITypeAdapterFactory<Duration> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<Duration> instance = new DurationTypeAdapterFactory(Adapter.getInstance());

	private DurationTypeAdapterFactory(final TypeAdapter<Duration> typeAdapter) {
		super(Duration.class, typeAdapter);
	}

	public static final class Adapter
			extends AbstractStringTypeAdapter<Duration> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Duration> instance = new Adapter()
				.nullSafe();

		@Override
		protected Duration fromString(final String text) {
			return Duration.parse(text);
		}

		@Override
		protected String toString(final Duration value) {
			return value.toString();
		}

	}

}
