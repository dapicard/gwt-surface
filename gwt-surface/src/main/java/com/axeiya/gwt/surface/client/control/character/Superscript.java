package com.axeiya.gwt.surface.client.control.character;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractButtonControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.text.SuperscriptInserter;

public class Superscript extends AbstractButtonControl {

  public Superscript() {
    this(ControlResources.Util.getInstance());
  }

  public Superscript(ControlResources resources) {
    super(new SuperscriptInserter(), new Image(resources.superscript()), resources, CONSTANTS
        .superscript());
  }
}
