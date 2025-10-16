package lsh.ext.gson.ext.com.google.common.io;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class BaseEncodingTypeAdapterFactory {

	public static final ITypeAdapterFactory<byte[]> base16ForPrimitiveByteArray = forPrimitiveByteArray(BaseEncodingTypeAdapter.base16ForPrimitiveByteArray);
	public static final ITypeAdapterFactory<byte[]> base32ForPrimitiveByteArray = forPrimitiveByteArray(BaseEncodingTypeAdapter.base32ForPrimitiveByteArray);
	public static final ITypeAdapterFactory<byte[]> base32HexForPrimitiveByteArray = forPrimitiveByteArray(BaseEncodingTypeAdapter.base32HexForPrimitiveByteArray);
	public static final ITypeAdapterFactory<byte[]> base64ForPrimitiveByteArray = forPrimitiveByteArray(BaseEncodingTypeAdapter.base64ForPrimitiveByteArray);
	public static final ITypeAdapterFactory<byte[]> base64UrlForPrimitiveByteArray = forPrimitiveByteArray(BaseEncodingTypeAdapter.base64UrlForPrimitiveByteArray);

	public static ITypeAdapterFactory<byte[]> forPrimitiveByteArray(final TypeAdapter<byte[]> typeAdapter) {
		return ITypeAdapterFactory.forClass(byte[].class, typeAdapter);
	}

}
