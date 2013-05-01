package com.axeiya.gwt.surface.client.inserter.tableinserter;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.TableCaptionElement;
import com.google.gwt.dom.client.TableElement;
import com.axeiya.gwt.surface.client.event.enterkeypressed.EnterKeyPressedEvent;
import com.axeiya.gwt.surface.client.event.enterkeypressed.EnterKeyPressedHandler;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.inserter.blockinserter.BlockInserter;
import com.axeiya.gwt.surface.client.inserter.paragraphinserter.PInserter;
import com.axeiya.gwt.surface.client.ranges.Range;
import com.axeiya.gwt.surface.client.ranges.Selection;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class TableInserter extends Inserter implements EnterKeyPressedHandler {

	private static final TableElement emptyElement = Document.get().createTableElement();
	public static final String TABLE_TAG = "table";
	public static List<String> SECTION_TAGS = Arrays.asList("thead", "tbody");
	public static final String LINE_TAG = "tr";
	public static List<String> CELL_TAGS = Arrays.asList("td", "th");

	protected class LocalBlockInserter extends BlockInserter<TableElement> {

		protected LocalBlockInserter(InsertAction<TableElement> action) {
			super(action);
		}

		@Override
		protected TableElement as(Element element) {
			return TableInserter.this.as(element);
		}

		@Override
		protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection) {
			return TableInserter.this.adjustSelectionAssignee(matchingAncestor, selection);
		}

	}

	protected class TableInsertAction extends InsertAction<TableElement> {

		@Override
		public void onAction(TableElement element, SurfaceSelection selection) {
			TableConfig config = TableInserter.this.currentConfig;
			element.setWidth(config.getWidth() + config.getWidthUnit().getType());
			element.setBorder(1);
			if (config.getCaption() != null) {
				TableCaptionElement caption = Document.get().createCaptionElement();
				caption.setInnerText(config.getCaption());
				caption.setAttribute("align", config.getCaptionAlign().name().toLowerCase());
				element.appendChild(caption);
			}

			if (config.isHeaderLine()) {
				Element thead = Document.get().createTHeadElement();
				Element tr = Document.get().createTRElement();
				thead.appendChild(tr);
				element.appendChild(thead);
				for (int column = 0; column < config.getColumn(); column++) {
					Element th = Document.get().createTHElement();
					tr.appendChild(th);
					th.appendChild(DOMUtil.createFocusBr());
				}
			}
			Element tbody = Document.get().createTBodyElement();
			element.appendChild(tbody);
			for (int line = (config.isHeaderLine() ? 1 : 0); line < config.getLine(); line++) {
				Element tr = Document.get().createTRElement();
				tbody.appendChild(tr);
				for (int column = 0; column < config.getColumn(); column++) {
					Element td = Document.get().createTDElement();
					tr.appendChild(td);
					td.appendChild(DOMUtil.createFocusBr());
				}
			}
		}

		@Override
		public TableElement getEmptyElement() {
			return emptyElement;
		}
	}

	protected LocalBlockInserter blockInserter;
	protected InsertAction<TableElement> action;
	protected TableConfig currentConfig;

	public TableInserter() {
		action = new TableInsertAction();
		blockInserter = new LocalBlockInserter(action);
	}

	protected TableInserter(InsertAction<TableElement> action) {
		this.action = action;
		blockInserter = new LocalBlockInserter(action);
	}

	public void insert(SurfaceSelection selection, TableConfig config) {
		currentConfig = config;
		selection = getNextRootRange(selection);
		blockInserter.insert(selection);
	}

	/**
	 * Use {@link TableInserter#insert(Selection, TableConfig)} instead
	 */
	@Deprecated
	@Override
	public void insert(SurfaceSelection selection) {
		throw new IllegalArgumentException("Use insert(Selection,TableConfig) instead");
	}

	/**
	 * Retourne une selection juste après le noeud racine de la sélection passée
	 * en paramètre, sauf si c'est un tableau, pour pouvoir imbriquer les tableaux
	 * 
	 * @param selection
	 * @return
	 */
	protected SurfaceSelection getNextRootRange(SurfaceSelection selection) {
		Node base = selection.getSelection().getRange().getCommonAncestorContainer();
		Element ancestor;
		if (base.getNodeType() == Node.TEXT_NODE) {
			ancestor = base.getParentElement();
		} else {
			ancestor = (Element) base;
		}

		if (ancestor != null && !ancestor.hasAttribute(DOMUtil.ROOT_ATTRIBUTE)) {
			while (ancestor != null && !ancestor.getParentElement().hasAttribute(DOMUtil.ROOT_ATTRIBUTE)) {
				ancestor = ancestor.getParentElement();
			}
			if (!ancestor.getTagName().equals(emptyElement.getTagName())) {
				// On calcule l'offset du noeud
				int offset = 1;// 1 car on veut s'insérer après ancestor
				Node child = ancestor.getParentElement().getFirstChild();
				while (!child.equals(ancestor)) {
					offset++;
					child = child.getNextSibling();
				}
				// on place la sélection après cet ancetre
				selection.getSelection().getRange().setStart(ancestor.getParentElement(), offset);
				selection.getSelection().getRange().setEnd(ancestor.getParentElement(), offset);
			}
		}
		return selection;
	}

	protected TableElement as(Element element) {
		return TableElement.as(element);
	}

	@Override
	public void remove(SurfaceSelection selection) {
		Node ancestor = DOMUtil.getFirstAncestorOfType(selection.getSelection().getRange().getStartContainer(), TABLE_TAG);
		if (ancestor != null) {
			ancestor.removeFromParent();
		}
	}

	@Override
	public boolean isSelectionAssignee(SurfaceSelection selection) {
		return blockInserter.isSelectionAssignee(selection);
	}

	@Override
	protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection) {
		return true;
	}

	protected boolean isInLastLine(Node node) {
		Node tr = DOMUtil.getFirstAncestorOfType(node, LINE_TAG);
		if (tr != null) {
			Element parent = tr.getParentElement();
			if (parent.getTagName().toLowerCase().equals(TABLE_TAG)) {
				return tr.getNextSibling() == null;
			} else if (SECTION_TAGS.contains(parent.getTagName().toLowerCase())) {
				if (parent.getTagName().toLowerCase().equals("thead")) {
					return tr.getNextSibling() == null && parent.getNextSiblingElement() == null;
				} else {
					// tbody
					return tr.getNextSibling() == null;
				}
			}
		}
		return false;
	}

	@Override
	public void onEnterKeyPressed(EnterKeyPressedEvent event) {
		if (!event.isHandled() && isInLastLine(event.getSelection().getSelection().getRange().getStartContainer())) {
			Element table = DOMUtil.getFirstAncestorOfType(event.getSelection().getSelection().getRange().getStartContainer(), TABLE_TAG);
			Element paragraph = PInserter.createEmptyParagraph();
			table.getParentElement().insertAfter(paragraph, table);
			Range range = Range.createRange();
			range.setStart(paragraph, 0);
			range.setEnd(paragraph, 0);
			event.getSelection().getAssociatedSurface().setSelection(range);
			event.setHandled(true);
			event.setPreventDefault(true);
		}
	}

	public TableConfig getCurrentConfig(SurfaceSelection selection) {
		Node ancestor = DOMUtil.getFirstAncestorOfType(selection.getSelection().getRange().getStartContainer(), TABLE_TAG);
		if (ancestor != null) {
			TableElement table = (TableElement) ancestor;

			TableConfig config = new TableConfig();
			try {
				config.setBorderSize(Integer.parseInt(table.getAttribute("border")));
			} catch (NumberFormatException nfe) {
				config.setBorderSize(0);
			}
			return config;
		} else {
			return null;
		}
	}

}
