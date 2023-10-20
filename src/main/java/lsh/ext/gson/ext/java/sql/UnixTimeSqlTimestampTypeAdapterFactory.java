package lsh.ext.gson.ext.java.sql;

import java.io.IOException;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnixTimeSqlTimestampTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Timestamp> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapterFactory instance = new UnixTimeSqlTimestampTypeAdapterFactory();

	@Override
	protected TypeAdapter<Timestamp> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( typeToken.getRawType() != Timestamp.class ) {
			return null;
		}
		return Adapter.getInstance();
	}

	/**
	 * Represents the epoch to {@link Timestamp} and vice versa type adapter.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends TypeAdapter<Timestamp> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Timestamp> instance = new Adapter()
				.nullSafe();

		@Override
		public Timestamp read(final JsonReader in)
				throws IOException {
			return new Timestamp(in.nextLong() * 1000);
		}

		@Override
		public void write(final JsonWriter out, final Timestamp value)
				throws IOException {
			out.value(value.getTime() / 1000);
		}

	}

}
