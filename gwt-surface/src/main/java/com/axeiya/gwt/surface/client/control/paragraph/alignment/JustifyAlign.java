package com.axeiya.gwt.surface.client.control.paragraph.alignment;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.paragraphinserter.alignment.JustifyAlignInserter;

public class JustifyAlign extends AbstractToggleControl {

  public JustifyAlign() {
    this(ControlResources.Util.getInstance());
  }

  public JustifyAlign(ControlResources resources) {
    super(new JustifyAlignInserter(), new Image(resources.alignJustify()), resources, CONSTANTS
        .justifyAlign());
  }
}
