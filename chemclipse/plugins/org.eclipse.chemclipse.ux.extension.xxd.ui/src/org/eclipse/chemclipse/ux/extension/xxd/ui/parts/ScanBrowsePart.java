/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.ux.extension.xxd.ui.parts;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.ux.extension.xxd.ui.Activator;
import org.eclipse.chemclipse.ux.extension.xxd.ui.part.support.DataUpdateSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.part.support.IDataUpdateListener;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ExtendedScanBrowseUI;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ScanBrowsePart {

	private static final String TOPIC = IChemClipseEvents.TOPIC_CHROMATOGRAM_XXD_UPDATE_SELECTION;
	//
	private DataUpdateSupport dataUpdateSupport = Activator.getDefault().getDataUpdateSupport();
	private ExtendedScanBrowseUI composite;
	//
	private IDataUpdateListener updateListener = new IDataUpdateListener() {

		@Override
		public void update(String topic, List<Object> objects) {

			updateSelection(objects, topic);
		}
	};

	@Inject
	public ScanBrowsePart(Composite parent, MPart part) {

		composite = new ExtendedScanBrowseUI(parent, SWT.NONE);
		dataUpdateSupport.add(updateListener);
	}

	@Focus
	public void setFocus() {

		updateSelection(dataUpdateSupport.getUpdates(TOPIC), TOPIC);
	}

	@Override
	protected void finalize() throws Throwable {

		dataUpdateSupport.remove(updateListener);
		super.finalize();
	}

	private void updateSelection(List<Object> objects, String topic) {

		/*
		 * 0 => because only one property was used to register the event.
		 */
		if(isVisible()) {
			if(objects.size() == 1) {
				Object object = objects.get(0);
				if(isChromatogramTopic(topic)) {
					if(object instanceof IChromatogramSelection) {
						composite.update((IChromatogramSelection<?, ?>)object);
					}
				}
			}
		}
	}

	private boolean isVisible() {

		return (composite != null && !composite.isDisposed() && composite.isVisible());
	}

	private boolean isChromatogramTopic(String topic) {

		if(topic.equals(IChemClipseEvents.TOPIC_CHROMATOGRAM_XXD_UPDATE_SELECTION)) {
			return true;
		} else if(topic.equals(IChemClipseEvents.TOPIC_CHROMATOGRAM_MSD_UPDATE_CHROMATOGRAM_SELECTION)) {
			return true;
		} else if(topic.equals(IChemClipseEvents.TOPIC_CHROMATOGRAM_CSD_UPDATE_CHROMATOGRAM_SELECTION)) {
			return true;
		} else if(topic.equals(IChemClipseEvents.TOPIC_CHROMATOGRAM_WSD_UPDATE_CHROMATOGRAM_SELECTION)) {
			return true;
		} else {
			return false;
		}
	}
}