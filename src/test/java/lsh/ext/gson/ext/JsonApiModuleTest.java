package lsh.ext.gson.ext;

import java.util.stream.Stream;
import javax.json.JsonValue;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonApiModuleTest
		extends AbstractModuleTest {

	@Override
	protected IModule createUnit() {
		return JsonApiModule.getInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(JsonValue.class))
		);
	}

}
