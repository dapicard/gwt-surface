package com.axeiya.gwt.surface.client.inserter.listinserter;

import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Text;
import com.google.gwt.dom.client.UListElement;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.ranges.Range;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public abstract class ListInsertAction<E extends Element> extends InsertAction<E> {

  abstract protected List<String> getTagCollection();

  @Override
  public void onAction(E element, SurfaceSelection selection) {
    // Dans le cas de liste imbriquées, on vérifie que l'élément ul/ol n'est pas dans un li
    Element parent = DOMUtil.getFirstAncestorOfType(element, "li");
    if (parent != null) {
      // auquel cas, on supprime ce li, et on rattache à la liste parente
      Element parentList = parent.getParentElement();
      if (parentList != null) {
        parentList.insertAfter(element, parent);
        DOMUtil.cleanBranch(parent);
      }
    }
    // On insert un li entre deux
    Element li = Document.get().createLIElement();
    moveContent(element, li);
    element.appendChild(li);
    // on essaye de recoler les ul adjacents
    Node child, nextSibling, n = DOMUtil.getNextStrongSibling(element);
    if (n != null && n.getNodeType() == Node.ELEMENT_NODE
        && n.getNodeName().equals(element.getTagName())) {
      // Le suivant est de même type
      UListElement next = (UListElement) n;
      child = next.getFirstChild();
      while (child != null) {
        nextSibling = child.getNextSibling();
        child.removeFromParent();
        element.appendChild(child);
        child = nextSibling;
      }
      next.removeFromParent();
    }
    n = DOMUtil.getPreviousStrongSibling(element);
    if (n != null && n.getNodeType() == Node.ELEMENT_NODE
        && n.getNodeName().equals(element.getTagName())) {
      // Le précédent est de même type
      UListElement previous = (UListElement) n;
      child = element.getFirstChild();
      while (child != null) {
        nextSibling = child.getNextSibling();
        child.removeFromParent();
        previous.appendChild(child);
        child = nextSibling;
      }
      element.removeFromParent();
    }
    if (!DOMUtil.hasStrongNode(li)) {
      Text text = Document.get().createTextNode(" ");
      li.appendChild(text);
      Range range = selection.getSelection().getRange();
      range.setStart(text, 0);
      range.setEnd(text, 0);
      selection.getAssociatedSurface().setSelection(range);
    }
  }

  @Override
  public void onRevert(Element element, SurfaceSelection selection) {
    // on supprime les li si on n'est plus dans une liste
    if (element.getParentElement() == null
        || !getTagCollection().contains(element.getParentElement().getTagName().toLowerCase())) {
      if (element.getTagName().equalsIgnoreCase("li")) {
        Element parent = element.getParentElement();
        Node nextSibling, child = element.getFirstChild();
        while (child != null) {
          nextSibling = child.getNextSibling();
          child.removeFromParent();
          parent.insertBefore(child, element);
          child = nextSibling;
        }
        element.removeFromParent();
      }
    }
  }

  protected void moveContent(Element from, Element to) {
    Node nextSibling, child = from.getFirstChild();
    while (child != null) {
      nextSibling = child.getNextSibling();
      child.removeFromParent();
      // On supprime les P
      if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equalsIgnoreCase("p")) {
        moveContent((Element) child, to);
      } else {
        to.appendChild(child);
      }
      child = nextSibling;
    }
  }

}
