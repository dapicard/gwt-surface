package com.axeiya.gwt.surface.client.event.selectedsurfacechange;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasSelectedSurfaceChangeHandlers extends HasHandlers {

	HandlerRegistration addSelectedSurfaceChangeHandler(SelectedSurfaceChangeHandler handler);
}
