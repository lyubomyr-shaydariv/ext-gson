package lsh.ext.gson.ext.java.time;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.Nullable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link Instant}.
 */
public final class InstantTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<Instant>
		implements ITypeAdapterFactory<Instant> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<Instant> instance = new InstantTypeAdapterFactory(Adapter.getInstance());

	private InstantTypeAdapterFactory(final TypeAdapter<Instant> typeAdapter) {
		super(Instant.class, typeAdapter);
	}

	@SuppressWarnings("checkstyle:MissingJavadocMethod")
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new InstantTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	/**
	 * A type adapter for {@link Instant}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<Instant> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Instant> instance = getInstance(DateTimeFormatter.ISO_INSTANT);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, Instant::from);
		}

		@SuppressWarnings("checkstyle:MissingJavadocMethod")
		public static TypeAdapter<Instant> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
