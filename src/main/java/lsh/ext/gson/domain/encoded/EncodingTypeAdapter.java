package lsh.ext.gson.domain.encoded;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IFunction1;
import lsh.ext.gson.JsonStringTypeAdapter;

@UtilityClass
public final class EncodingTypeAdapter {

	public static TypeAdapter<byte[]> forPrimitiveByteArray(
			final IFunction1<? super byte[], String> encode,
			final IFunction1<? super String, byte[]> decode
	) {
		return JsonStringTypeAdapter.getInstance(encode, decode);
	}

	public static TypeAdapter<char[]> forPrimitiveCharArray(
			final IFunction1<? super char[], String> encode,
			final IFunction1<? super String, char[]> decode
	) {
		return JsonStringTypeAdapter.getInstance(encode, decode);
	}

}
