package lsh.ext.gson.ext.java.sql;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JavaSqlModuleTest
		extends AbstractModuleTest {

	private static final TypeToken<Date> dateTypeToken = TypeToken.get(Date.class);
	private static final TypeToken<Timestamp> timestampTypeToken = TypeToken.get(Timestamp.class);
	private static final TypeToken<Time> timeTypeToken = TypeToken.get(Time.class);

	public JavaSqlModuleTest() {
		super(JavaSqlModule.getInstance());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(dateTypeToken),
				Arguments.of(timestampTypeToken),
				Arguments.of(timeTypeToken)
		);
	}

}
