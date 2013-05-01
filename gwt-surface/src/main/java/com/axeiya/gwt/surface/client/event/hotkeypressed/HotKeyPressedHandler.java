package com.axeiya.gwt.surface.client.event.hotkeypressed;

import com.google.gwt.event.shared.EventHandler;


public interface HotKeyPressedHandler extends EventHandler {
	

	void onHotKeyPressed(HotKeyPressedEvent event);
}