package lsh.ext.gson.ext.java.util;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class Java8UtilModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<Optional<Object>> optionalTypeAdapterFactory = OptionalTypeAdapterFactory.defaultForOptional;

	@Setter
	private ITypeAdapterFactory<OptionalDouble> optionalDoubleTypeAdapterFactory = OptionalTypeAdapterFactory.getDefaultForOptionalDouble();

	@Setter
	private ITypeAdapterFactory<OptionalInt> optionalIntTypeAdapterFactory = OptionalTypeAdapterFactory.getDefaultForOptionalInt();

	@Setter
	private ITypeAdapterFactory<OptionalLong> optionalLongTypeAdapterFactory = OptionalTypeAdapterFactory.getDefaultForOptionalLong();

	@Override
	public IModule build() {
		return IModule.from(
				optionalTypeAdapterFactory,
				optionalDoubleTypeAdapterFactory,
				optionalIntTypeAdapterFactory,
				optionalLongTypeAdapterFactory
		);
	}

}
