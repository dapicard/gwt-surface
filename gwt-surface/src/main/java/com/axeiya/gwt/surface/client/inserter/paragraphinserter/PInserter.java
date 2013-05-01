package com.axeiya.gwt.surface.client.inserter.paragraphinserter;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.Text;
import com.axeiya.gwt.surface.client.event.enterkeypressed.EnterKeyPressedEvent;
import com.axeiya.gwt.surface.client.event.enterkeypressed.EnterKeyPressedHandler;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.ranges.Range;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class PInserter extends ParagraphInserter<ParagraphElement> implements
    EnterKeyPressedHandler {
  public static final List<String> PARAGRAPH_TAGS = Arrays.asList("p", "h1", "h2", "h3", "h4",
      "h5", "h6", "table");
  public static final Element DEFAULT_ELEMENT = Document.get().createPElement();

  protected static final PInsertAction insertAction = new PInsertAction();

  protected static class PInsertAction extends InsertAction<ParagraphElement> {
    private static final ParagraphElement emptyElement = Document.get().createPElement();

    @Override
    public void onAction(ParagraphElement element, SurfaceSelection selection) {
      if (!DOMUtil.hasStrongNode(element)) {
        element.appendChild(DOMUtil.createFocusBr());
      }
    }

    @Override
    public ParagraphElement getEmptyElement() {
      return emptyElement;
    }
  }

  public PInserter() {
    this(insertAction);
  }

  protected PInserter(InsertAction<ParagraphElement> action) {
    super(action);
  }

  @Override
  protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection) {
    return true;
  }

  @Override
  protected List<String> getTagCollection() {
    return PARAGRAPH_TAGS;
  }

  @Override
  protected Element getDefaultElement() {
    return DEFAULT_ELEMENT;
  }

  @Override
  protected ParagraphElement as(Element element) {
    return ParagraphElement.as(element);
  }

  @Override
  public void onEnterKeyPressed(EnterKeyPressedEvent event) {
    Range range = event.getSelection().getSelection().getRange();
    Node startNode = range.getStartContainer();
    Element ancestor = getCommonMatchingAncestor(event.getSelection());
    // Si le curseur est à la fin du noeud et que l'enter n'a pas encore été intercepté
    if (!event.isHandled() && ancestor != null && DOMUtil.atEnd(ancestor, range)) {
      // On est dans un type paragraphe
      if (!event.isCtrlKey()) {
        Element newParagraph = createEmptyParagraph();
        ancestor.getParentElement().insertAfter(newParagraph, ancestor);

        // this.action.doAction(as(newParagraph), event.getSelection());
        range.setStart(newParagraph, 0);
        range.setEnd(newParagraph, 0);
        event.getSelection().getAssociatedSurface().setSelection(range);
      } else {
        Element br;
        if (ancestor.getLastChild().getNodeType() == Node.ELEMENT_NODE
            && ancestor.getLastChild().getNodeName().equalsIgnoreCase("br")) {
          br = (Element) ancestor.getLastChild();
        } else {
          br = startNode.getOwnerDocument().createBRElement();
          ancestor.appendChild(br);
        }
        Text emptyText = startNode.getOwnerDocument().createTextNode("");
        ancestor.appendChild(emptyText);
        ancestor.appendChild(br.cloneNode(false));
        range.setStart(emptyText, 0);
        range.setEnd(emptyText, 0);
        event.getSelection().getAssociatedSurface().setSelection(range);
      }
      event.setHandled(true);
      event.setPreventDefault(true);
    }
  }

  public static Element createEmptyParagraph() {
    Element newParagraph = Document.get().createElement(DEFAULT_ELEMENT.getTagName());
    newParagraph.insertFirst(DOMUtil.createFocusBr());
    return newParagraph;
  }
}
