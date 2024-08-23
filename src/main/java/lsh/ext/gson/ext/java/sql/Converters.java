package lsh.ext.gson.ext.java.sql;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import lombok.experimental.UtilityClass;
import lsh.ext.gson.domain.UnixTimeTypeAdapter;

@UtilityClass
final class Converters {

	static final UnixTimeTypeAdapter.IConverter<Date> dateConverter = new UnixTimeTypeAdapter.IConverter<>() {
		@Override
		public long toSeconds(final Date value) {
			return value.getTime() / 1000;
		}

		@Override
		public Date fromSeconds(final long s) {
			return new Date(s * 1000);
		}
	};

	static final UnixTimeTypeAdapter.IConverter<Time> timeConverter = new UnixTimeTypeAdapter.IConverter<>() {
		@Override
		public long toSeconds(final Time value) {
			return value.getTime() / 1000;
		}

		@Override
		public Time fromSeconds(final long s) {
			return new Time(s * 1000);
		}
	};

	static final UnixTimeTypeAdapter.IConverter<Timestamp> timestampConverter = new UnixTimeTypeAdapter.IConverter<>() {
		@Override
		public long toSeconds(final Timestamp value) {
			return value.getTime() / 1000;
		}

		@Override
		public Timestamp fromSeconds(final long s) {
			return new Timestamp(s * 1000);
		}
	};

}
