package com.axeiya.gwt.surface.client;

import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.axeiya.gwt.surface.client.event.domchange.HasDomChangeHandlers;
import com.axeiya.gwt.surface.client.event.enterkeypressed.HasEnterKeyPressedHandlers;
import com.axeiya.gwt.surface.client.event.hotkeypressed.HasHotKeyPressedHandlers;
import com.axeiya.gwt.surface.client.event.selectedsurfacechange.HasSelectedSurfaceChangeHandlers;
import com.axeiya.gwt.surface.client.event.selectionchange.HasSelectionChangeHandlers;
import com.axeiya.gwt.surface.client.processor.InPlaceProcessor;
import com.axeiya.gwt.surface.client.processor.Processor;
import com.axeiya.gwt.surface.client.ranges.Range;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public interface Surface extends HasSelectionChangeHandlers, HasEnterKeyPressedHandlers, HasHotKeyPressedHandlers, HasSelectedSurfaceChangeHandlers, IsWidget, HasValue<String>,
		HasFocusHandlers, HasDomChangeHandlers {

	SurfaceSelection getSelection();

	void setSelection(Range range);

	void notifyUpdate();

	void focus();

	void addPostProcessor(Processor postProcessor);

	void addPreProcessor(Processor preProcessor);

	void processOnce(InPlaceProcessor processor);

	com.google.gwt.dom.client.Element getElement();

	boolean isEditable();

	void setEditable(boolean editable);

	void setSelection(Range range, boolean fireEvent);

	void fireDomChange();

}