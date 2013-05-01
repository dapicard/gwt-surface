package com.axeiya.gwt.surface.client.control.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.axeiya.gwt.surface.client.control.AbstractControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.event.selectionchange.SelectionChangeEvent;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.text.FontFamilyInserter;
import com.axeiya.gwt.surface.client.inserter.inlineinserter.text.FontFamilyInserter.FontFamilyConfig;

public class FontFamilySelector extends AbstractControl implements IsWidget, ChangeHandler {

  public static final FontFamily DEFAULT_FAMILY = new FontFamily("Default", "");
  public static final List<FontFamily> FAMILIES;
  static {
    FAMILIES = new ArrayList<FontFamilySelector.FontFamily>();
    FAMILIES.add(new FontFamily("Andale mono", "'andale mono', times"));
    FAMILIES.add(new FontFamily("Arial", "arial, helvetica, sans-serif"));
    FAMILIES.add(new FontFamily("Arial Black", "arial black,avant garde"));
    FAMILIES.add(new FontFamily("Book Antiqua", "book antiqua,palatino"));
    FAMILIES.add(new FontFamily("Comic Sans MS", "comic sans ms,sans-serif"));
    FAMILIES.add(new FontFamily("Courrier New", "courier new,courier"));
    FAMILIES.add(new FontFamily("Georgia", "georgia,palatino"));
    FAMILIES.add(new FontFamily("Helvetica", "helvetica"));
    FAMILIES.add(new FontFamily("Impact", "impact,chicago"));
    FAMILIES.add(new FontFamily("Symbol", "symbol"));
    FAMILIES.add(new FontFamily("Tahoma", "tahoma,arial,helvetica,sans-serif"));
    FAMILIES.add(new FontFamily("Terminal", "terminal,monaco"));
    FAMILIES.add(new FontFamily("Times New Roman", "times new roman,times"));
    FAMILIES.add(new FontFamily("Trebuchet MS", "trebuchet ms,geneva"));
    FAMILIES.add(new FontFamily("Verdana", "verdana,geneva"));
  }

  public static class FontFamily {
    private String name;
    private String css;

    public FontFamily(String name) {
      this(name, name);
    }

    public FontFamily(String name, String css) {
      super();
      this.name = name;
      this.css = css;
    }

    public String getName() {
      return name;
    }

    public String getCss() {
      return css;
    }
  }

  private FlowPanel ui;
  private ListBox fontSelector = new ListBox();
  private Map<Integer, Inserter> inserters = new HashMap<Integer, Inserter>();
  private FontFamilyInserter defaultInserter = new FontFamilyInserter(new FontFamilyConfig(
      DEFAULT_FAMILY.getCss()));

  public FontFamilySelector() {
    this(ControlResources.Util.getInstance());
  }

  public FontFamilySelector(ControlResources resources) {
    ui = new FlowPanel();
    ui.setStyleName(resources.button().surfaceDiv());
    fontSelector.addChangeHandler(this);
    fontSelector.setWidth("150px");
    ui.add(fontSelector);
    addFontFamily(DEFAULT_FAMILY);
    inserters.put(0, defaultInserter);
    for (FontFamily family : FAMILIES) {
      addFontFamily(family);
    }
  }

  public void addFontFamily(FontFamily fontFamily) {
    int index = fontSelector.getItemCount();
    fontSelector.addItem(fontFamily.getName());
    inserters.put(index, new FontFamilyInserter(new FontFamilyConfig(fontFamily.getCss())));
  }

  @Override
  public void onSelectionChange(SelectionChangeEvent event) {
    boolean found = false;
    int index = 1;
    Iterator<Inserter> inserter = inserters.values().iterator();
    // We skip the defaultInserter because this is the fallback
    inserter.next();
    while (!found && inserter.hasNext()) {
      if (inserter.next().isSelectionAssignee(event.getSelection())) {
        found = true;
        fontSelector.setSelectedIndex(index);
      }
      index++;
    }
    if (!found) {
      // fallback
      fontSelector.setSelectedIndex(0);
    }
  }

  @Override
  public Inserter getInserter() {
    return defaultInserter;
  }

  @Override
  public Widget asWidget() {
    return ui;
  }

  @Override
  public void onChange(ChangeEvent event) {
    final Inserter inserter = inserters.get(fontSelector.getSelectedIndex());
    execute(new Command() {
      @Override
      public void execute() {
        inserter.insert(currentSurface.getSelection());
      }
    });
  }

}
