package com.axeiya.gwt.surface.client.inserter.indentinserter;

import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.Style.Unit;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.listinserter.OListInserter;
import com.axeiya.gwt.surface.client.inserter.listinserter.UListInserter;
import com.axeiya.gwt.surface.client.inserter.paragraphinserter.PInserter;
import com.axeiya.gwt.surface.client.inserter.paragraphinserter.ParagraphInserter;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class IndentInserter extends ParagraphInserter<Element> {

  public static final int OFFSET = 40;

  protected static final IndentInsertAction insertAction = new IndentInsertAction();

  protected static class IndentInsertAction extends InsertAction<Element> {
    private static final Element emptyElement = Document.get().createPElement();

    @Override
    public void onAction(Element element, SurfaceSelection selection) {
      int marginLeft;
      try {
        marginLeft = Integer.parseInt(element.getStyle().getMarginLeft().replaceAll("px", ""));
      } catch (NumberFormatException nfe) {
        marginLeft = 0;
      }
      marginLeft += OFFSET;
      element.getStyle().setMarginLeft(marginLeft, Unit.PX);
    }

    @Override
    public Element getEmptyElement() {
      return emptyElement;
    }
  }

  private UListInserter uListInserter;
  private OListInserter oListInserter;

  public IndentInserter() {
    this(insertAction);
  }

  protected IndentInserter(InsertAction<Element> action) {
    super(action);
    uListInserter = new UListInserter();
    oListInserter = new OListInserter();
  }

  @Override
  public void insert(SurfaceSelection selection) {
    Element listAncestor =
        DOMUtil.getFirstAncestorInTypes(selection.getSelection().getRange()
            .getCommonAncestorContainer(), OListInserter.TAGS_LIST);
    if (listAncestor != null) {
      // Contexte de liste
      if (listAncestor.getTagName().equalsIgnoreCase(UListInserter.TAG_NAME)) {
        uListInserter.insert(selection);
      } else if (listAncestor.getTagName().equalsIgnoreCase(OListInserter.TAG_NAME)) {
        oListInserter.insert(selection);
      }
    } else {
      // contexte de paragraphe
      Element ancestor = getCommonMatchingAncestor(selection);
      if (ancestor != null && ancestor.getParentElement() != null) {
        action.doAction(ancestor, selection);
      } else {
        // On insert un élément autour de la sélection
        blockInserter.insert(selection);
      }
    }
  }

  @Override
  protected ParagraphElement as(Element element) {
    return ParagraphElement.as(element);
  }

  @Override
  protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection) {
    return true;
  }

  @Override
  protected List<String> getTagCollection() {
    return PInserter.PARAGRAPH_TAGS;
  }

  @Override
  protected Element getDefaultElement() {
    return PInserter.DEFAULT_ELEMENT;
  }

}
