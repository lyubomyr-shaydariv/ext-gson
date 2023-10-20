package lsh.ext.gson.ext.java.sql;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import lsh.ext.gson.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class JavaSqlModuleTest
		extends AbstractModuleTest {

	@Override
	protected IModule createUnit() {
		return JavaSqlModule.getDefaultInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.get(Date.class)),
				Arguments.of(TypeToken.get(Timestamp.class)),
				Arguments.of(TypeToken.get(Time.class))
		);
	}

}
