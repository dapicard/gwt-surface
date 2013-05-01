package com.axeiya.gwt.surface.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.event.selectionchange.SelectionChangeEvent;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.widget.DecoratedPushButton;

public class AbstractButtonControl extends AbstractClickableControl implements ClickHandler,
    IsWidget {

  private DecoratedPushButton ui;
  private Inserter inserter;

  public AbstractButtonControl(Inserter inserter, Image icon, ControlResources resources) {
    this(inserter, icon, resources, "");
  }

  public AbstractButtonControl(Inserter inserter, Image icon, ControlResources resources,
      String tooltip) {
    ui = new DecoratedPushButton(icon);
    ui.setTitle(tooltip);
    ui.setStyleName(resources.button().surfacePushButton());
    this.inserter = inserter;
    ui.addClickHandler(this);
  }

  @Override
  public void onClick(ClickEvent event) {
    execute(new Command() {
      @Override
      public void execute() {
        inserter.insert(currentSurface.getSelection());
      }
    });
  }

  @Override
  public void onSelectionChange(SelectionChangeEvent event) {
  }

  public DecoratedPushButton getUi() {
    return ui;
  }

  public Inserter getInserter() {
    return inserter;
  }

  @Override
  public Widget asWidget() {
    return ui;
  }

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return ui.addClickHandler(handler);
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
    ui.fireEvent(event);
  }

}
