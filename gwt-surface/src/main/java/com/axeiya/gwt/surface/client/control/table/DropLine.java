package com.axeiya.gwt.surface.client.control.table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.axeiya.gwt.surface.client.control.AbstractControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.event.selectionchange.SelectionChangeEvent;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.tableinserter.TableLineInserter;
import com.axeiya.gwt.surface.client.widget.DecoratedPushButton;

public class DropLine extends AbstractControl implements ClickHandler, IsWidget {

  private DecoratedPushButton ui;
  private TableLineInserter inserter;

  public DropLine() {
    this(ControlResources.Util.getInstance());
  }

  public DropLine(ControlResources resources) {
    ui = new DecoratedPushButton(new Image(resources.dropRow()));
    ui.setTitle(CONSTANTS.dropLine());
    ui.setStyleName(resources.button().surfacePushButton());
    inserter = new TableLineInserter();
    ui.addClickHandler(this);
  }

  @Override
  public void onSelectionChange(SelectionChangeEvent event) {
    ui.setEnabled(inserter.isSelectionAssignee(event.getSelection()));
  }

  @Override
  public void onClick(ClickEvent event) {
    execute(new Command() {
      @Override
      public void execute() {
        inserter.remove(currentSurface.getSelection());
      }
    });
  }

  @Override
  public Inserter getInserter() {
    return inserter;
  }

  @Override
  public Widget asWidget() {
    return ui;
  }
}
