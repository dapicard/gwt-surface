package com.axeiya.gwt.surface.client.inserter.inlineinserter;

import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.ranges.Range;
import com.axeiya.gwt.surface.client.ranges.Selection;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

abstract public class InlineInserter<E extends Element> extends Inserter {

  protected InsertAction<E> action;
  protected InsertAction<E> invertAction;

  protected InlineInserter() {
  }

  protected InlineInserter(InsertAction<E> action, InsertAction<E> invertAction) {
    this.action = action;
    this.invertAction = invertAction;
  }

  public void setAction(InsertAction<E> action) {
    this.action = action;
  }

  public void setInvertAction(InsertAction<E> invertAction) {
    this.invertAction = invertAction;
  }

  private void insert(SurfaceSelection selection, final Node currentElement, boolean started,
      boolean finished, InsertAction<E> action) {
    Range range = selection.getSelection().getRange();
    final Node startNode = range.getStartContainer();
    Document document = startNode.getOwnerDocument();
    int startOffset = range.getStartOffset();
    final Node endNode = range.getEndContainer();
    int endOffset = range.getEndOffset();
    if (currentElement.getNodeType() == Node.TEXT_NODE) {
      if (currentElement.equals(startNode)) {
        String texte = startNode.getNodeValue();
        final String before = texte.substring(0, startOffset);
        String inner = texte.substring(startOffset);

        if (before.trim().isEmpty() && DOMUtil.isAloneChild(currentElement)
            && isApplicable(currentElement.getParentElement())) {
          action.doAction(as(currentElement.getParentElement()), selection);
        } else {
          final Element container = document.createElement(action.getEmptyElement().getTagName());
          container.appendChild(document.createTextNode(inner));

          Scheduler.get().scheduleFinally(new ScheduledCommand() {
            @Override
            public void execute() {
              startNode.setNodeValue(before);
              currentElement.getParentElement().insertAfter(container, startNode);
            }
          });
          action.doAction(as(container), selection);
        }
      } else if (currentElement.equals(endNode)) {
        String texte = endNode.getNodeValue();
        String inner = texte.substring(0, endOffset);
        final String after = texte.substring(endOffset);
        if (after.trim().isEmpty() && DOMUtil.isAloneChild(currentElement)
            && isApplicable(currentElement.getParentElement())) {
          action.doAction(as(currentElement.getParentElement()), selection);
        } else {
          final Element container = document.createElement(action.getEmptyElement().getTagName());
          container.appendChild(document.createTextNode(inner));
          Scheduler.get().scheduleFinally(new ScheduledCommand() {
            @Override
            public void execute() {
              endNode.setNodeValue(after);
              currentElement.getParentElement().insertBefore(container, endNode);
            }
          });
          action.doAction(as(container), selection);
        }
      } else if (started) {
        if (isApplicable(currentElement.getParentElement())
            && range.containsNode(currentElement.getParentNode(), false)) {
          action.doAction(as(currentElement.getParentElement()), selection);
        } else {
          final Element container = document.createElement(action.getEmptyElement().getTagName());
          Scheduler.get().scheduleFinally(new ScheduledCommand() {
            @Override
            public void execute() {
              currentElement.getParentElement().insertBefore(container, currentElement);
              currentElement.removeFromParent();
              container.appendChild(currentElement);
            }
          });
          action.doAction(as(container), selection);
        }
      }
    } else {
      Node nextSibling, child = currentElement.getFirstChild();
      boolean start = started, finish = false;
      while (child != null && !finish) {
        nextSibling = child.getNextSibling();
        if (!start) {
          start = range.containsNode(child, false);
        }
        if (start && !range.containsNode(child, false)) {
          finish = true;
        }
        if (start) {
          insert(selection, child, true, finish, action);
        } else if (!finish) {
          insert(selection, child, false, false, action);
          // start = true;
        }
        child = nextSibling;
      }
    }
  }

  protected void insert(SurfaceSelection selection, InsertAction<E> action) {
    Range range = selection.getSelection().getRange();

    final Node startNode = range.getStartContainer();
    int startOffset = range.getStartOffset();
    String startName = startNode.getNodeName();
    Node startParent = startNode.getParentNode();
    Node endNode = range.getEndContainer();
    int endOffset = range.getEndOffset();
    Node ancest = range.getCommonAncestorContainer();
    if (ancest.equals(startNode) || ancest.equals(endNode)) {
      ancest = startNode.getParentNode();
    }
    final Node ancestor = ancest;

    // La sélection est sur des noeuds textes contigues
    if (!startNode.equals(endNode) && startNode.getNodeType() == Node.TEXT_NODE
        && endNode.getNodeType() == Node.TEXT_NODE
        && startNode.getParentNode().equals(endNode.getParentNode())
        && DOMUtil.containsOnlyTextNodesBetweenNodes(startNode.getParentNode(), startNode, endNode)) {
      // Dans ce cas, on doit rassembler les noeuds texte avant traitement
      selection = DOMUtil.glueTextNodes(selection, startNode.getOwnerDocument());
      // et on relance
      insert(selection, action);
      return;
    }

    if (startNode == null || endNode == null) {
      // TODO : dans ce cas, on ne fait rien ?
      // ancestor.appendChild(container);
    } else if (startNode.equals(endNode)) {
      // Inline insertion
      // On vérifie si le parent direct ne contient que ce texte
      Element parent = startNode.getParentElement();
      String texte = startNode.getNodeValue();
      final String before = texte.substring(0, startOffset);
      String inner = texte.substring(startOffset, endOffset);
      final String after = texte.substring(endOffset);
      if (isApplicable(parent) && parent.getInnerText().trim().equals(inner.trim())) {
        // Dans ce cas, on applique au parent
        action.doAction(as(parent), selection);
      } else {
        // Sinon, on créé un noeud pour appliquer
        Element container =
            startNode.getOwnerDocument().createElement(action.getEmptyElement().getTagName());
        container.appendChild(startNode.getOwnerDocument().createTextNode(inner));

        final Node insertNode = container;
        Scheduler.get().scheduleFinally(new ScheduledCommand() {
          @Override
          public void execute() {
            startNode.setNodeValue(before);
            ancestor.insertAfter(insertNode, startNode);
            Node afterNode = startNode.getOwnerDocument().createTextNode(after);
            ancestor.insertAfter(afterNode, insertNode);
          }
        });

        action.doAction(as(container), selection);
      }
    } else {
      insert(selection, ancestor, false, false, action);
    }
  }

  public void insert(SurfaceSelection selection) {
    insert(selection, action);
  }

  public void remove(SurfaceSelection selection) {
    insert(selection, invertAction);
  }

  private boolean check(SurfaceSelection selection, Node currentElement, boolean started, boolean finished) {

    Range range = selection.getSelection().getRange();
    Node startNode = range.getStartContainer();
    int startOffset = range.getStartOffset();
    Node endNode = range.getEndContainer();
    int endOffset = range.getEndOffset();
    Node nextSibling, child = currentElement.getFirstChild();
    boolean start = started, finish = false, assignable = true, hasChild = false;
    while (child != null && !finish) {
      hasChild = true;
      nextSibling = child.getNextSibling();
      if (start && !range.containsNode(child, false)) {
        finish = true;
      }
      if (start && !finish) {
        if (child.getNodeType() != Node.TEXT_NODE) {
          assignable = assignable && check(selection, child, true, finish);
        } else {
          if (child.equals(endNode) && endOffset == 0) {
            // Dans ce cas, ce noeud est "sélectionné", mais pour aucun caractère (fréquent dans
            // FF), donc on l'ignore
          } else {

            assignable =
                assignable
                    && ((child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().isEmpty()) || adjustSelectionAssignee(
                        child.getNodeType() == Node.TEXT_NODE ? (Element) child.getParentElement()
                            : (Element) child, selection));
          }
        }
      } else if (range.containsNode(child, true)) {
        if (child.equals(startNode) && child.getNodeType() == Node.TEXT_NODE
            && startOffset == startNode.getNodeValue().length()) {
          // Dans ce cas, ce noeud est "sélectionné", mais pour aucun caractère (fréquent dans FF),
          // donc on l'ignore
        } else if (child.equals(endNode) && endOffset == 0) {
          // Dans ce cas, noeud sélectionné pour aucun caractère, donc on ignore
        } else {
          assignable =
              assignable
                  && (adjustSelectionAssignee(child.getNodeType() == Node.TEXT_NODE
                      ? (Element) child.getParentElement() : (Element) child, selection) || check(
                      selection, child, false, finish));
        }
        start = true;
      }
      child = nextSibling;
    }
    return (!hasChild ? adjustSelectionAssignee((Element) currentElement, selection) : true)
        && assignable;
  }

  @Override
  public boolean isSelectionAssignee(SurfaceSelection selection) {
    Range range = selection.getSelection().getRange();
    Node startNode = range.getStartContainer();
    Node endNode = range.getEndContainer();
    Element ancestor = (Element) range.getCommonAncestorContainer();
    if(ancestor == null) {
      return false;
    }
    if (ancestor.equals(startNode) || ancestor.equals(endNode)) {
      ancestor = startNode.getParentElement();
    }

    // La sélection est sur des noeuds textes contigues
    if (!startNode.equals(endNode) && startNode.getNodeType() == Node.TEXT_NODE
        && endNode.getNodeType() == Node.TEXT_NODE
        && startNode.getParentNode().equals(endNode.getParentNode())
        && DOMUtil.containsOnlyTextNodesBetweenNodes(startNode.getParentNode(), startNode, endNode)) {
      // Dans ce cas, on doit rassembler les noeuds texte avant traitement
      selection = DOMUtil.glueTextNodes(selection, startNode.getOwnerDocument());
      // et on relance
      return isSelectionAssignee(selection);
    }

    if (startNode == null || endNode == null) {
      return false;
    } else if (startNode.equals(endNode)) {
      Element parent = startNode.getParentElement();
      return adjustSelectionAssignee(parent, selection);
    } else {
      return check(selection, ancestor, false, false);
    }
  }

  protected abstract E as(Element element);

  /**
   * Indique si l'action est applicable sur l'élément
   * 
   * @param element
   * @return
   */
  protected boolean isApplicable(Element element) {
    String tagName = element.getTagName().toLowerCase();
    return getApplicableTags().contains(tagName);
  }

  /**
   * @return Liste des tags sur lequel l'inserter est applicable
   */
  abstract protected List<String> getApplicableTags();
}
