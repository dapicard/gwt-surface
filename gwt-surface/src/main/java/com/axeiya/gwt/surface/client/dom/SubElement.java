package com.axeiya.gwt.surface.client.dom;

import com.google.gwt.dom.client.Element;

public class SubElement extends Element {

  public static final String TAG = "sub";
  
  public static SubElement as(Element elem) {
    assert elem.getTagName().equalsIgnoreCase(TAG);
    return (SubElement) elem;
  }
  
  protected SubElement() {
  }
}
