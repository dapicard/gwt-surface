package com.axeiya.gwt.surface.client.event.selectedsurfacechange;

import com.google.gwt.event.shared.GwtEvent;
import com.axeiya.gwt.surface.client.Surface;

public class SelectedSurfaceChangeEvent extends GwtEvent<SelectedSurfaceChangeHandler> {

  private static Type<SelectedSurfaceChangeHandler> TYPE;

  public static Type<SelectedSurfaceChangeHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<SelectedSurfaceChangeHandler>();
    }
    return TYPE;
  }

  private Surface surface;

  public SelectedSurfaceChangeEvent(Surface surface) {
    super();
    this.surface = surface;
  }

  public Surface getSurface() {
    return surface;
  }

  @Override
  protected void dispatch(SelectedSurfaceChangeHandler handler) {
    handler.onSelectedSurfaceChange(this);
  }

  @Override
  public GwtEvent.Type<SelectedSurfaceChangeHandler> getAssociatedType() {
    return getType();
  }

  public static void fire(HasSelectedSurfaceChangeHandlers source, Surface surface) {
    SelectedSurfaceChangeEvent event = new SelectedSurfaceChangeEvent(surface);
    source.fireEvent(event);
  }

  public static void fire(HasSelectedSurfaceChangeHandlers source, SelectedSurfaceChangeEvent event) {
    source.fireEvent(event);
  }
}
