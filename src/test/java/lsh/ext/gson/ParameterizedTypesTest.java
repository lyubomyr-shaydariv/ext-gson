package lsh.ext.gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import static lsh.ext.gson.MoreMatchers.isParameterizedType;
import static lsh.ext.gson.ParameterizedTypes.listType;
import static lsh.ext.gson.ParameterizedTypes.mapType;
import static lsh.ext.gson.ParameterizedTypes.resolveTypeArguments;
import static lsh.ext.gson.ParameterizedTypes.setType;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public final class ParameterizedTypesTest {

	@Test
	public void testResolveTypeArgumentsForRawTypes() {
		assertThat(resolveTypeArguments(List.class), is(new Type[]{ Object.class }));
	}

	@Test
	public void testResolveTypeArgumentsForParameterizedTypes() {
		assertThat(resolveTypeArguments(listType(String.class)), is(new Type[]{ String.class }));
		assertThat(resolveTypeArguments(mapType(Integer.class, Float.class)), is(new Type[]{ Integer.class, Float.class }));
	}

	@Test
	public void testResolveTypeArgumentsForNonGenericType() {
		assertThat(resolveTypeArguments(String.class), is(new Type[]{}));
	}

	@Test
	public void testListType() {
		assertThat(listType(String.class), isParameterizedType(List.class, String.class));
		assertThat(listType(Void.class), isParameterizedType(List.class, Void.class));
		assertThat(listType(Object.class), isParameterizedType(List.class, Object.class));
		assertThat(listType(String.class), is(listType(String.class)));
	}

	@Test
	public void testSetType() {
		assertThat(setType(String.class), isParameterizedType(Set.class, String.class));
		assertThat(setType(Void.class), isParameterizedType(Set.class, Void.class));
		assertThat(setType(Object.class), isParameterizedType(Set.class, Object.class));
		assertThat(setType(String.class), is(setType(String.class)));
	}

	@Test
	public void testMapType() {
		assertThat(mapType(String.class, Void.class), isParameterizedType(Map.class, String.class, Void.class));
		assertThat(mapType(Void.class, Object.class), isParameterizedType(Map.class, Void.class, Object.class));
		assertThat(mapType(Object.class, String.class), isParameterizedType(Map.class, Object.class, String.class));
		assertThat(mapType(String.class, Void.class), is(mapType(String.class, Void.class)));
	}

}
