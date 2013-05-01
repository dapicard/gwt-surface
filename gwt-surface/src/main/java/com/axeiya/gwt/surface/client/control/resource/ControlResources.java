package com.axeiya.gwt.surface.client.control.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.axeiya.gwt.surface.client.control.resource.css.ButtonCss;
import com.axeiya.gwt.surface.client.control.resource.css.ToolBarCss;

public interface ControlResources extends ClientBundle {

  static class Util {
    private static ControlResources instance;

    public static ControlResources getInstance() {
      if (instance == null) {
        instance = GWT.create(ControlResources.class);
        instance.button().ensureInjected();
        instance.toolbar().ensureInjected();
      }
      return instance;
    }
  }

  @Source("css/button.css")
  @NotStrict
  ButtonCss button();

  @Source("css/toolbar.css")
  @NotStrict
  ToolBarCss toolbar();

  /*
   * Icon
   */
  @Source("icon/left-border.png")
  DataResource leftBorder();

  @Source("icon/right-border.png")
  DataResource rightBorder();

  @Source("icon/top-border.png")
  DataResource topBorder();

  @Source("icon/bottom-border.png")
  DataResource bottomBorder();

  @Source("icon/button-background.png")
  DataResource buttonBackground();

  @Source("icon/bold.png")
  ImageResource bold();

  @Source("icon/italic.png")
  ImageResource italic();

  @Source("icon/underline.png")
  ImageResource underline();

  @Source("icon/image.gif")
  ImageResource image();

  @Source("icon/image-menu.png")
  ImageResource imageMenu();

  @Source("icon/image-active.gif")
  ImageResource imageActive();

  @Source("icon/underline.png")
  ImageResource strikethrought();

  @Source("icon/ulist.png")
  ImageResource unorderedList();

  @Source("icon/olist.png")
  ImageResource orderedList();

  @Source("icon/add-column.png")
  ImageResource addColumn();

  @Source("icon/add-row.png")
  ImageResource addRow();

  @Source("icon/add-table.png")
  ImageResource addTable();

  @Source("icon/align-center.png")
  ImageResource alignCenter();

  @Source("icon/align-justify.png")
  ImageResource alignJustify();

  @Source("icon/align-left.png")
  ImageResource alignLeft();

  @Source("icon/align-menu.png")
  ImageResource alignMenu();

  @Source("icon/align-right.png")
  ImageResource alignRight();

  @Source("icon/background-color.png")
  ImageResource backgroundColor();

  @Source("icon/bullet-type.png")
  ImageResource bulletType();

  @Source("icon/drop-column.png")
  ImageResource dropColumn();

  @Source("icon/drop-row.png")
  ImageResource dropRow();

  @Source("icon/drop-table.png")
  ImageResource dropTable();

  @Source("icon/font-color.png")
  ImageResource fontColor();

  @Source("icon/indent.png")
  ImageResource indent();

  @Source("icon/line-spacing.png")
  ImageResource lineSpacing();

  @Source("icon/link.png")
  ImageResource link();

  @Source("icon/list-menu.png")
  ImageResource listMenu();

  @Source("icon/merge-cell.png")
  ImageResource mergeCell();

  @Source("icon/outdent.png")
  ImageResource outdent();

  @Source("icon/paragraph-after.png")
  ImageResource paragraphAfter();

  @Source("icon/paragraph-before.png")
  ImageResource paragraphBefore();

  @Source("icon/redo.png")
  ImageResource redo();

  @Source("icon/spacing-menu.png")
  ImageResource spacingMenu();

  @Source("icon/subscript.png")
  ImageResource subscript();

  @Source("icon/superscript.png")
  ImageResource superscript();

  @Source("icon/table-menu.png")
  ImageResource tableMenu();

  @Source("icon/undo.png")
  ImageResource undo();

  @Source("icon/quote.png")
  ImageResource quote();

  @Source("icon/drop-link.png")
  ImageResource dropLink();

  @Source("icon/comment.png")
  ImageResource comment();

  @Source("other/separator.png")
  ImageResource separator();

}
