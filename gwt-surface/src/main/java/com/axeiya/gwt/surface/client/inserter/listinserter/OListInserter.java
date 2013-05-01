package com.axeiya.gwt.surface.client.inserter.listinserter;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.OListElement;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;

public class OListInserter extends ListInserter<OListElement> {
  public static final String TAG_NAME = "ol";
  public static final List<String> TAGS_LIST = Arrays.asList("ol", "ul");
  protected static final OListInsertAction insertAction = new OListInsertAction();

  protected static class OListInsertAction extends ListInsertAction<OListElement> {
    private static final OListElement emptyElement = Document.get().createOLElement();

    @Override
    public OListElement getEmptyElement() {
      return emptyElement;
    }

    @Override
    protected List<String> getTagCollection() {
      return TAGS_LIST;
    }
  }

  public OListInserter() {
    this(insertAction);
  }

  protected OListInserter(InsertAction<OListElement> action) {
    super(action);

  }

  @Override
  protected OListElement as(Element element) {
    return OListElement.as(element);
  }

  @Override
  protected List<String> getTagCollection() {
    return TAGS_LIST;
  }
}
