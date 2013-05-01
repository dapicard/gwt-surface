package com.axeiya.gwt.surface.client.control.image;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.axeiya.gwt.surface.client.control.AbstractControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.event.selectionchange.SelectionChangeEvent;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.blockinserter.media.ImageInserter;
import com.axeiya.gwt.surface.client.inserter.blockinserter.media.ImageInserter.ImageConfig;

public class ImageSize extends AbstractControl implements ValueChangeHandler<Integer>, IsWidget {

  private ControlResources resources;

  private FlowPanel ui;
  private ImageInserter inserter;
  private boolean imgSelected = false;

  private IntegerBox widthBox;
  private IntegerBox heightBox;

  public ImageSize() {
    this(ControlResources.Util.getInstance());
  }

  public ImageSize(ControlResources resources) {
    this.resources = resources;

    ui = new FlowPanel();
    ui.setStyleName(resources.button().surfaceDiv());
    Label width = new Label(CONSTANTS.width());
    width.setStyleName(resources.toolbar().label());
    ui.add(width);

    widthBox = new IntegerBox();
    widthBox.setWidth("50px");
    ui.add(widthBox);
    // Label px1 = new Label("px");
    // ui.add(px1);

    Label height = new Label(CONSTANTS.height());
    height.setStyleName(resources.toolbar().label());
    ui.add(height);

    heightBox = new IntegerBox();
    heightBox.setWidth("50px");
    ui.add(heightBox);
    // Label px2 = new Label("px");
    // ui.add(px2);

    inserter = new ImageInserter();
    widthBox.addValueChangeHandler(this);
    heightBox.addValueChangeHandler(this);

    widthBox.setEnabled(false);
    heightBox.setEnabled(false);
  }

  @Override
  public void onSelectionChange(SelectionChangeEvent event) {
    imgSelected = inserter.isSelectionAssignee(event.getSelection());
    widthBox.setEnabled(imgSelected);
    heightBox.setEnabled(imgSelected);
    if (imgSelected) {
      ImageConfig config = inserter.getCurrentConfig(event.getSelection());
      widthBox.setValue(config.getWidth() > -1 ? config.getWidth() : null);
      heightBox.setValue(config.getHeight() > -1 ? config.getHeight() : null);
    }
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
  public void onValueChange(ValueChangeEvent<Integer> event) {
    if (imgSelected) {
      ImageConfig config = inserter.getCurrentConfig(currentSurface.getSelection());
      config.setWidth(widthBox.getValue() != null ? widthBox.getValue() : -1);
      config.setHeight(heightBox.getValue() != null ? heightBox.getValue() : -1);
      inserter.updateConfig(currentSurface.getSelection(), config);
    }
  }

}
