package com.axeiya.gwt.surface.client.control.headless;

import com.axeiya.gwt.surface.client.control.AbstractControl;
import com.axeiya.gwt.surface.client.inserter.Inserter;

abstract public class AbstractHeadlessControl extends AbstractControl {

  private Inserter inserter;

  public AbstractHeadlessControl(Inserter inserter) {
    this.inserter = inserter;
  }

  @Override
  public Inserter getInserter() {
    return inserter;
  }

}
