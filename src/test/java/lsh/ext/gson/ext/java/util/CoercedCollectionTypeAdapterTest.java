package lsh.ext.gson.ext.java.util;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.test.TestTypeAdapters;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class CoercedCollectionTypeAdapterTest
		extends AbstractTypeAdapterTest<List<?>, List<?>> {

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final List<?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(TestTypeAdapters.stringToIntegerMapTypeAdapter, UtilTypeAdapterFactory.defaultBuilderFactoryForCoercedCollection(Types.collectionTypeToken)),
						"{\"foo\":1,\"bar\":2}",
						List.of(ImmutableMap.of("foo", 1, "bar", 2))
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(TestTypeAdapters.stringTypeAdapter, UtilTypeAdapterFactory.defaultBuilderFactoryForCoercedCollection(Types.collectionTypeToken)),
						"\"foo\"",
						List.of("foo")
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(TestTypeAdapters.integerTypeAdapter, UtilTypeAdapterFactory.defaultBuilderFactoryForCoercedCollection(Types.collectionTypeToken)),
						"39",
						List.of(39)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(TestTypeAdapters.booleanTypeAdapter, UtilTypeAdapterFactory.defaultBuilderFactoryForCoercedCollection(Types.collectionTypeToken)),
						"true",
						List.of(true)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(TestTypeAdapters.stringToIntegerMapTypeAdapter, UtilTypeAdapterFactory.defaultBuilderFactoryForCoercedCollection(Types.collectionTypeToken)),
						"[{\"foo\":1,\"bar\":2},{\"bar\":3,\"qux\":4}]",
						List.of(ImmutableMap.of("foo", 1, "bar", 2), ImmutableMap.of("bar", 3, "qux", 4))
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(TestTypeAdapters.stringTypeAdapter, UtilTypeAdapterFactory.defaultBuilderFactoryForCoercedCollection(Types.collectionTypeToken)),
						"[\"foo\",\"bar\",\"baz\"]",
						List.of("foo", "bar", "baz")
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(TestTypeAdapters.integerTypeAdapter, UtilTypeAdapterFactory.defaultBuilderFactoryForCoercedCollection(Types.collectionTypeToken)),
						"[39,42,100]",
						List.of(39, 42, 100)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(TestTypeAdapters.booleanTypeAdapter, UtilTypeAdapterFactory.defaultBuilderFactoryForCoercedCollection(Types.collectionTypeToken)),
						"[true,false,true]",
						List.of(true, false, true)
				)
		);
	}

}
