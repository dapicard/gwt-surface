package com.axeiya.gwt.surface.client.inserter;

import com.google.gwt.dom.client.Element;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

abstract public class Inserter {

  abstract public void insert(SurfaceSelection selection);

  abstract public void remove(SurfaceSelection selection);

  abstract public boolean isSelectionAssignee(SurfaceSelection selection);

  abstract protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection);
}
