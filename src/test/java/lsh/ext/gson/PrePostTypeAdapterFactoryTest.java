package lsh.ext.gson;

import java.util.List;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class PrePostTypeAdapterFactoryTest {

	@Test
	public void testPreConstruct() {
		@SuppressWarnings("unchecked")
		final PrePostTypeAdapter.IProcessor<User> processorMock = Mockito.mock(PrePostTypeAdapter.IProcessor.class);
		final PrePostTypeAdapter.IProcessor.IFactory<User> userProcessorFactory = type -> type == User.class ? processorMock : null;
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapter.Factory.getInstance(List.of(userProcessorFactory), List.of()))
				.create();
		gson.toJson(user);
		Mockito.verify(processorMock)
				.process(ArgumentMatchers.eq(user));
		Mockito.verifyNoMoreInteractions(processorMock);
	}

	@Test
	public void testPostConstruct() {
		@SuppressWarnings("unchecked")
		final PrePostTypeAdapter.IProcessor<User> processorMock = Mockito.mock(PrePostTypeAdapter.IProcessor.class);
		final PrePostTypeAdapter.IProcessor.IFactory<User> userProcessorFactory = type -> type == User.class ? processorMock : null;
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapter.Factory.getInstance(List.of(), List.of(userProcessorFactory)))
				.create();
		gson.fromJson("{\"firstName\":\"John\",\"lastName\":\"Doe\"}", User.class);
		Mockito.verify(processorMock)
				.process(ArgumentMatchers.eq(user));
		Mockito.verifyNoMoreInteractions(processorMock);
	}

	private static final User user = new User("John", "Doe");

	private record User(
			String firstName,
			String lastName
	) {
	}

}
