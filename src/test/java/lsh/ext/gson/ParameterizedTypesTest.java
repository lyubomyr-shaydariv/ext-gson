package lsh.ext.gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class ParameterizedTypesTest {

	private static final Type[] emptyTypeArray = new Type[0];

	@Test
	public void testResolveTypeArgumentsForRawTypes() {
		MatcherAssert.assertThat(ParameterizedTypes.getTypeArguments(List.class), CoreMatchers.is(new Type[][]{ new Type[]{ Object.class } }));
	}

	@Test
	public void testResolveTypeArgumentsForParameterizedTypes() {
		MatcherAssert.assertThat(ParameterizedTypes.getTypeArguments(ParameterizedTypes.listOf(String.class)), CoreMatchers.is(new Type[][]{ new Type[]{ String.class } }));
		MatcherAssert.assertThat(ParameterizedTypes.getTypeArguments(ParameterizedTypes.mapOf(Integer.class, Float.class)), CoreMatchers.is(new Type[][]{ new Type[]{ Integer.class }, new Type[]{ Float.class } }));
	}

	@Test
	public void testResolveTypeArgumentsForParameterizedTypesWithBounds() {
		MatcherAssert.assertThat(ParameterizedTypes.getTypeArguments(GenericClassWithSomeBounds.class), CoreMatchers.is(new Type[][]{ new Type[]{ Number.class }, new Type[]{ CharSequence.class, new TypeToken<List<?>>() {}.getType() } }));
	}

	@Test
	public void testResolveTypeArgumentsForNonGenericType() {
		MatcherAssert.assertThat(ParameterizedTypes.getTypeArguments(String.class), CoreMatchers.is(emptyTypeArray));
	}

	@Test
	public void testListType() {
		MatcherAssert.assertThat(ParameterizedTypes.listOf(String.class), MoreMatchers.isParameterizedType(List.class, String.class));
		MatcherAssert.assertThat(ParameterizedTypes.listOf(Void.class), MoreMatchers.isParameterizedType(List.class, Void.class));
		MatcherAssert.assertThat(ParameterizedTypes.listOf(Object.class), MoreMatchers.isParameterizedType(List.class, Object.class));
		MatcherAssert.assertThat(ParameterizedTypes.listOf(String.class), CoreMatchers.is(ParameterizedTypes.listOf(String.class)));
	}

	@Test
	public void testSetType() {
		MatcherAssert.assertThat(ParameterizedTypes.setOf(String.class), MoreMatchers.isParameterizedType(Set.class, String.class));
		MatcherAssert.assertThat(ParameterizedTypes.setOf(Void.class), MoreMatchers.isParameterizedType(Set.class, Void.class));
		MatcherAssert.assertThat(ParameterizedTypes.setOf(Object.class), MoreMatchers.isParameterizedType(Set.class, Object.class));
		MatcherAssert.assertThat(ParameterizedTypes.setOf(String.class), CoreMatchers.is(ParameterizedTypes.setOf(String.class)));
	}

	@Test
	public void testMapType() {
		MatcherAssert.assertThat(ParameterizedTypes.mapOf(String.class, Void.class), MoreMatchers.isParameterizedType(Map.class, String.class, Void.class));
		MatcherAssert.assertThat(ParameterizedTypes.mapOf(Void.class, Object.class), MoreMatchers.isParameterizedType(Map.class, Void.class, Object.class));
		MatcherAssert.assertThat(ParameterizedTypes.mapOf(Object.class, String.class), MoreMatchers.isParameterizedType(Map.class, Object.class, String.class));
		MatcherAssert.assertThat(ParameterizedTypes.mapOf(String.class, Void.class), CoreMatchers.is(ParameterizedTypes.mapOf(String.class, Void.class)));
	}

	private static final class GenericClassWithSomeBounds<N extends Number, CS extends CharSequence & List<?>> {
	}

}
