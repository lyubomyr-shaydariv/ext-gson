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
		final IPrePostProcessor<User> userProcessor = Mockito.mock(IPrePostProcessor.class);
		final IPrePostProcessorFactory<User> userProcessorFactory = new IPrePostProcessorFactory<>() {
			@Override
			public boolean supports(final TypeToken<?> typeToken) {
				return typeToken.getRawType() == User.class;
			}

			@Override
			public IPrePostProcessor<User> createProcessor() {
				return userProcessor;
			}
		};
		final Gson gson = GsonBuilders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.getInstance(List.of(userProcessorFactory)))
				.create();
		gson.toJson(new User("John", "Doe"));
		Mockito.verify(userProcessor).pre(ArgumentMatchers.eq(new User("John", "Doe")));
	}

	@Test
	public void testPostConstruct() {
		@SuppressWarnings("unchecked")
		final IPrePostProcessor<User> userProcessor = Mockito.mock(IPrePostProcessor.class);
		final IPrePostProcessorFactory<User> userProcessorFactory = new IPrePostProcessorFactory<>() {
			@Override
			public boolean supports(final TypeToken<?> typeToken) {
				return typeToken.getRawType() == User.class;
			}

			@Override
			public IPrePostProcessor<User> createProcessor() {
				return userProcessor;
			}
		};
		final Gson gson = GsonBuilders.createNormalized()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.getInstance(List.of(userProcessorFactory)))
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