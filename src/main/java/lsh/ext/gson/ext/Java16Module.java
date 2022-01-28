package lsh.ext.gson.ext;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;
import lsh.ext.gson.ext.java.lang.RecordTypeAdapterFactory;

/**
 * <p>
 * Implements a Java 16 module registering the following type adapter factories:
 * </p>
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
			.done();

	private Java16Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Java 16", typeAdapterFactories);
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

		/**
		 * @return A new module instance.
		 */
		public IModule done() {
			final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = Stream.of(
							RecordTypeAdapterFactory.getInstance()
					)
					.toList();
			return new Java16Module(typeAdapterFactories);
		}

	}

}
