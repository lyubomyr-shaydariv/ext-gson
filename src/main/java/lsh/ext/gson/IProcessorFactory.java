package lsh.ext.gson;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.gson.reflect.TypeToken;

/**
 * A factory to return a processor.
 *
 * @param <T>
 * 		Type the processor is implemented for.
 */
public interface IProcessorFactory<T> {

	/**
	 * @param typeToken
	 * 		Type token.
	 *
	 * @return {@code true} if the factory can create a processor for the type represented by the given type token, otherwise {@code false}.
	 */
	boolean supports(TypeToken<?> typeToken);

	/**
	 * @return A processor for the given type.
	 */
	Consumer<T> createProcessor();

	static <T> IProcessorFactory<T> create(final Predicate<? super TypeToken<?>> supports, final Supplier<? extends Consumer<T>> createProcessor) {
		return new IProcessorFactory<>() {
			@Override
			public boolean supports(final TypeToken<?> typeToken) {
				return supports.test(typeToken);
			}

			@Override
			public Consumer<T> createProcessor() {
				return createProcessor.get();
			}
		};
	}

}
