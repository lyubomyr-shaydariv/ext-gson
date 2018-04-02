package lsh.ext.gson.adapters;

import java.util.Objects;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public final class PrePostTypeAdapterFactoryTest {

	@Test
	public void testPreConstruct() {
		@SuppressWarnings("unchecked")
		final IPrePostProcessor<User> userProcessor = Mockito.mock(IPrePostProcessor.class);
		final IPrePostProcessorFactory<User> userProcessorFactory = new IPrePostProcessorFactory<User>() {
			@Override
			public boolean supports(final TypeToken<?> typeToken) {
				return typeToken.getRawType() == User.class;
			}

			@Override
			public IPrePostProcessor<User> createProcessor() {
				return userProcessor;
			}
		};
		final Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.get(ImmutableList.of(userProcessorFactory)))
				.create();
		gson.toJson(new User("John", "Doe"));
		Mockito.verify(userProcessor).pre(Matchers.eq(new User("John", "Doe")));
	}

	@Test
	public void testPostConstruct() {
		@SuppressWarnings("unchecked")
		final IPrePostProcessor<User> userProcessor = Mockito.mock(IPrePostProcessor.class);
		final IPrePostProcessorFactory<User> userProcessorFactory = new IPrePostProcessorFactory<User>() {
			@Override
			public boolean supports(final TypeToken<?> typeToken) {
				return typeToken.getRawType() == User.class;
			}

			@Override
			public IPrePostProcessor<User> createProcessor() {
				return userProcessor;
			}
		};
		final Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(PrePostTypeAdapterFactory.get(ImmutableList.of(userProcessorFactory)))
				.create();
		gson.fromJson("{\"firstName\":\"John\",\"lastName\":\"Doe\"}", User.class);
		Mockito.verify(userProcessor).post(Matchers.eq(new User("John", "Doe")));
	}

	private static final class User {

		final String firstName;
		final String lastName;

		private User(final String firstName, final String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		@Override
		public boolean equals(final Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			final User that = (User) o;
			return Objects.equals(firstName, that.firstName) &&
					Objects.equals(lastName, that.lastName);
		}

		@Override
		public int hashCode() {
			return Objects.hash(firstName, lastName);
		}

	}

}
