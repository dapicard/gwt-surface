package com.axeiya.gwt.surface.client.control.character;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractButtonControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.text.SubscriptInserter;

public class Subscript extends AbstractButtonControl {

  public Subscript() {
    this(ControlResources.Util.getInstance());
  }

  public Subscript(ControlResources resources) {
    super(new SubscriptInserter(), new Image(resources.subscript()), resources, CONSTANTS
        .subscript());
  }
}
