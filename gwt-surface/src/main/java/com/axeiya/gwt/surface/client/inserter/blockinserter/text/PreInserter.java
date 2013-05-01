package com.axeiya.gwt.surface.client.inserter.blockinserter.text;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.PreElement;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.blockinserter.BlockInserter;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class PreInserter extends BlockInserter<PreElement> {

  protected static final PreInsertAction insertAction = new PreInsertAction();

  public PreInserter() {
    this(insertAction);
  }

  protected PreInserter(InsertAction<PreElement> action) {
    super(action);
  }

  protected static class PreInsertAction extends InsertAction<PreElement> {
    private static final PreElement emptyElement = Document.get().createPreElement();

    @Override
    public void onAction(PreElement element, SurfaceSelection selection) {
      if (!DOMUtil.hasStrongNode(element)) {
        element.appendChild(DOMUtil.createFocusBr());
      }
    }

    @Override
    public PreElement getEmptyElement() {
      return emptyElement;
    }
  }

  @Override
  protected PreElement as(Element element) {
    return PreElement.as(element);
  }
}