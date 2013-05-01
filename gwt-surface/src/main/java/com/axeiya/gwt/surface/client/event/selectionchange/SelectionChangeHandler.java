package com.axeiya.gwt.surface.client.event.selectionchange;

import com.google.gwt.event.shared.EventHandler;


public interface SelectionChangeHandler extends EventHandler {
	

	void onSelectionChange(SelectionChangeEvent event);
}