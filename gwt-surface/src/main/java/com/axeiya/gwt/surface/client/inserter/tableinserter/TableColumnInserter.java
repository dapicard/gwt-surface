package com.axeiya.gwt.surface.client.inserter.tableinserter;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.ranges.Selection;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class TableColumnInserter extends Inserter {

  @Override
  public void insert(SurfaceSelection surfaceSelection) {
    Selection selection = surfaceSelection.getSelection();
    // on détermine à quel endroit on doit insérer la colonne
    Node endNode = selection.getRange().getEndContainer();
    Node actualCell = DOMUtil.getFirstAncestorInTypes(endNode, TableInserter.CELL_TAGS);
    int index = getColumnIndex(actualCell);
    // maintenant qu'on a l'index, on insère dans chaque ligne une colonne
    TableElement table =
        (TableElement) DOMUtil.getFirstAncestorOfType(actualCell, TableInserter.TABLE_TAG);
    if (table != null) {
      // Deux cas possibles : soit on a des sections, soit directement les tr
      Element child = table.getFirstChildElement();
      while (child != null) {
        if (child.getTagName().toLowerCase().equals(TableInserter.LINE_TAG)) {
          insertCell((TableRowElement) child, index);
        } else if (TableInserter.SECTION_TAGS.contains(child.getTagName().toLowerCase())) {
          Element row = child.getFirstChildElement();
          while (row != null) {
            if (row.getTagName().toLowerCase().equals(TableInserter.LINE_TAG)) {
              insertCell((TableRowElement) row, index);
            }
            row = row.getNextSiblingElement();
          }
        }
        child = child.getNextSiblingElement();
      }
    }
  }

  protected void insertCell(TableRowElement row, int column) {
    int index = 0;
    TableCellElement cell = (TableCellElement) row.getFirstChildElement();
    while (cell != null && index <= column) {
      index += cell.getColSpan();
      if (index > column) {
        // on insère juste avant (ou après) cette cellule
        TableCellElement newCell = (TableCellElement) cell.cloneNode(false);
        newCell.setColSpan(1);
        row.insertBefore(newCell, cell);
      }
      cell = (TableCellElement) cell.getNextSiblingElement();
    }

  }

  protected int getColumnIndex(Node actualCell) {
    // On cherche à connaitre l'index de cette cellule
    Element actualTr = actualCell.getParentElement();
    Element child = actualTr.getFirstChildElement();
    int index = 0;
    while (child != null && !child.equals(actualCell)) {
      if (TableInserter.CELL_TAGS.contains(child.getTagName().toLowerCase())) {
        TableCellElement td = (TableCellElement) child;
        index += td.getColSpan();
      }
      child = child.getNextSiblingElement();
    }
    return index;
  }

  @Override
  public void remove(SurfaceSelection surfaceSelection) {
    Selection selection = surfaceSelection.getSelection();
    Node endNode = selection.getRange().getEndContainer();
    Node actualCell = DOMUtil.getFirstAncestorInTypes(endNode, TableInserter.CELL_TAGS);
    int index = getColumnIndex(actualCell);
    TableElement table =
        (TableElement) DOMUtil.getFirstAncestorOfType(actualCell, TableInserter.TABLE_TAG);
    if (table != null) {
      // Deux cas possibles : soit on a des sections, soit directement les tr
      Element child = table.getFirstChildElement();
      while (child != null) {
        if (child.getTagName().toLowerCase().equals(TableInserter.LINE_TAG)) {
          dropCell((TableRowElement) child, index);
        } else if (TableInserter.SECTION_TAGS.contains(child.getTagName().toLowerCase())) {
          Element row = child.getFirstChildElement();
          while (row != null) {
            if (row.getTagName().toLowerCase().equals(TableInserter.LINE_TAG)) {
              dropCell((TableRowElement) row, index);
            }
            row = row.getNextSiblingElement();
          }
        }
        child = child.getNextSiblingElement();
      }
    }
  }

  protected void dropCell(TableRowElement row, int column) {
    int index = -1;
    TableCellElement cell = (TableCellElement) row.getFirstChildElement();
    while (cell != null && index < column) {
      index += cell.getColSpan();
      if (index == column) {
        cell.removeFromParent();
      } else if (index > column) {
        // on enlève un colspan
        cell.setColSpan(cell.getColSpan() - 1);
      } else {
        cell = (TableCellElement) cell.getNextSiblingElement();
      }
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
