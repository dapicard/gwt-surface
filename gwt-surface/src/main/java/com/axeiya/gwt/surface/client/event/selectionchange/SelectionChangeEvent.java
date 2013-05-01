package com.axeiya.gwt.surface.client.event.selectionchange;

import com.google.gwt.event.shared.GwtEvent;
import com.axeiya.gwt.surface.client.ranges.Selection;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class SelectionChangeEvent extends GwtEvent<SelectionChangeHandler> {

  private static Type<SelectionChangeHandler> TYPE;

  public static Type<SelectionChangeHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<SelectionChangeHandler>();
    }
    return TYPE;
  }

  private SurfaceSelection selection;

  protected SelectionChangeEvent(SurfaceSelection selection) {
    super();
    this.selection = selection;
  }

  public SurfaceSelection getSelection() {
    return selection;
  }

  @Override
  protected void dispatch(SelectionChangeHandler handler) {
    handler.onSelectionChange(this);
  }

  @Override
  public GwtEvent.Type<SelectionChangeHandler> getAssociatedType() {
    return getType();
  }

  public static void fire(HasSelectionChangeHandlers source, SurfaceSelection oldSelection) {
    source.fireEvent(new SelectionChangeEvent(oldSelection));
  }

  public static void fire(HasSelectionChangeHandlers source, SelectionChangeEvent event) {
    source.fireEvent(event);
  }
}
