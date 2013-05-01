package com.axeiya.gwt.surface.client.control.list;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.listinserter.UListInserter;

public class UList extends AbstractToggleControl {

  public UList() {
    this(ControlResources.Util.getInstance());
  }

  public UList(ControlResources resources) {
    super(new UListInserter(), new Image(resources.unorderedList()), resources, CONSTANTS
        .unorderedlist());
  }
}
