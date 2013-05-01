package com.axeiya.gwt.surface.client.control.table;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.axeiya.gwt.surface.client.control.AbstractControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.event.selectionchange.SelectionChangeEvent;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.tableinserter.TableConfig;
import com.axeiya.gwt.surface.client.inserter.tableinserter.TableInserter;

@SuppressWarnings("rawtypes")
public class TableConfiguration extends AbstractControl implements IsWidget, ValueChangeHandler {

  private ControlResources resources;
  private FlowPanel ui;
  private IntegerBox borderSizeBox;
  private TableInserter inserter;
  private boolean tableSelected;

  public TableConfiguration() {
    this(ControlResources.Util.getInstance());
  }

  public TableConfiguration(ControlResources resources) {
    this.resources = resources;
    inserter = new TableInserter();

    ui = new FlowPanel();
    ui.setStyleName(resources.button().surfaceDiv());

    Label width = new Label(CONSTANTS.borderSize());
    width.setStyleName(resources.toolbar().label());
    ui.add(width);

    borderSizeBox = new IntegerBox();
    borderSizeBox.setWidth("25px");
    borderSizeBox.setAlignment(TextAlignment.RIGHT);
    borderSizeBox.addValueChangeHandler(this);
    ui.add(borderSizeBox);
  }

  @Override
  public void onSelectionChange(SelectionChangeEvent event) {
    tableSelected = inserter.isSelectionAssignee(event.getSelection());
    borderSizeBox.setEnabled(tableSelected);
    if (tableSelected) {
      TableConfig config = inserter.getCurrentConfig(event.getSelection());
      borderSizeBox.setValue(config.getBorderSize() > -1 ? config.getBorderSize() : null);
    }
  }

  @Override
  public Widget asWidget() {
    return ui;
  }

  @Override
  public Inserter getInserter() {
    return inserter;
  }

  @Override
  public void onValueChange(ValueChangeEvent event) {

  }

}
