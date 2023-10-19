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
		final IProcessor<User> mockUserProcessor = Mockito.mock(IProcessor.class);
		final IProcessorFactory<User> userProcessorFactory = typeToken -> typeToken.getRawType() == User.class ? mockUserProcessor : null;
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.getInstance(List.of(userProcessorFactory), List.of()))
				.create();
		gson.toJson(user);
		Mockito.verify(mockUserProcessor).process(ArgumentMatchers.eq(user));
		Mockito.verifyNoMoreInteractions(mockUserProcessor);
	}

	@Test
	public void testPostConstruct() {
		@SuppressWarnings("unchecked")
		final IProcessor<User> mockUserProcessor = Mockito.mock(IProcessor.class);
		final IProcessorFactory<User> userProcessorFactory = typeToken -> typeToken.getRawType() == User.class ? mockUserProcessor : null;
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.getInstance(List.of(), List.of(userProcessorFactory)))
				.create();
		gson.fromJson("{\"firstName\":\"John\",\"lastName\":\"Doe\"}", User.class);
		Mockito.verify(mockUserProcessor).process(ArgumentMatchers.eq(user));
		Mockito.verifyNoMoreInteractions(mockUserProcessor);
	}

	private static final User user = new User("John", "Doe");

	private record User(
			String firstName,
			String lastName
	) {
	}

}
