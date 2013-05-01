package com.axeiya.gwt.surface.client.control.paragraph.alignment;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.paragraphinserter.alignment.LeftAlignInserter;

public class LeftAlign extends AbstractToggleControl {

  public LeftAlign() {
    this(ControlResources.Util.getInstance());
  }

  public LeftAlign(ControlResources resources) {
    super(new LeftAlignInserter(), new Image(resources.alignLeft()), resources, CONSTANTS
        .leftAlign());
  }
}
