package lsh.ext.gson.ext.java.time;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class InstantTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<Instant> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<Instant> instance = getInstance(DateTimeFormatter.ISO_INSTANT);

	private InstantTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, Instant::from);
	}

	public static TypeAdapter<Instant> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new InstantTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<Instant> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<Instant> instance = new Factory(InstantTypeAdapter.instance);

		@Getter(AccessLevel.PROTECTED)
		private final TypeAdapter<Instant> typeAdapter;

		private Factory(final TypeAdapter<Instant> typeAdapter) {
			super(Instant.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<Instant> getInstance(final TypeAdapter<Instant> typeAdapter) {
			return new Factory(typeAdapter);
		}

	}

}
