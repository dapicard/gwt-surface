package com.axeiya.gwt.surface.client;

import java.util.ArrayList;
import java.util.List;

import com.axeiya.gwt.surface.client.control.AbstractClickableControl;
import com.axeiya.gwt.surface.client.control.AbstractControl;

public class ToolGroup {

  private AbstractClickableControl mainComponent;
  private List<AbstractControl> subComponents;

  public ToolGroup(AbstractClickableControl mainComponent) {
    this.mainComponent = mainComponent;
    subComponents = new ArrayList<AbstractControl>();
  }

  public AbstractClickableControl getMainComponent() {
    return mainComponent;
  }

  public List<AbstractControl> getSubComponents() {
    return subComponents;
  }

  public void addSubComponent(AbstractControl component) {
    subComponents.add(component);
  }
}
