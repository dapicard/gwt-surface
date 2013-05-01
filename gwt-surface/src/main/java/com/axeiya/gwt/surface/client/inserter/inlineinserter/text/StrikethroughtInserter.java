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

public class StrikethroughtInserter extends InlineInserter<Element> {

  protected static final StrikethroughtInsertAction insertAction = new StrikethroughtInsertAction();
  protected static final StrikethroughtInvertAction invertAction = new StrikethroughtInvertAction();

  public StrikethroughtInserter() {
    this(insertAction, invertAction);
  }

  protected StrikethroughtInserter(InsertAction<Element> action, InsertAction<Element> invertAction) {
    super(action, invertAction);
  }

  protected static class StrikethroughtInsertAction extends InsertAction<Element> {
    private static final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
      element.getStyle().setTextDecoration(TextDecoration.LINE_THROUGH);
    }

    @Override
    public SpanElement getEmptyElement() {
      return emptyElement;
    }
  }

  protected static class StrikethroughtInvertAction extends InsertAction<Element> {
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
  protected SpanElement as(Element element) {
    return SpanElement.as(element);
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
      if (TextDecoration.LINE_THROUGH.getCssName().toLowerCase().equals(textDecoration)) {
        return true;
      }
      matchingAncestor = matchingAncestor.getParentElement();
    }
    return false;
  }

}
