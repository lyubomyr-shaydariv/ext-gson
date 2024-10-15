package lsh.ext.gson.ext.org.apache.commons.collections4;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.BoundedCollection;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class ApacheCommonsCollections4Module
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<? extends Bag<?>> bagTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForBag;

	@Setter
	private ITypeAdapterFactory<? extends BidiMap<String, ?>> bidiMapTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForBidiMap;

	@Setter
	private ITypeAdapterFactory<? extends BoundedCollection<?>> boundedCollectionTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForBoundedCollection;

	@Setter
	private ITypeAdapterFactory<? extends IterableMap<String, ?>> iterableMapTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForIterableMap;

	@Setter
	private ITypeAdapterFactory<? extends KeyValue<String, ?>> keyTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForKeyValue;

	@Setter
	private ITypeAdapterFactory<? extends MultiSet<?>> multiSetTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForMultiSet;

	@Setter
	private ITypeAdapterFactory<? extends MultiValuedMap<String, ?>> multiValuedMapTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForMultiValueMap;

	@Override
	public IModule build() {
		return IModule.from(
				bagTypeAdapterFactory,
				bidiMapTypeAdapterFactory,
				boundedCollectionTypeAdapterFactory,
				iterableMapTypeAdapterFactory,
				keyTypeAdapterFactory,
				multiSetTypeAdapterFactory,
				multiValuedMapTypeAdapterFactory
		);
	}

}
