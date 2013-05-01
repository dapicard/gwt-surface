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
import com.axeiya.gwt.surface.client.inserter.tableinserter.TableColumnInserter;
import com.axeiya.gwt.surface.client.widget.DecoratedPushButton;

public class DropColumn extends AbstractControl implements ClickHandler, IsWidget {

  private DecoratedPushButton ui;
  private TableColumnInserter inserter;

  public DropColumn() {
    this(ControlResources.Util.getInstance());
  }

  public DropColumn(ControlResources resources) {
    ui = new DecoratedPushButton(new Image(resources.dropColumn()));
    ui.setTitle(CONSTANTS.dropColumn());
    ui.setStyleName(resources.button().surfacePushButton());
    inserter = new TableColumnInserter();
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
