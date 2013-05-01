package com.axeiya.gwt.surface.client.event.enterkeypressed;

import com.google.gwt.event.shared.GwtEvent;
import com.axeiya.gwt.surface.client.ranges.Selection;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class EnterKeyPressedEvent extends GwtEvent<EnterKeyPressedHandler> {

  private static Type<EnterKeyPressedHandler> TYPE;

  public static Type<EnterKeyPressedHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<EnterKeyPressedHandler>();
    }
    return TYPE;
  }

  private SurfaceSelection selection;
  private boolean isAltKey;
  private boolean isCtrlKey;
  private boolean isMetaKey;
  private boolean handled = false;
  private boolean preventDefault = false;

  public EnterKeyPressedEvent(SurfaceSelection selection, boolean isAltKey, boolean isCtrlKey,
      boolean isMetaKey) {
    super();
    this.selection = selection;
    this.isAltKey = isAltKey;
    this.isCtrlKey = isCtrlKey;
    this.isMetaKey = isMetaKey;
  }

  public SurfaceSelection getSelection() {
    return selection;
  }

  public boolean isAltKey() {
    return isAltKey;
  }

  public boolean isCtrlKey() {
    return isCtrlKey;
  }

  public boolean isMetaKey() {
    return isMetaKey;
  }

  public boolean isHandled() {
    return handled;
  }

  public void setHandled(boolean handled) {
    this.handled = handled;
  }

  public boolean isPreventDefault() {
    return preventDefault;
  }

  public void setPreventDefault(boolean preventDefault) {
    this.preventDefault = preventDefault;
  }

  @Override
  protected void dispatch(EnterKeyPressedHandler handler) {
    handler.onEnterKeyPressed(this);
  }

  @Override
  public GwtEvent.Type<EnterKeyPressedHandler> getAssociatedType() {
    return getType();
  }

  public static EnterKeyPressedEvent fire(HasEnterKeyPressedHandlers source, SurfaceSelection selection,
      boolean isAltKey, boolean isCtrlKey, boolean isMetaKey) {
    EnterKeyPressedEvent event =
        new EnterKeyPressedEvent(selection, isAltKey, isCtrlKey, isMetaKey);
    source.fireEvent(event);
    return event;
  }

  public static void fire(HasEnterKeyPressedHandlers source, EnterKeyPressedEvent event) {
    source.fireEvent(event);
  }
}
