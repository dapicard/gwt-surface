package com.axeiya.gwt.surface.client.widget;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;

public class DecoratedToggleButton extends ToggleButton {

  private DivElement leftBorder;
  private DivElement rightBorder;

  public DecoratedToggleButton() {
    super();
    createDecoration();
  }

  public DecoratedToggleButton(Image upImage, ClickHandler handler) {
    super(upImage, handler);
    createDecoration();
  }

  public DecoratedToggleButton(Image upImage, Image downImage, ClickHandler handler) {
    super(upImage, downImage, handler);
    createDecoration();
  }

  public DecoratedToggleButton(Image upImage, Image downImage) {
    super(upImage, downImage);
    createDecoration();
  }

  public DecoratedToggleButton(Image upImage) {
    super(upImage);
    createDecoration();
  }

  public DecoratedToggleButton(String upText, ClickHandler handler) {
    super(upText, handler);
    createDecoration();
  }

  public DecoratedToggleButton(String upText, String downText, ClickHandler handler) {
    super(upText, downText, handler);
    createDecoration();
  }

  public DecoratedToggleButton(String upText, String downText) {
    super(upText, downText);
    createDecoration();
  }

  public DecoratedToggleButton(String upText) {
    super(upText);
    createDecoration();
  }

  protected void createDecoration() {
    leftBorder = Document.get().createDivElement();
    leftBorder.setClassName("surface-DecoratedButton-LeftBorder");
    rightBorder = Document.get().createDivElement();
    rightBorder.setClassName("surface-DecoratedButton-RightBorder");
    getElement().appendChild(leftBorder);
    getElement().appendChild(rightBorder);
  }

}
