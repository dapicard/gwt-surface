package com.axeiya.gwt.surface.client.inserter.inlineinserter;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class BackgroundColorInserter extends InlineInserter<Element> {

  private BackgroundColorConfig currentConfig;

  public BackgroundColorInserter() {
    action = new FontFamilyInsertAction();
    invertAction = new FontFamilyInvertAction();
  }

  protected BackgroundColorInserter(InsertAction<Element> action, InsertAction<Element> invertAction) {
    super(action, invertAction);
  }

  protected class FontFamilyInsertAction extends InsertAction<Element> {
    private final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
      if (BackgroundColorInserter.this.currentConfig.getColor().equals(
          BackgroundColorConfig.DEFAULT_COLOR)) {
        element.getStyle().clearBackgroundColor();
      } else {
        element.getStyle().setBackgroundColor(BackgroundColorInserter.this.currentConfig.getColor());
      }
    }

    @Override
    public SpanElement getEmptyElement() {
      return emptyElement;
    }
  }

  protected static class FontFamilyInvertAction extends InsertAction<Element> {
    private static final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
    }

    @Override
    public SpanElement getEmptyElement() {
      return emptyElement;
    }
  }

  public static class BackgroundColorConfig {
    public static final String DEFAULT_COLOR = "default";
    private String color;

    public BackgroundColorConfig(String color) {
      super();
      this.color = color;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }
  }

  public void insert(SurfaceSelection selection, BackgroundColorConfig config) {
    currentConfig = config;
    super.insert(selection);
  }

  @Deprecated
  @Override
  public void insert(SurfaceSelection selection) {
    throw new IllegalArgumentException("Use insert(Selection,FontColorConfig) instead");
  }

  @Override
  protected Element as(Element element) {
    return element;
  }

  @Override
  protected List<String> getApplicableTags() {
    return Arrays.asList("p", "span", "td", "div");
  }

  @Override
  protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection) {
    //TODO : à implanter en permettant de retourner la valeur de la couleur
    return false;
  }

}
