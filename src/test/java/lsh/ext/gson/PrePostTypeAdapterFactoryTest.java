package lsh.ext.gson;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class PrePostTypeAdapterFactoryTest {

	@Test
	public void testPreConstruct() {
		@SuppressWarnings("unchecked")
		final IPreProcessor<User> userProcessor = Mockito.mock(IPreProcessor.class);
		final IPreProcessorFactory<User> userProcessorFactory = new IPreProcessorFactory<>() {
			@Override
			public boolean supports(final TypeToken<?> typeToken) {
				return typeToken.getRawType() == User.class;
			}

			@Override
			public IPreProcessor<User> createPreProcessor() {
				return userProcessor;
			}
		};
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.getInstance(List.of(userProcessorFactory), List.of()))
				.create();
		gson.toJson(new User("John", "Doe"));
		Mockito.verify(userProcessor).pre(ArgumentMatchers.eq(new User("John", "Doe")));
	}

	@Test
	public void testPostConstruct() {
		@SuppressWarnings("unchecked")
		final IPostProcessor<User> userProcessor = Mockito.mock(IPostProcessor.class);
		final IPostProcessorFactory<User> userProcessorFactory = new IPostProcessorFactory<>() {
			@Override
			public boolean supports(final TypeToken<?> typeToken) {
				return typeToken.getRawType() == User.class;
			}

			@Override
			public IPostProcessor<User> createPostProcessor() {
				return userProcessor;
			}
		};
		final Gson gson = Gsons.Builders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.getInstance(List.of(), List.of(userProcessorFactory)))
				.create();
		gson.fromJson("{\"firstName\":\"John\",\"lastName\":\"Doe\"}", User.class);
		Mockito.verify(userProcessor).post(ArgumentMatchers.eq(new User("John", "Doe")));
	}

	private record User(
			String firstName,
			String lastName
	) {
	}

}
