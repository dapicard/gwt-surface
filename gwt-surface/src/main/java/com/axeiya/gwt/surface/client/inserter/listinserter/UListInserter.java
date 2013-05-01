package com.axeiya.gwt.surface.client.inserter.listinserter;

import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.UListElement;
import com.axeiya.gwt.surface.client.event.hotkeypressed.HotKeyPressedEvent;
import com.axeiya.gwt.surface.client.event.hotkeypressed.HotKeyPressedHandler;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class UListInserter extends ListInserter<UListElement> implements HotKeyPressedHandler {
  public static final String TAG_NAME = "ul";
  protected static final UListInsertAction insertAction = new UListInsertAction();

  protected static class UListInsertAction extends ListInsertAction<UListElement> {
    private static final UListElement emptyElement = Document.get().createULElement();

    @Override
    public UListElement getEmptyElement() {
      return emptyElement;
    }

    @Override
    protected List<String> getTagCollection() {
      return OListInserter.TAGS_LIST;
    }
  }

  public UListInserter() {
    this(insertAction);
  }

  protected UListInserter(InsertAction<UListElement> action) {
    super(action);
  }

  @Override
  protected UListElement as(Element element) {
    return UListElement.as(element);
  }

  @Override
  protected List<String> getTagCollection() {
    return OListInserter.TAGS_LIST;
  }

  @Override
  public void onHotKeyPressed(HotKeyPressedEvent event) {
    if (event.isCtrlKey() && event.getKeyCode() == 76) {
      // Ctrl+l
      SurfaceSelection selection = event.getSelection();
      if (isSelectionAssignee(selection)) {
        remove(selection);
      } else {
        insert(selection);
      }
      event.setHandled(true);
      event.setPreventDefault(true);
    }
  }

}