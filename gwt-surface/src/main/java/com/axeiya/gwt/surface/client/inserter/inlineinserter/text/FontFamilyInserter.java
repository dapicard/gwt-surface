package com.axeiya.gwt.surface.client.inserter.inlineinserter.text;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.InlineInserter;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class FontFamilyInserter extends InlineInserter<Element> {
  public static final String FONTFAMILY_PROPERTYNAME = "fontFamily";

  private FontFamilyConfig currentConfig;

  public FontFamilyInserter(FontFamilyConfig config) {
    action = new FontFamilyInsertAction();
    invertAction = new FontFamilyInvertAction();
    currentConfig = config;
  }

  protected FontFamilyInserter(InsertAction<Element> action, InsertAction<Element> invertAction,
      FontFamilyConfig config) {
    super(action, invertAction);
    currentConfig = config;
  }

  protected class FontFamilyInsertAction extends InsertAction<Element> {
    private final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
      if (FontFamilyInserter.this.currentConfig.getFontName().equals(
          FontFamilyConfig.DEFAULT_FONTNAME)) {
        element.getStyle().clearProperty(FONTFAMILY_PROPERTYNAME);
      } else {
        element.getStyle().setProperty(FONTFAMILY_PROPERTYNAME,
            FontFamilyInserter.this.currentConfig.getFontName());
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

  public static class FontFamilyConfig {
    public static final String DEFAULT_FONTNAME = "default";
    private String fontName;

    public FontFamilyConfig(String fontName) {
      super();
      this.fontName = fontName;
    }

    public String getFontName() {
      return fontName;
    }

    public void setFontName(String fontName) {
      this.fontName = fontName;
    }
  }

  @Override
  public void insert(SurfaceSelection selection) {
    super.insert(selection);
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
    return matchingAncestor.getStyle().getProperty(FONTFAMILY_PROPERTYNAME) != null
        && matchingAncestor.getStyle().getProperty(FONTFAMILY_PROPERTYNAME).equals(
            currentConfig.getFontName());
  }

}
