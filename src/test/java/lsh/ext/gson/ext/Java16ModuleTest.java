package lsh.ext.gson.ext;

import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class Java16ModuleTest
		extends AbstractModuleTest {

	public Java16ModuleTest() {
		super("Java 16");
	}

	@Override
	protected IModule createUnit() {
		return Java16Module.getDefaultInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(DummyRecord.class))
		);
	}

	private static record DummyRecord(
	) {
	}

}
