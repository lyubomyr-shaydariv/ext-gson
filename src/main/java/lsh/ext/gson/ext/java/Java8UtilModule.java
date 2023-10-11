package lsh.ext.gson.ext.java;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ext.java.util.OptionalTypeAdapterFactory;
import lsh.ext.gson.ext.java.util.stream.StreamTypeAdapterFactory;

/**
 * Implements a Java 8 module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link OptionalTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
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
	@Accessors(fluent = true, chain = true, prefix = "with")
	public static final class Builder {

		private static final Iterable<? extends TypeAdapterFactory> defaultTypeAdapterFactories = Stream.of(
						OptionalTypeAdapterFactory.getInstance()
				)
				.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new Java8UtilModule(defaultTypeAdapterFactories);
		}

	}

}
