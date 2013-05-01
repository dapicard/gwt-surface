package com.axeiya.gwt.surface.client.control.block;

import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.inserter.blockinserter.text.PreInserter;

public class Pre extends AbstractToggleControl {

  public Pre() {
    super(new PreInserter(), "Pre");
  }
}
