package lsh.ext.gson;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public final class JUnitArguments {

	// TODO configure JUnit?
	@SuppressWarnings("DataFlowIssue")
	public static Arguments makeVariadic(final Arguments args) {
		@SuppressWarnings("ReturnOfNull")
		final String[] stringArgs = Stream.of(args.get())
				.map(o -> o != null ? o.toString() : null)
				.toArray(String[]::new);
		return Arguments.of((Object) stringArgs);
	}

}
