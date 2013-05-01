package com.axeiya.gwt.surface.client.ranges;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Node;

public final class Selection extends JavaScriptObject {
  
  protected Selection() {
  }

  /**
   * @return La sélection sur le document principal
   */
  public static final native Selection getSelection() /*-{
		return $wnd['rangy'].getSelection();
  }-*/;

  public static final native Selection getIFrameSelection(IFrameElement iframe) /*-{
		return $wnd['rangy'].getIframeSelection(iframe);
  }-*/;

  public native JavaScriptObject getNativeSelection() /*-{
		return this.nativeSelection;
  }-*/;

  public native int getRangeCount() /*-{
		return this.rangeCount;
  }-*/;

  public native boolean isCollapsed() /*-{
		return this.isCollapsed;
  }-*/;

  public native Node getAnchorNode() /*-{
		return this.anchorNode;
  }-*/;

  public native int getAnchorOffset() /*-{
		return this.anchorOffset;
  }-*/;

  public native Node getFocusNode() /*-{
		return this.focusNode;
  }-*/;

  public native int getFocusOffset() /*-{
		return this.focusOffset;
  }-*/;

  public native Range getRangeAt(int index) /*-{
		return this.getRangeAt(index);
  }-*/;

  public Range getRange() {
    try {
      return getRangeAt(0);
    } catch (JavaScriptException jse) {
      return null;
    }
  }

  public native void setSingleRange(Range range) /*-{
		this.setSingleRange(range);
  }-*/;

  public native String toHtml() /*-{
		return this.toHtml();
  }-*/;
}
