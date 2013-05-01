package com.axeiya.gwt.surface.client.control.list;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.listinserter.OListInserter;

public class OList extends AbstractToggleControl {

  public OList() {
    this(ControlResources.Util.getInstance());
  }

  public OList(ControlResources resources) {
    super(new OListInserter(), new Image(resources.orderedList()), resources, CONSTANTS
        .orderedlist());
  }
}
