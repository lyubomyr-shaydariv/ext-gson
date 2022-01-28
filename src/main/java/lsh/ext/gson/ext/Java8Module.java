package lsh.ext.gson.ext;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;
import lsh.ext.gson.ext.java.util.OptionalTypeAdapterFactory;
import lsh.ext.gson.ext.java.util.stream.StreamTypeAdapterFactory;

/**
 * <p>
 * Implements a Java 8 module registering the following type adapter factories:
 * </p>
 *
 * <ul>
 * <li>{@link OptionalTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
 */
public final class Java8Module
		extends AbstractModule {

	private static final IModule instance = build()
			.done();

	private Java8Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Java 8", typeAdapterFactories);
	}

	/**
	 * @return The default instance of the module with the default type adapter factories settings.
	 */
	public static IModule getInstance() {
		return instance;
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder build() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Builder {

		/**
		 * @return A new module instance.
		 */
		public IModule done() {
			final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = Stream.of(
					OptionalTypeAdapterFactory.getInstance(),
					StreamTypeAdapterFactory.getInstance()
			)
					.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
			return new Java8Module(typeAdapterFactories);
		}

	}

}
