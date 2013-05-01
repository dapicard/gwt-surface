package com.axeiya.gwt.surface.client.inserter.tableinserter;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.ranges.Selection;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class TableLineInserter extends Inserter {

  @Override
  public void insert(SurfaceSelection selection) {
    Node ancestor = selection.getSelection().getRange().getCommonAncestorContainer();

    // Ensuite on cherche à trouver le tr le plus proche
    Node endNode = selection.getSelection().getRange().getEndContainer();
    Node tr = DOMUtil.getFirstAncestorOfType(endNode, TableInserter.LINE_TAG);
    int columnCount = tr.getChildCount();
    Node newLine = tr.cloneNode(false);
    for (int i = 0; i < columnCount; i++) {
      Node td = tr.getChild(i).cloneNode(false);
      td.appendChild(DOMUtil.createFocusBr());
      newLine.appendChild(td);
    }
    tr.getParentElement().insertAfter(newLine, tr);
  }

  @Override
  public void remove(SurfaceSelection selection) {
    Node endNode = selection.getSelection().getRange().getEndContainer();
    Node tr = DOMUtil.getFirstAncestorOfType(endNode, TableInserter.LINE_TAG);
    if (tr != null) {
      tr.removeFromParent();
    }
  }

  @Override
  public boolean isSelectionAssignee(SurfaceSelection selection) {
    Node ancestor =
        DOMUtil.getFirstAncestorOfType(selection.getSelection().getRange().getStartContainer(),
            TableInserter.TABLE_TAG);
    return ancestor != null;
  }

  @Override
  protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection) {
    return true;
  }

}
