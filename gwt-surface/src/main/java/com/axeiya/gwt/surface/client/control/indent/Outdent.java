package com.axeiya.gwt.surface.client.control.indent;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractButtonControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.indentinserter.OutdentInserter;

public class Outdent extends AbstractButtonControl {

  public Outdent() {
    this(ControlResources.Util.getInstance());
  }

  public Outdent(ControlResources resources) {
    super(new OutdentInserter(), new Image(resources.outdent()), resources, CONSTANTS.outdent());
  }
}
