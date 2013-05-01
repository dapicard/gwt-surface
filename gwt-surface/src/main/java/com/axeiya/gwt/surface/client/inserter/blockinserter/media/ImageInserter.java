package com.axeiya.gwt.surface.client.inserter.blockinserter.media;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style.Unit;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.blockinserter.BlockInserter;
import com.axeiya.gwt.surface.client.ranges.Selection;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class ImageInserter extends BlockInserter<ImageElement> {

  private static final ImageElement emptyElement = Document.get().createImageElement();

  private ImageConfig currentConfig;

  public ImageInserter() {
    action = new ImageInsertAction();
  }

  protected ImageInserter(InsertAction<ImageElement> action) {
    this.action = action;
  }

  protected class ImageInsertAction extends InsertAction<ImageElement> {

    @Override
    public void onAction(ImageElement element, SurfaceSelection selection) {
      ImageConfig config = ImageInserter.this.currentConfig;
      element.setSrc(config.getUrl());
      if (config.getWidth() > -1) {
        element.getStyle().setWidth(config.getWidth(), Unit.PX);
      } else {
        element.getStyle().clearWidth();
      }
      if (config.getHeight() > -1) {
        element.getStyle().setHeight(config.getHeight(), Unit.PX);
      } else {
        element.getStyle().clearHeight();
      }
    }

    @Override
    public ImageElement getEmptyElement() {
      return emptyElement;
    }
  }

  public static class ImageConfig {
    private String url;
    private int width = -1;
    private int height = -1;

    public ImageConfig(String url) {
      super();
      this.url = url;
    }

    public String getUrl() {
      return url;
    }

    public int getWidth() {
      return width;
    }

    public int getHeight() {
      return height;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public void setHeight(int height) {
      this.height = height;
    }

  }

  @Override
  protected ImageElement as(Element element) {
    return ImageElement.as(element);
  }

  public void insert(SurfaceSelection selection, ImageConfig config) {
    currentConfig = config;
    selection.getSelection().getRange().deleteContents();
    selection.getSelection().getRange().collapse(true);
    super.insert(selection);
  }

  /**
   * Use {@link ImageInserter#insert(Selection, String)} instead
   */
  @Deprecated
  @Override
  public void insert(SurfaceSelection selection) {
    throw new IllegalArgumentException("Use insert(Selection,ImageConfig) instead");
  }

  @Override
  public boolean isSelectionAssignee(SurfaceSelection selection) {
    Node ancestor = selection.getSelection().getRange().getCommonAncestorContainer();
    if (ancestor != null && ancestor.getNodeType() == Node.ELEMENT_NODE) {
      Element img = DOMUtil.getFirstChildOfType((Element) ancestor, "img");
      return img != null;
    }
    return false;
  }

  public ImageConfig getCurrentConfig(SurfaceSelection selection) {
    Node ancestor = selection.getSelection().getRange().getCommonAncestorContainer();
    if (ancestor != null && ancestor.getNodeType() == Node.ELEMENT_NODE) {
      ImageElement img = (ImageElement) DOMUtil.getFirstChildOfType((Element) ancestor, "img");
      if (img != null) {
        ImageConfig config = new ImageConfig(img.getSrc());
        try {
          if (img.getStyle().getWidth() != null && !img.getStyle().getWidth().isEmpty()) {
            String width = img.getStyle().getWidth().replaceAll("px", "");
            config.setWidth(Integer.parseInt(width));
          }
          if (img.getStyle().getHeight() != null && !img.getStyle().getHeight().isEmpty()) {
            String height = img.getStyle().getHeight().replaceAll("px", "");
            config.setHeight(Integer.parseInt(height));
          }
        } catch (NumberFormatException nfe) {
          GWT.log("Error while retrieving img size", nfe);
        }
        return config;
      }
    }
    return null;

  }

  public void updateConfig(SurfaceSelection selection, ImageConfig config) {
    Node ancestor = selection.getSelection().getRange().getCommonAncestorContainer();
    if (ancestor != null && ancestor.getNodeType() == Node.ELEMENT_NODE) {
      ImageElement img = (ImageElement) DOMUtil.getFirstChildOfType((Element) ancestor, "img");
      if (img != null) {
        currentConfig = config;
        action.onAction(img, selection);
      }
    }
  }
}
