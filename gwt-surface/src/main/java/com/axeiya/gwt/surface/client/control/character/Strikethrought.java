package com.axeiya.gwt.surface.client.control.character;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.text.StrikethroughtInserter;

public class Strikethrought extends AbstractToggleControl {

  public Strikethrought() {
    this(ControlResources.Util.getInstance());
  }

  public Strikethrought(ControlResources resources) {
    super(new StrikethroughtInserter(), new Image(resources.strikethrought()), resources, CONSTANTS
        .strikethrought());
  }
}
