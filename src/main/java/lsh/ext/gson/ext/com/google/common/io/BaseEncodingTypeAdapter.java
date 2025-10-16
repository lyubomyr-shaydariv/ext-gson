package lsh.ext.gson.ext.com.google.common.io;

import com.google.common.io.BaseEncoding;
import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.LiteralStringTypeAdapter;

@UtilityClass
public final class BaseEncodingTypeAdapter {

	private static final BaseEncoding base16 = BaseEncoding.base16();
	private static final BaseEncoding base32 = BaseEncoding.base32();
	private static final BaseEncoding base32Hex = BaseEncoding.base32Hex();
	private static final BaseEncoding base64 = BaseEncoding.base64();
	private static final BaseEncoding base64url = BaseEncoding.base64Url();

	public static TypeAdapter<byte[]> forPrimitiveByteArray(final BaseEncoding baseEncoding) {
		return LiteralStringTypeAdapter.getInstance(
				baseEncoding::encode,
				baseEncoding::decode
		);
	}

	public static final TypeAdapter<byte[]> base16ForPrimitiveByteArray = forPrimitiveByteArray(base16);
	public static final TypeAdapter<byte[]> base32ForPrimitiveByteArray = forPrimitiveByteArray(base32);
	public static final TypeAdapter<byte[]> base32HexForPrimitiveByteArray = forPrimitiveByteArray(base32Hex);
	public static final TypeAdapter<byte[]> base64ForPrimitiveByteArray = forPrimitiveByteArray(base64);
	public static final TypeAdapter<byte[]> base64UrlForPrimitiveByteArray = forPrimitiveByteArray(base64url);

}
