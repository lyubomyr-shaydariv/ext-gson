package lsh.ext.gson.domain.encoded;

import java.util.Base64;
import java.util.function.Function;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.JsonStringTypeAdapter;

@UtilityClass
public final class EncodingTypeAdapter {

	public static final TypeAdapter<byte[]> base64ForPrimitiveByteArray = forPrimitiveByteArray(
			Base64.getEncoder()::encodeToString,
			Base64.getDecoder()::decode
	);

	public static final TypeAdapter<byte[]> urlForPrimitiveByteArray = forPrimitiveByteArray(
			Base64.getUrlEncoder()::encodeToString,
			Base64.getUrlDecoder()::decode
	);

	public static TypeAdapter<byte[]> forPrimitiveByteArray(
			final Function<? super byte[], String> encode,
			final Function<? super String, byte[]> decode
	) {
		return JsonStringTypeAdapter.getInstance(encode, decode);
	}

	public static TypeAdapter<char[]> forPrimitiveCharArray(
			final Function<? super char[], String> encode,
			final Function<? super String, char[]> decode
	) {
		return JsonStringTypeAdapter.getInstance(encode, decode);
	}

}
