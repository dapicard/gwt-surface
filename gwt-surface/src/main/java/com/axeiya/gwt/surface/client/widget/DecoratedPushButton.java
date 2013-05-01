package com.axeiya.gwt.surface.client.widget;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public class DecoratedPushButton extends PushButton {

  private DivElement leftBorder;
  private DivElement rightBorder;

  public DecoratedPushButton() {
    super();
    createDecoration();
  }

  public DecoratedPushButton(Image upImage) {
    super(upImage);
    createDecoration();
  }

  public DecoratedPushButton(String upText) {
    super(upText);
    createDecoration();
  }

  public DecoratedPushButton(Image upImage, ClickHandler handler) {
    super(upImage, handler);
    createDecoration();
  }

  public DecoratedPushButton(Image upImage, Image downImage) {
    super(upImage, downImage);
    createDecoration();
  }

  public DecoratedPushButton(String upText, ClickHandler handler) {
    super(upText, handler);
    createDecoration();
  }

  public DecoratedPushButton(String upText, String downText) {
    super(upText, downText);
    createDecoration();
  }

  public DecoratedPushButton(Image upImage, Image downImage, ClickHandler handler) {
    super(upImage, downImage, handler);
    createDecoration();
  }

  public DecoratedPushButton(String upText, String downText, ClickHandler handler) {
    super(upText, downText, handler);
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
