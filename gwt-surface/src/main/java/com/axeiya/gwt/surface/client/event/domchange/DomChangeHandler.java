package com.axeiya.gwt.surface.client.event.domchange;

import com.google.gwt.event.shared.EventHandler;

public interface DomChangeHandler extends EventHandler {

	void onDomChange(DomChangeEvent event);
}