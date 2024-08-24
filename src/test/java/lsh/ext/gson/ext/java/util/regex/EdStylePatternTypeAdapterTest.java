package lsh.ext.gson.ext.java.util.regex;

import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class EdStylePatternTypeAdapterTest
		extends AbstractTypeAdapterTest<Pattern, PatternWrapper> {

	@Nullable
	@Override
	protected PatternWrapper normalize(@Nullable final Pattern pattern) {
		if ( pattern == null ) {
			return null;
		}
		return PatternWrapper.from(pattern);
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						EdStylePatternTypeAdapter.getInstance(),
						"\"/^foo.*bar$/m\"",
						Pattern.compile("^foo.*bar$", Pattern.MULTILINE)
				)
		);
	}

}
