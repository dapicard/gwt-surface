package com.axeiya.gwt.surface.sample.client;

import com.axeiya.gwt.surface.client.ContentEditableSurface;
import com.axeiya.gwt.surface.client.DefaultToolBar;
import com.axeiya.gwt.surface.client.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class SurfaceSample implements EntryPoint {

	@Override
	public void onModuleLoad() {
		ContentEditableSurface surface = new ContentEditableSurface();
		ToolBar toolbar = new DefaultToolBar();
		toolbar.addManagedSurface(surface);
		
		surface.setValue("GWT Surface sample application");
		
		RootPanel.get().add(toolbar);
		RootPanel.get().add(surface);
	}

}
