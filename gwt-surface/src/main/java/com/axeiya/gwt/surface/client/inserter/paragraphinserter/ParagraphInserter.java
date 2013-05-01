package com.axeiya.gwt.surface.client.inserter.paragraphinserter;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.blockinserter.BlockInserter;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public abstract class ParagraphInserter<E extends Element> extends Inserter {

  protected InsertAction<E> action;
  protected LocalBlockInserter blockInserter;

  protected class LocalBlockInserter extends BlockInserter<E> {

    protected LocalBlockInserter(InsertAction<E> action) {
      super(action);
    }

    @Override
    protected E as(Element element) {
      return ParagraphInserter.this.as(element);
    }

  }

  protected ParagraphInserter() {
    super();
  }

  protected ParagraphInserter(InsertAction<E> action) {
    setAction(action);
  }

  protected void setAction(InsertAction<E> action) {
    this.action = action;
    blockInserter = new LocalBlockInserter(action);
  }

  @Override
  public void insert(SurfaceSelection selection) {
    Element ancestor = getCommonMatchingAncestor(selection);
    if (ancestor != null && ancestor.getParentElement() != null) {
      Element base = (Element) action.getEmptyElement().cloneNode(false);
      // On rattache les enfants de ancestor à son remplaçant
      Node nextSibling, child = ancestor.getFirstChild();
      while (child != null) {
        nextSibling = child.getNextSibling();
        child.removeFromParent();
        base.appendChild(child);
        child = nextSibling;
      }
      ancestor.getParentElement().insertBefore(base, ancestor);
      ancestor.removeFromParent();
      action.doAction(as(base), selection);
    } else {
      // On insert un élément autour de la sélection
      blockInserter.insert(selection);
    }
  }

  @Override
  public void remove(SurfaceSelection selection) {
    Element ancestor = getCommonMatchingAncestor(selection);
    if (ancestor != null && ancestor.getParentElement() != null
        && !ancestor.getTagName().equals(getDefaultElement().getTagName())) {
      Element base = (Element) getDefaultElement().cloneNode(false);
      // On rattache les enfants de ancestor à son remplaçant
      Node nextSibling, child = ancestor.getFirstChild();
      while (child != null) {
        nextSibling = child.getNextSibling();
        child.removeFromParent();
        base.appendChild(child);
        child = nextSibling;
      }
      ancestor.getParentElement().insertBefore(base, ancestor);
      ancestor.removeFromParent();
    }
  }

  @Override
  public boolean isSelectionAssignee(SurfaceSelection selection) {
    Element ancestor = getElementMatchingAncestor(selection);
    if (ancestor != null) {
      return adjustSelectionAssignee(ancestor, selection);
    }
    return false;
  }

  @Override
  abstract protected boolean adjustSelectionAssignee(Element matchingAncestor,
      SurfaceSelection selection);

  abstract protected List<String> getTagCollection();

  abstract protected Element getDefaultElement();

  protected abstract E as(Element element);

  protected Element getCommonMatchingAncestor(SurfaceSelection selection) {
    Element ancestor = (Element) selection.getSelection().getRange().getCommonAncestorContainer();
    return DOMUtil.getFirstAncestorInTypes(ancestor, getTagCollection());
  }

  protected Element getElementMatchingAncestor(SurfaceSelection selection) {
    Element ancestor = (Element) selection.getSelection().getRange().getCommonAncestorContainer();
    return DOMUtil.getFirstAncestorOfType(ancestor, action.getEmptyElement().getTagName());
  }

}
