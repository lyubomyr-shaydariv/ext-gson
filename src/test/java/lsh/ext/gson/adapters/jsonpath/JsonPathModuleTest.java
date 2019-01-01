package lsh.ext.gson.adapters.jsonpath;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.provider.Arguments;

@Disabled
public final class JsonPathModuleTest
		extends AbstractModuleTest {

	public JsonPathModuleTest() {
		super("Jayway JsonPath");
	}

	@Nonnull
	@Override
	protected IModule createUnit() {
		return JsonPathModule.get();
	}

	@Nonnull
	@Override
	protected Stream<Arguments> supported() {
		return Stream.empty();
	}

}
