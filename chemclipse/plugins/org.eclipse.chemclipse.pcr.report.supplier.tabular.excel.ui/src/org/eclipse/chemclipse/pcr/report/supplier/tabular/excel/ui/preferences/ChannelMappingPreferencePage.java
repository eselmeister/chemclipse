/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.pcr.report.supplier.tabular.excel.ui.preferences;

import org.eclipse.chemclipse.pcr.report.supplier.tabular.excel.preferences.PreferenceSupplier;
import org.eclipse.chemclipse.pcr.report.supplier.tabular.excel.ui.Activator;
import org.eclipse.chemclipse.pcr.report.supplier.tabular.ui.editors.ChannelMappingFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class ChannelMappingPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public ChannelMappingPreferencePage() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Exports plates into *.xlsx reports according to these rules.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		addField(new ChannelMappingFieldEditor(PreferenceSupplier.P_CHANNEL_MAPPING, "", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {

	}
}