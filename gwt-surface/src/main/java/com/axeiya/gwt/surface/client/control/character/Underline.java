package com.axeiya.gwt.surface.client.control.character;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.text.UnderlineInserter;

public class Underline extends AbstractToggleControl {

  public Underline() {
    this(ControlResources.Util.getInstance());
  }

  public Underline(ControlResources resources) {
    super(new UnderlineInserter(), new Image(resources.underline()), resources, CONSTANTS
        .underline());
  }
}
