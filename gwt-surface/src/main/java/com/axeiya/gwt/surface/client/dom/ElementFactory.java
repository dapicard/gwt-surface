package com.axeiya.gwt.surface.client.dom;

import com.google.gwt.user.client.DOM;

public class ElementFactory {

  public static SupElement createSupElement() {
    return SupElement.as(DOM.createElement(SupElement.TAG));
  }
  
  public static SubElement createSubElement() {
    return SubElement.as(DOM.createElement(SubElement.TAG));
  }
}
