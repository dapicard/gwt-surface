package com.axeiya.gwt.surface.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

/**
 * Scheduler permettant de traiter séparemment des Commands. L'exécution de
 * chaque Command est séparée par un retour au navigateur
 * 
 * @author Damien Picard
 * 
 */
public class DelayedScheduler {
	@SuppressWarnings("unchecked")
	private static JsArray<Job> jobQueue = (JsArray<Job>) JavaScriptObject.createArray();
	private static boolean running = false;
	private static final Timer scheduler = new Timer() {
		@Override
		public void run() {
			Job task = jobQueue.shift();
			if (task != null) {
				task.getCommand().execute();
				scheduler.schedule(1);
			} else {
				running = false;
			}
		}

		public void cancel() {
			super.cancel();
			running = false;
		};

	};

	/**
	 * Ajoute une commande à traiter
	 * 
	 * @param command
	 *            Commande à traiter
	 */
	public static void scheduleDelayed(Command command) {
		Job task = Job.create();
		task.setCommand(command);
		jobQueue.push(task);
		maybeStart();
	}

	private static void maybeStart() {
		if (!running) {
			running = true;
			scheduler.schedule(1);
		}
	}

	static final class Job extends JavaScriptObject {

		// constructeur protégé par défaut
		protected Job() {
		}

		public static final Job create() {
			return JavaScriptObject.createObject().cast();
		};

		public native void setCommand(Command command) /*-{
			this.command = command;
		}-*/;

		public native Command getCommand() /*-{
			return this.command;
		}-*/;
	}
}
