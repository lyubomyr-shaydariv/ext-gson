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
		final PrePostTypeAdapter.IPreProcessor<User> processorMock = Mockito.mock(PrePostTypeAdapter.IPreProcessor.class);
		final PrePostTypeAdapter.IPreProcessor.IFactory<User> userProcessorFactory = type -> type == User.class ? processorMock : null;
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapter.Factory.getInstance(List.of(userProcessorFactory), null))
				.create();
		gson.toJson(user);
		Mockito.verify(processorMock)
				.preProcess(ArgumentMatchers.eq(user));
		Mockito.verifyNoMoreInteractions(processorMock);
	}

	@Test
	public void testPostConstruct() {
		@SuppressWarnings("unchecked")
		final PrePostTypeAdapter.IPostProcessor<User> processorMock = Mockito.mock(PrePostTypeAdapter.IPostProcessor.class);
		final PrePostTypeAdapter.IPostProcessor.IFactory<User> userProcessorFactory = type -> type == User.class ? processorMock : null;
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapter.Factory.getInstance(null, List.of(userProcessorFactory)))
				.create();
		gson.fromJson("{\"firstName\":\"John\",\"lastName\":\"Doe\"}", User.class);
		Mockito.verify(processorMock)
				.postProcess(ArgumentMatchers.eq(user));
		Mockito.verifyNoMoreInteractions(processorMock);
	}

	private static final User user = new User("John", "Doe");

	private record User(
			String firstName,
			String lastName
	) {
	}

}
