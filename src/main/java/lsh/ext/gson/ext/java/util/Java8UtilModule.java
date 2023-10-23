package lsh.ext.gson.ext.java.util;

import java.util.Optional;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.UnmodifiableIterable;

/**
 * Implements a Java 8 module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link OptionalTypeAdapterFactory}</li>
 * </ul>
 */
public final class Java8UtilModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = builder()
			.build();

	private Java8UtilModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<? extends Optional<?>> optionalTypeAdapterFactory = OptionalTypeAdapterFactory.getInstance();

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new Java8UtilModule(UnmodifiableIterable.copyOf(
					optionalTypeAdapterFactory
			));
		}

	}

}
