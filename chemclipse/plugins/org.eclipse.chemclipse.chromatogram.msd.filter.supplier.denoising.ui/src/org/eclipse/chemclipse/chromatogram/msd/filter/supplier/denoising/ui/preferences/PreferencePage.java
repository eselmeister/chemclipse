/*******************************************************************************
 * Copyright (c) 2010, 2017 Lablicate GmbH.
 * 
 * All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.filter.supplier.denoising.ui.preferences;

import org.eclipse.chemclipse.chromatogram.msd.filter.supplier.denoising.preferences.PreferenceSupplier;
import org.eclipse.chemclipse.chromatogram.msd.filter.supplier.denoising.ui.Activator;
import org.eclipse.chemclipse.model.support.SegmentWidth;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Set the denoising filter settings.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		addField(new IonListEditor(PreferenceSupplier.P_IONS_TO_REMOVE, "Ions to remove in all cases", getFieldEditorParent()));
		addField(new IonListEditor(PreferenceSupplier.P_IONS_TO_PRESERVE, "Ions to preserve in all cases", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_ADJUST_THRESHOLD_TRANSITIONS, "Adjust threshold transitions.", getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_SEGMENT_WIDTH, "Segment width to determine noise.", SegmentWidth.getElements(), getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}
