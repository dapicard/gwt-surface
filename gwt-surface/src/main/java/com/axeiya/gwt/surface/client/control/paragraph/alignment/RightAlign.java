package com.axeiya.gwt.surface.client.control.paragraph.alignment;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.paragraphinserter.alignment.RightAlignInserter;

public class RightAlign extends AbstractToggleControl {

  public RightAlign() {
    this(ControlResources.Util.getInstance());
  }

  public RightAlign(ControlResources resources) {
    super(new RightAlignInserter(), new Image(resources.alignRight()), resources, CONSTANTS
        .rightAlign());
  }
}
