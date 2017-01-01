/*******************************************************************************
 * Copyright (c) 2013, 2017 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.peak.detector.supplier.deconvolution.ui.handlers;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Named;

import org.eclipse.chemclipse.chromatogram.msd.peak.detector.supplier.deconvolution.ui.internal.handlers.DetectorRunnable;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.progress.core.InfoType;
import org.eclipse.chemclipse.progress.core.StatusLineLogger;
import org.eclipse.chemclipse.rcp.app.ui.handlers.PerspectiveSwitchHandler;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.support.events.IPerspectiveAndViewIds;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class DetectorHandler implements EventHandler {

	private static final Logger logger = Logger.getLogger(DetectorHandler.class);
	private static IChromatogramSelectionMSD chromatogramSelection;

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_PART) MPart part) {

		/*
		 * Try to select and show the perspective and view.
		 */
		PerspectiveSwitchHandler.focusPerspectiveAndView(IPerspectiveAndViewIds.PERSPECTIVE_PEAKS_MSD, IPerspectiveAndViewIds.VIEW_PEAK_LIST_MSD);
		/*
		 * Get the actual cursor, create a new wait cursor and show the wait
		 * cursor.<br/> Show the origin cursor when finished.<br/> Use the
		 * Display.asyncExec instances to show the messages properly.<br/> I
		 * really don't like the GUI stuff, maybe there is a smarter way to
		 * inform the user.<br/> So, i thought also on Eclipse "jobs", but there
		 * will be problems, when the operation method updates the
		 * chromatogramSelection.<br/> A progress bar would be also applicable,
		 * but the IProgressMonitorDialog (monitor.beginTask(TASK_NAME,
		 * IProgressMonitor.UNKNOWN) didn't showed a progress under linux.
		 */
		if(chromatogramSelection != null) {
			final Display display = Display.getCurrent();
			DetectorRunnable runnable = new DetectorRunnable(chromatogramSelection);
			ProgressMonitorDialog monitor = new ProgressMonitorDialog(display.getActiveShell());
			try {
				/*
				 * Use true, true ... instead of false, true ... if the progress bar
				 * should be shown in action.
				 */
				monitor.run(true, true, runnable);
			} catch(InvocationTargetException e) {
				logger.warn(e);
			} catch(InterruptedException e) {
				logger.warn(e);
			}
			StatusLineLogger.setInfo(InfoType.MESSAGE, "Done: " + runnable.getDetectedPeaks() + " Peaks detected");
		}
	}

	@Override
	public void handleEvent(Event event) {

		if(event.getTopic().equals(IChemClipseEvents.TOPIC_CHROMATOGRAM_MSD_UPDATE_CHROMATOGRAM_SELECTION)) {
			chromatogramSelection = (IChromatogramSelectionMSD)event.getProperty(IChemClipseEvents.PROPERTY_CHROMATOGRAM_SELECTION);
		}
	}
}
