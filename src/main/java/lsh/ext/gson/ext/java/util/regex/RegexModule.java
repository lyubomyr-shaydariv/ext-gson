package lsh.ext.gson.ext.java.util.regex;

import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class RegexModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<Pattern> patternTypeAdapterFactory = RegexTypeAdapterFactory.simpleForPattern;

	@Override
	public IModule build() {
		return IModule.from(
				patternTypeAdapterFactory
		);
	}

}
