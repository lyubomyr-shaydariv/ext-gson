package lsh.ext.gson.ext.json;

import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.json.JsonValue;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonApiModuleTest
		extends AbstractModuleTest {

	public JsonApiModuleTest() {
		super("Java JSON API");
	}

	@Nonnull
	@Override
	protected IModule createUnit() {
		return JsonApiModule.getDefaultInstance();
	}

	@Nonnull
	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(JsonValue.class))
		);
	}

}
