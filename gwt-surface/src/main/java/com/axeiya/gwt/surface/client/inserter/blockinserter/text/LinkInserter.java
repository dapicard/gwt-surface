package com.axeiya.gwt.surface.client.inserter.blockinserter.text;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.blockinserter.BlockInserter;
import com.axeiya.gwt.surface.client.ranges.Selection;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class LinkInserter extends BlockInserter<AnchorElement> {

  private static final AnchorElement emptyElement = Document.get().createAnchorElement();

  private LinkConfig currentConfig;

  public LinkInserter() {
    action = new LinkInsertAction();
  }

  protected LinkInserter(InsertAction<AnchorElement> action) {
    this.action = action;
  }

  protected class LinkInsertAction extends InsertAction<AnchorElement> {

    @Override
    public void onAction(AnchorElement element, SurfaceSelection selection) {
      LinkConfig config = LinkInserter.this.currentConfig;
      element.setHref(config.getUrl());
    }

    @Override
    public AnchorElement getEmptyElement() {
      return emptyElement;
    }
  }

  public static class LinkConfig {
    private String url;

    public LinkConfig(String url) {
      super();
      this.url = url;
    }

    public String getUrl() {
      return url;
    }
  }

  @Override
  protected AnchorElement as(Element element) {
    return AnchorElement.as(element);
  }

  public void insert(SurfaceSelection selection, LinkConfig config) {
    currentConfig = config;
    super.insert(selection);
  }

  /**
   * Use {@link LinkInserter#insert(Selection, String)} instead
   */
  @Deprecated
  @Override
  public void insert(SurfaceSelection selection) {
    throw new IllegalArgumentException("Use insert(Selection,String) instead");
  }

  public LinkConfig getCurrentConfig(SurfaceSelection selection) {
    AnchorElement anchor = (AnchorElement) getCommonMatchingAncestor(selection);
    if (anchor != null) {
      LinkConfig config = new LinkConfig(anchor.getHref());
      return config;
    }
    return null;

  }

  public void updateConfig(SurfaceSelection selection, String url) {
    AnchorElement anchor = (AnchorElement) getCommonMatchingAncestor(selection);
    if (anchor != null) {
      currentConfig = new LinkConfig(url);
      action.onAction(anchor, selection);
    }
  }
}
