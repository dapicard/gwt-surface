package com.axeiya.gwt.surface.client.control.paragraph.alignment;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.paragraphinserter.alignment.CenterAlignInserter;

public class CenterAlign extends AbstractToggleControl {

  public CenterAlign() {
    this(ControlResources.Util.getInstance());
  }

  public CenterAlign(ControlResources resources) {
    super(new CenterAlignInserter(), new Image(resources.alignCenter()), resources, CONSTANTS
        .centerAlign());
  }
}
