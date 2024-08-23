package lsh.ext.gson.ext.java.util;

import java.util.Date;

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

}
