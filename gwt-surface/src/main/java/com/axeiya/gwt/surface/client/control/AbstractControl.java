package com.axeiya.gwt.surface.client.control;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.axeiya.gwt.surface.client.Surface;
import com.axeiya.gwt.surface.client.event.selectedsurfacechange.SelectedSurfaceChangeEvent;
import com.axeiya.gwt.surface.client.event.selectedsurfacechange.SelectedSurfaceChangeHandler;
import com.axeiya.gwt.surface.client.event.selectionchange.SelectionChangeHandler;
import com.axeiya.gwt.surface.client.i18n.ControlConstants;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.util.DelayedScheduler;

abstract public class AbstractControl implements SelectionChangeHandler,
    SelectedSurfaceChangeHandler {
  public static final ControlConstants CONSTANTS = GWT.create(ControlConstants.class);

  protected Surface currentSurface;

  protected void execute(final Command command) {
    DelayedScheduler.scheduleDelayed(new Command() {
      @Override
      public void execute() {
        if (currentSurface != null
            && currentSurface.isEditable()
            && currentSurface.getElement().isOrHasChild(
                currentSurface.getSelection().getSelection().getRange().getStartContainer())
            && currentSurface.getElement().isOrHasChild(
                currentSurface.getSelection().getSelection().getRange().getEndContainer())) {
          command.execute();
          currentSurface.notifyUpdate();
        }
      }
    });
  }

  @Override
  public void onSelectedSurfaceChange(SelectedSurfaceChangeEvent event) {
    this.currentSurface = event.getSurface();
  }

  abstract public Inserter getInserter();
}
