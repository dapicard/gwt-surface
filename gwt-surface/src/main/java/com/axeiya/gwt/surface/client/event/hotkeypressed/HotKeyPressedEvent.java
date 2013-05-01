package com.axeiya.gwt.surface.client.event.hotkeypressed;

import com.google.gwt.event.shared.GwtEvent;
import com.axeiya.gwt.surface.client.ranges.Selection;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class HotKeyPressedEvent extends GwtEvent<HotKeyPressedHandler> {

  private static Type<HotKeyPressedHandler> TYPE;

  public static Type<HotKeyPressedHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<HotKeyPressedHandler>();
    }
    return TYPE;
  }

  private SurfaceSelection selection;
  private boolean isAltKey;
  private boolean isCtrlKey;
  private boolean isMetaKey;
  private int keyCode;
  private boolean handled = false;
  private boolean preventDefault = false;

  public HotKeyPressedEvent(SurfaceSelection selection, boolean isAltKey, boolean isCtrlKey,
      boolean isMetaKey, int keyCode) {
    super();
    this.selection = selection;
    this.isAltKey = isAltKey;
    this.isCtrlKey = isCtrlKey;
    this.isMetaKey = isMetaKey;
    this.keyCode = keyCode;
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

  public int getKeyCode() {
    return keyCode;
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
  protected void dispatch(HotKeyPressedHandler handler) {
    handler.onHotKeyPressed(this);
  }

  @Override
  public GwtEvent.Type<HotKeyPressedHandler> getAssociatedType() {
    return getType();
  }

  public static HotKeyPressedEvent fire(HasHotKeyPressedHandlers source, SurfaceSelection selection,
      boolean isAltKey, boolean isCtrlKey, boolean isMetaKey, int charCode) {
    HotKeyPressedEvent event =
        new HotKeyPressedEvent(selection, isAltKey, isCtrlKey, isMetaKey, charCode);
    source.fireEvent(event);
    return event;
  }

  public static void fire(HasHotKeyPressedHandlers source, HotKeyPressedEvent event) {
    source.fireEvent(event);
  }
}
