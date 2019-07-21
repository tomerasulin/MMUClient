package com.hit.controller;

import java.util.Observable;

import com.hit.model.CacheUnitModel;
import com.hit.model.Model;
import com.hit.view.CacheUnitView;
import com.hit.view.View;

public class CacheUnitController implements Controller {

	private Model model;
	private View view;
	
	public CacheUnitController(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	public void update(Observable obs,Object obj) {
		if(obs instanceof CacheUnitView) {
			((CacheUnitModel)model).updateModelData(obj);
		}else if(obs instanceof CacheUnitModel){
			((CacheUnitView)view).updateGui((String)obj);;
		}
	}
}
