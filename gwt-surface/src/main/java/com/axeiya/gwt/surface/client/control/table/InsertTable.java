package com.axeiya.gwt.surface.client.control.table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.axeiya.gwt.surface.client.control.AbstractClickableControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.event.selectionchange.SelectionChangeEvent;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.tableinserter.TableConfig;
import com.axeiya.gwt.surface.client.inserter.tableinserter.TableInserter;
import com.axeiya.gwt.surface.client.widget.DecoratedPushButton;

public class InsertTable extends AbstractClickableControl implements ClickHandler, IsWidget {

  private DecoratedPushButton ui;
  private TableInserter inserter;

  public InsertTable() {
    this(ControlResources.Util.getInstance());
  }

  public InsertTable(ControlResources resources) {
    ui = new DecoratedPushButton(new Image(resources.addTable()));
    ui.setTitle(CONSTANTS.insertTable());
    ui.setStyleName(resources.button().surfacePushButton());
    inserter = new TableInserter();
    ui.addClickHandler(this);
  }

  @Override
  public void onSelectionChange(SelectionChangeEvent event) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onClick(ClickEvent event) {
    final Integer line = Integer.parseInt(Window.prompt("Nombre de lignes", "4"));
    final Integer column = Integer.parseInt(Window.prompt("Nombre de colonnes", "4"));
    execute(new Command() {
      @Override
      public void execute() {
        inserter.insert(currentSurface.getSelection(), new TableConfig(line, column, true));
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

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return ui.addClickHandler(handler);
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
    ui.fireEvent(event);
  }
}
