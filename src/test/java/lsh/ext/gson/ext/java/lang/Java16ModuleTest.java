package lsh.ext.gson.ext.java.lang;

import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import org.junit.jupiter.params.provider.Arguments;

public final class Java16ModuleTest
		extends AbstractModuleTest {

	public Java16ModuleTest() {
		super(Java16Module.getDefaultInstance());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(DummyRecord.class))
		);
	}

	private record DummyRecord(
	) {
	}

}
