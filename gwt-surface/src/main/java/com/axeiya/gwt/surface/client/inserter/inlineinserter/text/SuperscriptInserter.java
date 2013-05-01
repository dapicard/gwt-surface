package com.axeiya.gwt.surface.client.inserter.inlineinserter.text;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.InlineInserter;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class SuperscriptInserter extends InlineInserter<Element> {

  protected static final SuperscriptInsertAction insertAction = new SuperscriptInsertAction();
  protected static final SuperscriptInvertAction invertAction = new SuperscriptInvertAction();

  public SuperscriptInserter() {
    this(insertAction, invertAction);
  }

  protected SuperscriptInserter(InsertAction<Element> action, InsertAction<Element> invertAction) {
    super(action, invertAction);
  }

  protected static class SuperscriptInsertAction extends InsertAction<Element> {
    private static final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
      element.getStyle().setVerticalAlign(VerticalAlign.BASELINE);
      element.getStyle().setFontSize(0.8, Unit.EM);
      element.getStyle().setPosition(Position.RELATIVE);
      element.getStyle().setTop(-0.4, Unit.EM);
    }

    @Override
    public SpanElement getEmptyElement() {
      return emptyElement;
    }
  }

  protected static class SuperscriptInvertAction extends InsertAction<Element> {
    private static final SpanElement emptyElement = Document.get().createSpanElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
      // TODO : La suppression de ceux-ci est probablement assez spécifique
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
    String top = "";
    while ((top == null || top.isEmpty()) && matchingAncestor != null) {
      top = matchingAncestor.getStyle().getTop();
      if ("-0.4em".equals(top)) {
        return true;
      }
      matchingAncestor = matchingAncestor.getParentElement();
    }
    return false;
  }

}
