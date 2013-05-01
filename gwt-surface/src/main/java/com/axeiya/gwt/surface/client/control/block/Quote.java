package com.axeiya.gwt.surface.client.control.block;

import com.google.gwt.user.client.ui.Image;
import com.axeiya.gwt.surface.client.control.AbstractToggleControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.inserter.blockinserter.text.QuoteInserter;

public class Quote extends AbstractToggleControl {

  public Quote() {
    this(ControlResources.Util.getInstance());
  }

  public Quote(ControlResources resources) {
    super(new QuoteInserter(), new Image(resources.quote()), resources, CONSTANTS.quote());
  }
}
