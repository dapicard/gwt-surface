package com.axeiya.gwt.surface.client.control.indent;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractButtonControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.indentinserter.IndentInserter;

public class Indent extends AbstractButtonControl {

  public Indent() {
    this(ControlResources.Util.getInstance());
  }

  public Indent(ControlResources resources) {
    super(new IndentInserter(), new Image(resources.indent()), resources, CONSTANTS.indent());
  }

}
