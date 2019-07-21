package com.hit.model;

import java.util.Observable;

public class CacheUnitModel extends Observable implements Model {

	private CacheUnitClient mClient;
	
	public CacheUnitModel() {
		mClient = new CacheUnitClient();
		
	}
	
	public <T> void updateModelData(T t) {
		setChanged();
		notifyObservers(mClient.send((String)t));
	}

}
