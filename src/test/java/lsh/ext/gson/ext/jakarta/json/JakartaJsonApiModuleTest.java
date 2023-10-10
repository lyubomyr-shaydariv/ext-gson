package lsh.ext.gson.ext.jakarta.json;

import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import jakarta.json.JsonValue;
import lsh.ext.gson.AbstractModuleTest;
import lsh.ext.gson.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class JakartaJsonApiModuleTest
		extends AbstractModuleTest {

	@Override
	protected IModule createUnit() {
		return JakartaJsonApiModule.getInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(JsonValue.class))
		);
	}

}
