package com.axeiya.gwt.surface.client.ranges;

import com.axeiya.gwt.surface.client.Surface;

public class SurfaceSelection {

  private Selection selection;
  private Surface associatedSurface;

  public SurfaceSelection(Selection selection, Surface associatedSurface) {
    this.selection = selection;
    this.associatedSurface = associatedSurface;
  }

  public Selection getSelection() {
    return selection;
  }

  public void setSelection(Selection selection) {
    this.selection = selection;
  }

  public Surface getAssociatedSurface() {
    return associatedSurface;
  }

  public void setAssociatedSurface(Surface associatedSurface) {
    this.associatedSurface = associatedSurface;
  }

}
