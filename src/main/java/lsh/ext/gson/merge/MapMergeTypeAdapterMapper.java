package lsh.ext.gson.merge;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.internal.ConstructorConstructors;

/**
 * A merge type adapter mapper for {@link Map}.
 *
 * @author Lyubomyr Shaydariv
 * @see MapTypeAdapterFactory
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapMergeTypeAdapterMapper
		implements IMergeTypeAdapterMapper {

	private static final IMergeTypeAdapterMapper instance = new MapMergeTypeAdapterMapper(false);
	private static final IMergeTypeAdapterMapper instanceComplex = new MapMergeTypeAdapterMapper(true);

	private final boolean complexMapKeySerialization;

	/**
	 * @return An instance of {@link MapMergeTypeAdapterMapper}.
	 */
	public static IMergeTypeAdapterMapper getInstance() {
		return getInstance(false);
	}

	/**
	 * @param complexMapKeySerialization Specifies if {@link MapTypeAdapterFactory#complexMapKeySerialization} should be used.
	 *
	 * @return An instance of {@link MapMergeTypeAdapterMapper}.
	 */
	public static IMergeTypeAdapterMapper getInstance(final boolean complexMapKeySerialization) {
		return !complexMapKeySerialization ? instance : instanceComplex;
	}

	@Nullable
	@Override
	public <T> TypeAdapter<T> map(final TypeAdapter<?> typeAdapter, final Object instance, final Gson gson,
			final TypeToken<T> typeToken) {
		if ( typeAdapter.getClass().getEnclosingClass() != MapTypeAdapterFactory.class ) {
			return null;
		}
		@SuppressWarnings("unchecked")
		final Map<Type, InstanceCreator<?>> instanceCreators = Collections.singletonMap(typeToken.getType(), type -> new NullOnPutMap<>((Map<Object, Object>) instance));
		final ConstructorConstructor constructorConstructor = ConstructorConstructors.create(instanceCreators);
		final TypeAdapterFactory typeAdapterFactory = new MapTypeAdapterFactory(constructorConstructor, complexMapKeySerialization);
		return typeAdapterFactory.create(gson, typeToken);
	}

	private static final class NullOnPutMap<K, V>
			extends ForwardingMap<K, V> {

		private NullOnPutMap(final Map<K, V> map) {
			super(map);
		}

		@Override
		@Nullable
		public V put(final K key, final V value) {
			super.put(key, value);
			return null;
		}

	}

	private abstract static class ForwardingMap<K, V>
			implements Map<K, V> {

		private final Map<K, V> map;

		protected ForwardingMap(final Map<K, V> map) {
			this.map = map;
		}

		// @formatter:off
		@Override public int size() { return map.size(); }
		@Override public boolean isEmpty() { return map.isEmpty(); }
		@Override public boolean containsKey(final Object key) { return map.containsKey(key); }
		@Override public boolean containsValue(final Object value) { return map.containsValue(value); }
		@Override public V get(final Object key) { return map.get(key); }
		@Override public V put(final K key, final V value) { return map.put(key, value); }
		@Override public V remove(final Object key) { return map.remove(key); }
		@Override public void putAll(final Map<? extends K, ? extends V> m) { map.putAll(m); }
		@Override public void clear() { map.clear(); }
		@Override public Set<K> keySet() { return map.keySet(); }
		@Override public Collection<V> values() { return map.values(); }
		@Override public Set<Entry<K, V>> entrySet() { return map.entrySet(); }
		// @formatter:on
	}

}
