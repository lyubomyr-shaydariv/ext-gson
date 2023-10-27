package lsh.ext.gson.ext.jakarta.json;

import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import jakarta.json.JsonValue;
import lsh.ext.gson.AbstractModuleTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JakartaJsonApiModuleTest
		extends AbstractModuleTest {

	private static final TypeToken<JsonValue> jsonValueTypeToken = TypeToken.get(JsonValue.class);

	public JakartaJsonApiModuleTest() {
		super(JakartaJsonApiModule.getInstance());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(jsonValueTypeToken)
		);
	}

}
