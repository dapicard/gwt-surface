package com.axeiya.gwt.surface.client.control.character;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.text.ItalicInserter;

public class Italic extends AbstractToggleControl {

  public Italic() {
    this(ControlResources.Util.getInstance());
  }

  public Italic(ControlResources resources) {
    super(new ItalicInserter(), new Image(resources.italic()), resources, CONSTANTS.italic());
  }
}
