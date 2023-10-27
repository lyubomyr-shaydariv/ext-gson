package lsh.ext.gson.ext.java.lang;

import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import org.junit.jupiter.params.provider.Arguments;

public final class Java16ModuleTest
		extends AbstractModuleTest {

	private static final TypeToken<DummyRecord> dummyRecordTypeToken = TypeToken.get(DummyRecord.class);

	public Java16ModuleTest() {
		super(Java16Module.getDefaultInstance());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(dummyRecordTypeToken)
		);
	}

	private record DummyRecord(
	) {
	}

}
