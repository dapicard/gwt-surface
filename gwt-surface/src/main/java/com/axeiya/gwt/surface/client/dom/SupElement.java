package com.axeiya.gwt.surface.client.dom;

import com.google.gwt.dom.client.Element;

public class SupElement extends Element {

  public static final String TAG = "sup";
  
  public static SupElement as(Element elem) {
    assert elem.getTagName().equalsIgnoreCase(TAG);
    return (SupElement) elem;
  }
  
  protected SupElement() {
  }
}
