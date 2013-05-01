package com.axeiya.gwt.surface.client.inserter.tableinserter;

import com.google.gwt.dom.client.Style.Unit;

public class TableConfig {

  public enum CaptionAlign {
    TOP, BOTTOM
  }

  private boolean headerLine;
  private int width;
  private Unit widthUnit;
  private int line;
  private int column;
  private String caption;
  private CaptionAlign captionAlign = CaptionAlign.BOTTOM;
  private int borderSize;

  public TableConfig(boolean headerLine, int width, Unit widthUnit, int line, int column,
      int borderSize) {
    super();
    this.headerLine = headerLine;
    this.width = width;
    this.widthUnit = widthUnit;
    this.line = line;
    this.column = column;
    this.borderSize = borderSize;
  }

  public TableConfig(int line, int column, boolean headerLine) {
    this(headerLine, 100, Unit.PCT, line, column, 1);
  }

  public TableConfig() {
  }

  public boolean isHeaderLine() {
    return headerLine;
  }

  public void setHeaderLine(boolean headerLine) {
    this.headerLine = headerLine;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public Unit getWidthUnit() {
    return widthUnit;
  }

  public void setWidthUnit(Unit widthUnit) {
    this.widthUnit = widthUnit;
  }

  public int getLine() {
    return line;
  }

  public void setLine(int line) {
    this.line = line;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public CaptionAlign getCaptionAlign() {
    return captionAlign;
  }

  public void setCaptionAlign(CaptionAlign captionAlign) {
    this.captionAlign = captionAlign;
  }

  public int getBorderSize() {
    return borderSize;
  }

  public void setBorderSize(int borderSize) {
    this.borderSize = borderSize;
  }

}
