package com.axeiya.gwt.surface.client.inserter.inlineinserter.text;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.InlineInserter;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class UnderlineInserter extends InlineInserter<Element> {

  protected static final UnderlineInsertAction insertAction = new UnderlineInsertAction();
  protected static final UnderlineInvertAction invertAction = new UnderlineInvertAction();

  public UnderlineInserter() {
    this(insertAction, invertAction);
  }

  protected UnderlineInserter(InsertAction<Element> action, InsertAction<Element> invertAction) {
    super(action, invertAction);
  }

  protected static class UnderlineInsertAction extends InsertAction<Element> {
    private static final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
      element.getStyle().setTextDecoration(TextDecoration.UNDERLINE);
    }

    @Override
    public SpanElement getEmptyElement() {
      return emptyElement;
    }
  }

  protected static class UnderlineInvertAction extends InsertAction<Element> {
    private static final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
      element.getStyle().setTextDecoration(TextDecoration.NONE);
    }

    @Override
    public SpanElement getEmptyElement() {
      return emptyElement;
    }
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
    String textDecoration = "";
    while ((textDecoration == null || textDecoration.isEmpty()) && matchingAncestor != null) {
      textDecoration = matchingAncestor.getStyle().getTextDecoration();
      if (TextDecoration.UNDERLINE.toString().toLowerCase().equals(textDecoration)) {
        return true;
      }
      matchingAncestor = matchingAncestor.getParentElement();
    }
    return false;
  }

}
