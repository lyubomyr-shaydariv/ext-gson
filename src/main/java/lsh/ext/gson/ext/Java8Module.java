package lsh.ext.gson.ext;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;
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
public final class Java8Module
		extends AbstractModule {

	@Getter
	private static final IModule instance = build()
			.build();

	private Java8Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
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

		private static final Iterable<? extends TypeAdapterFactory> defaultTypeAdapterFactories = Stream.of(
						OptionalTypeAdapterFactory.getInstance(),
						StreamTypeAdapterFactory.getInstance()
				)
				.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new Java8Module(defaultTypeAdapterFactories);
		}

	}

}
