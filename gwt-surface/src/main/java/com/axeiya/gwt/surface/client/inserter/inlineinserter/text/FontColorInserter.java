package com.axeiya.gwt.surface.client.inserter.inlineinserter.text;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.InlineInserter;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class FontColorInserter extends InlineInserter<Element> {

  private FontColorConfig currentConfig;

  public FontColorInserter() {
    action = new FontColorInsertAction();
    invertAction = new FontColorInvertAction();
  }

  protected FontColorInserter(InsertAction<Element> action, InsertAction<Element> invertAction) {
    super(action, invertAction);
  }

  protected class FontColorInsertAction extends InsertAction<Element> {
    private final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
      if (FontColorInserter.this.currentConfig.getColor().equals(FontColorConfig.DEFAULT_COLOR)) {
        element.getStyle().clearColor();
      } else {
        element.getStyle().setColor(FontColorInserter.this.currentConfig.getColor());
      }
    }

    @Override
    public SpanElement getEmptyElement() {
      return emptyElement;
    }
  }

  protected static class FontColorInvertAction extends InsertAction<Element> {
    private static final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
    }

    @Override
    public SpanElement getEmptyElement() {
      return emptyElement;
    }
  }

  public static class FontColorConfig {
    public static final String DEFAULT_COLOR = "default";
    private String color;

    public FontColorConfig(String color) {
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

  public void insert(SurfaceSelection selection, FontColorConfig config) {
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
    return Arrays.asList("p", "span");
  }

  @Override
  protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection) {
    // TODO : à implanter en permettant de retourner la valeur de la couleur
    return false;
  }

}
