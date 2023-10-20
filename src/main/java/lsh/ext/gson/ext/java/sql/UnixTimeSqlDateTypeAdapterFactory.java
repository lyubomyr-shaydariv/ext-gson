package lsh.ext.gson.ext.java.sql;

import java.io.IOException;
import java.sql.Date;

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
public final class UnixTimeSqlDateTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Date> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapterFactory instance = new UnixTimeSqlDateTypeAdapterFactory();

	@Override
	protected TypeAdapter<Date> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( typeToken.getRawType() != Date.class ) {
			return null;
		}
		return Adapter.getInstance();
	}

	/**
	 * Represents the epoch to {@link Date} and vice versa type adapter.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends TypeAdapter<Date> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Date> instance = new Adapter()
				.nullSafe();

		@Override
		public Date read(final JsonReader in)
				throws IOException {
			return new Date(in.nextLong() * 1000);
		}

		@Override
		public void write(final JsonWriter out, final Date value)
				throws IOException {
			out.value(value.getTime() / 1000);
		}

	}

}
