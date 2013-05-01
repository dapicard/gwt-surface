package com.axeiya.gwt.surface.client.inserter.blockinserter.text;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.blockinserter.BlockInserter;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class DivInserter extends BlockInserter<DivElement> {

  protected static final DivInsertAction insertAction = new DivInsertAction();

  public DivInserter() {
    this(insertAction);
  }

  protected DivInserter(InsertAction<DivElement> action) {
    super(action);
  }

  protected static class DivInsertAction extends InsertAction<DivElement> {
    private static final DivElement emptyElement = Document.get().createDivElement();

    @Override
    public void onAction(DivElement element, SurfaceSelection selection) {
      if (!DOMUtil.hasStrongNode(element)) {
        element.appendChild(DOMUtil.createFocusBr());
      }
    }

    @Override
    public DivElement getEmptyElement() {
      return emptyElement;
    }
  }

  @Override
  protected DivElement as(Element element) {
    return DivElement.as(element);
  }

}