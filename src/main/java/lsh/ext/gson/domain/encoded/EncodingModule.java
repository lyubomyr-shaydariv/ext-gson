package lsh.ext.gson.domain.encoded;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class EncodingModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<byte[]> primitiveByteArrayTypeAdapterFactory = EncodingTypeAdapterFactory.base64ForPrimitiveByteArray;

	@Override
	public IModule build() {
		return IModule.from(
				primitiveByteArrayTypeAdapterFactory
		);
	}

}
