package lsh.ext.gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class PrePostTypeAdapterFactoryTest {

	@Test
	public void testPreConstruct() {
		@SuppressWarnings("unchecked")
		final Consumer<User> processorMock = Mockito.mock(Consumer.class);
		final Iterable<Function<Type, Consumer<User>>> preProcessorFactories = List.of(
				type -> type == User.class ? processorMock : null
		);
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapter.Factory.getInstance(preProcessorFactories, null))
				.create();
		gson.toJson(user);
		Mockito.verify(processorMock)
				.accept(ArgumentMatchers.eq(user));
		Mockito.verifyNoMoreInteractions(processorMock);
	}

	@Test
	public void testPostConstruct() {
		@SuppressWarnings("unchecked")
		final Consumer<User> processorMock = Mockito.mock(Consumer.class);
		final List<Function<Type, Consumer<User>>> postProcessorFactories = List.of(
				type -> type == User.class ? processorMock : null
		);
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapter.Factory.getInstance(null, postProcessorFactories))
				.create();
		gson.fromJson("{\"firstName\":\"John\",\"lastName\":\"Doe\"}", User.class);
		Mockito.verify(processorMock)
				.accept(ArgumentMatchers.eq(user));
		Mockito.verifyNoMoreInteractions(processorMock);
	}

	private static final User user = new User("John", "Doe");

	private record User(
			String firstName,
			String lastName
	) {
	}

}
