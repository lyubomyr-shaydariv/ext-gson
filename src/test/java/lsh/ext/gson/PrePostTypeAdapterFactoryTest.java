package lsh.ext.gson;

import java.util.List;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class PrePostTypeAdapterFactoryTest {

	@Test
	public void testPreConstruct() {
		@SuppressWarnings("unchecked")
		final Consumer<User> userProcessor = Mockito.mock(Consumer.class);
		final IProcessorFactory<User> userProcessorFactory = new IProcessorFactory<>() {
			@Override
			public boolean supports(final TypeToken<?> typeToken) {
				return typeToken.getRawType() == User.class;
			}

			@Override
			public Consumer<User> createProcessor() {
				return userProcessor;
			}
		};
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.getInstance(List.of(userProcessorFactory), List.of()))
				.create();
		gson.toJson(new User("John", "Doe"));
		Mockito.verify(userProcessor).accept(ArgumentMatchers.eq(new User("John", "Doe")));
	}

	@Test
	public void testPostConstruct() {
		@SuppressWarnings("unchecked")
		final Consumer<User> userProcessor = Mockito.mock(Consumer.class);
		final IProcessorFactory<User> userProcessorFactory = new IProcessorFactory<>() {
			@Override
			public boolean supports(final TypeToken<?> typeToken) {
				return typeToken.getRawType() == User.class;
			}

			@Override
			public Consumer<User> createProcessor() {
				return userProcessor;
			}
		};
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.getInstance(List.of(), List.of(userProcessorFactory)))
				.create();
		gson.fromJson("{\"firstName\":\"John\",\"lastName\":\"Doe\"}", User.class);
		Mockito.verify(userProcessor).accept(ArgumentMatchers.eq(new User("John", "Doe")));
	}

	private record User(
			String firstName,
			String lastName
	) {
	}

}
