package lsh.ext.gson.ext.javax.json;

import java.util.stream.Stream;
import javax.json.JsonValue;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonApiModuleTest
		extends AbstractModuleTest {

	private static final TypeToken<JsonValue> jsonValueTypeToken = TypeToken.get(JsonValue.class);

	public JsonApiModuleTest() {
		super(JavaJsonApiModule.create().build());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(jsonValueTypeToken)
		);
	}

}
