package com.axeiya.gwt.surface.client.inserter.blockinserter.text;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.QuoteElement;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.blockinserter.BlockInserter;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class QuoteInserter extends BlockInserter<QuoteElement> {

  protected static final QuoteInsertAction insertAction = new QuoteInsertAction();

  public QuoteInserter() {
    this(insertAction);
  }

  protected QuoteInserter(InsertAction<QuoteElement> action) {
    super(action);
  }

  protected static class QuoteInsertAction extends InsertAction<QuoteElement> {
    private static final QuoteElement emptyElement = Document.get().createBlockQuoteElement();

    @Override
    public void onAction(QuoteElement element, SurfaceSelection selection) {
      if (!DOMUtil.hasStrongNode(element)) {
        element.appendChild(DOMUtil.createFocusBr());
      }
    }

    @Override
    public QuoteElement getEmptyElement() {
      return emptyElement;
    }
  }

  @Override
  protected QuoteElement as(Element element) {
    return QuoteElement.as(element);
  }

}
