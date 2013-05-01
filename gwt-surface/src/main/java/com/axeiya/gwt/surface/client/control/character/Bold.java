package com.axeiya.gwt.surface.client.control.character;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.text.BoldInserter;

public class Bold extends AbstractToggleControl {

  public Bold() {
    this(ControlResources.Util.getInstance());
  }

  public Bold(ControlResources resources) {
    super(new BoldInserter(), new Image(resources.bold()), resources, CONSTANTS.bold());
  }
}
