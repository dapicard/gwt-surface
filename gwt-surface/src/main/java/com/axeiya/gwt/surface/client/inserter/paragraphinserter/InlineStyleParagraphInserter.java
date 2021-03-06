package com.axeiya.gwt.surface.client.inserter.paragraphinserter;

import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ParagraphElement;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class InlineStyleParagraphInserter extends ParagraphInserter<ParagraphElement> {

  protected static class StylePInsertAction extends InsertAction<ParagraphElement> {
    private static final ParagraphElement emptyElement = Document.get().createPElement();

    private String propertyName;
    private String propertyValue;

    public StylePInsertAction(String propertyName, String propertyValue) {
      this.propertyName = propertyName;
      this.propertyValue = propertyValue;
    }

    @Override
    public void onAction(ParagraphElement element, SurfaceSelection selection) {
      element.getStyle().setProperty(propertyName, propertyValue);
      if (!DOMUtil.hasStrongNode(element)) {
        element.appendChild(DOMUtil.createFocusBr());
      }
    }

    @Override
    public ParagraphElement getEmptyElement() {
      return emptyElement;
    }
  }

  public InlineStyleParagraphInserter(String propertyName, String propertyValue) {
    super(new StylePInsertAction(propertyName, propertyValue));
  }

  @Override
  protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection) {
    return matchingAncestor.getStyle().getProperty(((StylePInsertAction) action).propertyName) != null
        && matchingAncestor.getStyle().getProperty(((StylePInsertAction) action).propertyName)
            .equals(((StylePInsertAction) action).propertyValue);
  }

  @Override
  protected List<String> getTagCollection() {
    return PInserter.PARAGRAPH_TAGS;
  }

  @Override
  protected Element getDefaultElement() {
    return PInserter.DEFAULT_ELEMENT;
  }

  @Override
  protected ParagraphElement as(Element element) {
    return ParagraphElement.as(element);
  }

  public static Element createEmptyParagraph() {
    Element newParagraph = Document.get().createElement(PInserter.DEFAULT_ELEMENT.getTagName());
    newParagraph.insertFirst(DOMUtil.createFocusBr());
    return newParagraph;
  }

}
