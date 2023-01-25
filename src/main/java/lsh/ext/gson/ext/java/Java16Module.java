package lsh.ext.gson.ext.java;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;
import lsh.ext.gson.ext.java.lang.RecordTypeAdapterFactory;

/**
 * Implements a Java 16 module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link RecordTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
 * @version 0-SNAPSHOT
 */
public final class Java16Module
		extends AbstractModule {

	private static final IModule defaultInstance = build()
			.build();

	private Java16Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return The default instance of the module with the default type adapter factories settings.
	 */
	public static IModule getDefaultInstance() {
		return defaultInstance;
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
						RecordTypeAdapterFactory.getInstance()
				)
				.toList();

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new Java16Module(defaultTypeAdapterFactories);
		}

	}

}
