package com.axeiya.gwt.surface.client.widget;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;

public class Separator extends Image {

  public Separator() {
    this(ControlResources.Util.getInstance());
  }

  public Separator(ControlResources resources) {
    super(resources.separator());
    setStyleName(resources.button().separator());
  }
}
