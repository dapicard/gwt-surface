package com.axeiya.gwt.surface.client.control.block;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.axeiya.gwt.surface.client.control.AbstractControl;
import com.axeiya.gwt.surface.client.control.resource.ControlResources;
import com.axeiya.gwt.surface.client.event.selectionchange.SelectionChangeEvent;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.blockinserter.text.LinkInserter;
import com.axeiya.gwt.surface.client.inserter.blockinserter.text.LinkInserter.LinkConfig;
import com.axeiya.gwt.surface.client.widget.DecoratedPushButton;

public class InsertLink extends AbstractControl implements ClickHandler, IsWidget {

	private DecoratedPushButton ui;
	private LinkInserter inserter;
	private boolean imgSelected = false;

	public InsertLink() {
		this(ControlResources.Util.getInstance());
	}

	public InsertLink(ControlResources resources) {
		ui = new DecoratedPushButton(new Image(resources.link()));
		ui.setTitle(CONSTANTS.insertLink());
		ui.setStyleName(resources.button().surfacePushButton());
		inserter = new LinkInserter();
		ui.addClickHandler(this);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (!imgSelected) {
			final String url = Window.prompt("URL", "http://talents-affinity.com");
			execute(new Command() {
				@Override
				public void execute() {
					inserter.insert(currentSurface.getSelection(), new LinkConfig(url));
				}
			});

		} else {
			LinkConfig config = inserter.getCurrentConfig(currentSurface.getSelection());
			if (config != null) {
				final String url = Window.prompt("URL", config.getUrl());
				execute(new Command() {
					@Override
					public void execute() {
						inserter.updateConfig(currentSurface.getSelection(), url);
					}
				});
			} else {
				final String url = Window.prompt("URL", "http://axeiya.com");
				execute(new Command() {
					@Override
					public void execute() {
						inserter.insert(currentSurface.getSelection(), new LinkConfig(url));
					}
				});
			}
		}
	}

	public DecoratedPushButton getUi() {
		return ui;
	}

	public Inserter getInserter() {
		return inserter;
	}

	@Override
	public void onSelectionChange(SelectionChangeEvent event) {
		imgSelected = inserter.isSelectionAssignee(event.getSelection());
	}

	@Override
	public Widget asWidget() {
		return ui;
	}
}
