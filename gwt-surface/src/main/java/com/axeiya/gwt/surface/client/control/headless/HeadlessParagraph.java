package com.axeiya.gwt.surface.client.control.headless;

import com.axeiya.gwt.surface.client.event.selectionchange.SelectionChangeEvent;
import com.axeiya.gwt.surface.client.inserter.paragraphinserter.PInserter;

/**
 * Utilisé pour capturer le [Enter] par défaut, sans avoir d'IHM correspondante
 * 
 * @author damien
 * 
 */
public class HeadlessParagraph extends AbstractHeadlessControl {

  public HeadlessParagraph() {
    super(new PInserter());
  }

  @Override
  public void onSelectionChange(SelectionChangeEvent event) {

  }
}
