package com.axeiya.gwt.surface.client;

import com.axeiya.gwt.surface.client.control.block.InsertLink;
import com.axeiya.gwt.surface.client.control.block.Quote;
import com.axeiya.gwt.surface.client.control.block.RemoveLink;
import com.axeiya.gwt.surface.client.control.character.Bold;
import com.axeiya.gwt.surface.client.control.character.FontFamilySelector;
import com.axeiya.gwt.surface.client.control.character.Italic;
import com.axeiya.gwt.surface.client.control.character.Strikethrought;
import com.axeiya.gwt.surface.client.control.character.Subscript;
import com.axeiya.gwt.surface.client.control.character.Superscript;
import com.axeiya.gwt.surface.client.control.character.Underline;
import com.axeiya.gwt.surface.client.control.image.ImageSize;
import com.axeiya.gwt.surface.client.control.image.InsertImage;
import com.axeiya.gwt.surface.client.control.image.MainImageButton;
import com.axeiya.gwt.surface.client.control.indent.Indent;
import com.axeiya.gwt.surface.client.control.indent.Outdent;
import com.axeiya.gwt.surface.client.control.list.OList;
import com.axeiya.gwt.surface.client.control.list.UList;
import com.axeiya.gwt.surface.client.control.paragraph.ParagraphControl;
import com.axeiya.gwt.surface.client.control.paragraph.alignment.CenterAlign;
import com.axeiya.gwt.surface.client.control.paragraph.alignment.JustifyAlign;
import com.axeiya.gwt.surface.client.control.paragraph.alignment.LeftAlign;
import com.axeiya.gwt.surface.client.control.paragraph.alignment.MainAlignButton;
import com.axeiya.gwt.surface.client.control.paragraph.alignment.RightAlign;
import com.axeiya.gwt.surface.client.control.table.AddColumn;
import com.axeiya.gwt.surface.client.control.table.AddLine;
import com.axeiya.gwt.surface.client.control.table.DropColumn;
import com.axeiya.gwt.surface.client.control.table.DropLine;
import com.axeiya.gwt.surface.client.control.table.DropTable;
import com.axeiya.gwt.surface.client.control.table.InsertTable;
import com.axeiya.gwt.surface.client.control.table.MainTableButton;
import com.axeiya.gwt.surface.client.control.table.TableConfiguration;
import com.axeiya.gwt.surface.client.event.enterkeypressed.EnterKeyPressedHandler;
import com.axeiya.gwt.surface.client.event.hotkeypressed.HotKeyPressedHandler;
import com.axeiya.gwt.surface.client.inserter.paragraphinserter.StyledParagraphInserter;
import com.axeiya.gwt.surface.client.widget.Separator;

public class DefaultToolBar extends ToolBar {

  public DefaultToolBar() {
    super();
    setStyleName("sc-Wysiwyg-Toolbar");

    add(new Bold());
    add(new Italic());
    add(new Underline());
    add(new Subscript());
    add(new Superscript());
    add(new Strikethrought());
    add(new FontFamilySelector());
    addWidget(new Separator());

    ToolGroup alignmentGroup = new ToolGroup(new MainAlignButton());
    alignmentGroup.addSubComponent(new LeftAlign());
    alignmentGroup.addSubComponent(new CenterAlign());
    alignmentGroup.addSubComponent(new RightAlign());
    alignmentGroup.addSubComponent(new JustifyAlign());
    add(alignmentGroup);
    ParagraphControl pControl = new ParagraphControl();
    pControl.addParagraphStyle("Code", new StyledParagraphInserter("code"));
    add(pControl);

    UList ulist = new UList();
    add(ulist);
    OList olist = new OList();
    add(olist);
    // add(new Pre());
    add(new Quote());
    add(new Indent());
    add(new Outdent());

    addWidget(new Separator());
    add(new InsertLink());
    add(new RemoveLink());

    ToolGroup imageGroup = new ToolGroup(new MainImageButton());
    imageGroup.addSubComponent(new InsertImage());
    imageGroup.addSubComponent(new ImageSize());
    add(imageGroup);

    ToolGroup tableGroup = new ToolGroup(new MainTableButton());
    tableGroup.addSubComponent(new InsertTable());
    tableGroup.addSubComponent(new DropTable());
    tableGroup.addSubComponent(new AddLine());
    tableGroup.addSubComponent(new DropLine());
    tableGroup.addSubComponent(new AddColumn());
    tableGroup.addSubComponent(new DropColumn());
    tableGroup.addSubComponent(new TableConfiguration());
    add(tableGroup);

    // Réaction aux Enters
    addEnterKeyPressedHandler((EnterKeyPressedHandler) ulist.getInserter());
    addEnterKeyPressedHandler((EnterKeyPressedHandler) olist.getInserter());
    addEnterKeyPressedHandler((EnterKeyPressedHandler) pControl.getInserter());

    // Réaction aux raccourcis
    addHotKeyPressedHandler((HotKeyPressedHandler) ulist.getInserter());
  }
}
