/*******************************************************************************
 * Copyright (c) 2022, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.ux.extension.xxd.ui.model;

import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.support.ScanSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.Activator;
import org.eclipse.chemclipse.ux.extension.xxd.ui.l10n.ExtensionMessages;
import org.eclipse.chemclipse.ux.extension.xxd.ui.preferences.PreferenceConstants;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.core.support.WavelengthSupport;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

public class TracesSupport {

	private static final IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();

	public static void copyTracesToClipboard(Display display, Object scan) {

		String traces = null;
		int maxCopyTraces = getNumberOfTraces();
		boolean sortTraces = isSortTraces();
		IScan scanInstance = null;
		//
		if(scan instanceof IScanMSD scanMSD) {
			traces = ScanSupport.extractTracesText(scanMSD, maxCopyTraces, sortTraces);
			scanInstance = scanMSD;
		} else if(scan instanceof IScanWSD scanWSD) {
			traces = WavelengthSupport.extractTracesText(scanWSD, maxCopyTraces, sortTraces);
			scanInstance = scanWSD;
		}
		/*
		 * Copy to clipboard
		 */
		if(traces != null) {
			TracesExportOption tracesExportOption = TracesSupport.getTracesExportOption();
			switch(tracesExportOption) {
				case NAMED_TRACE:
					/*
					 * Styrene | 104 103 ...
					 */
					ILibraryInformation libraryInformation = IIdentificationTarget.getLibraryInformation(scanInstance);
					if(libraryInformation != null) {
						traces = libraryInformation.getName() + " | " + traces;
					} else {
						traces = ExtensionMessages.unknown + " | " + traces;
					}
					break;
				default:
					/*
					 * 104 103 ...
					 */
					break;
			}
			//
			TextTransfer textTransfer = TextTransfer.getInstance();
			Object[] data = new Object[]{traces};
			Transfer[] dataTypes = new Transfer[]{textTransfer};
			Clipboard clipboard = new Clipboard(display);
			clipboard.setContents(data, dataTypes);
			clipboard.dispose();
		}
	}

	private static int getNumberOfTraces() {

		return preferenceStore.getInt(PreferenceConstants.P_MAX_COPY_SCAN_TRACES);
	}

	private static boolean isSortTraces() {

		return preferenceStore.getBoolean(PreferenceConstants.P_SORT_COPY_TRACES);
	}

	private static TracesExportOption getTracesExportOption() {

		try {
			return TracesExportOption.valueOf(preferenceStore.getString(PreferenceConstants.P_TRACES_EXPORT_OPTION));
		} catch(Exception e) {
			return TracesExportOption.SIMPLE_TEXT;
		}
	}
}
