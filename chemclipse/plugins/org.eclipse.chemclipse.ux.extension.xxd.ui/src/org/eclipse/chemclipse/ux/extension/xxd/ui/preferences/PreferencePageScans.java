/*******************************************************************************
 * Copyright (c) 2017, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.ux.extension.xxd.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.DoubleFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.ExtendedIntegerFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.LabelFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpacerFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpinnerFieldEditor;
import org.eclipse.chemclipse.ux.extension.xxd.ui.Activator;
import org.eclipse.chemclipse.ux.extension.xxd.ui.model.TracesExportOption;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ScanIdentifierUI;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swtchart.extensions.charts.ChartOptions;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePageScans extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePageScans() {

		super(FLAT);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Scans");
		setDescription("");
	}

	@Override
	public void createFieldEditors() {

		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new LabelFieldEditor("General chromatogram and scan axis titles.", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_TITLE_Y_AXIS_INTENSITY, "Intensity:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_TITLE_Y_AXIS_RELATIVE_INTENSITY, "Relative Intensity:", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new LabelFieldEditor("When changing the label fields, a restart is required.", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_TITLE_X_AXIS_MZ, "Ion [m/z]:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_TITLE_X_AXIS_PARENT_MZ, "Parent Ion [m/z]:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_TITLE_X_AXIS_PARENT_RESOLUTION, "Parent Resolution:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_TITLE_X_AXIS_DAUGHTER_MZ, "Daughter Ion [m/z]:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_TITLE_X_AXIS_DAUGHTER_RESOLUTION, "Daughter Resolution:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_TITLE_X_AXIS_COLLISION_ENERGY, "Collision Energy [eV]:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_TITLE_X_AXIS_WAVELENGTH, "Wavelength [nm]:", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_SCAN_LABEL_FONT_NAME, "Font Name:", getFieldEditorParent()));
		addField(new SpinnerFieldEditor(PreferenceSupplier.P_SCAN_LABEL_FONT_SIZE, "Font Size:", PreferenceSupplier.MIN_FONT_SIZE, PreferenceSupplier.MAX_FONT_SIZE, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_SCAN_LABEL_FONT_STYLE, "Font Style:", ChartOptions.FONT_STYLES, getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_SCAN_1, "Color Scan 1:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_SCAN_2, "Color Scan 2:", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new SpinnerFieldEditor(PreferenceSupplier.P_SCAN_LABEL_HIGHEST_INTENSITIES, "Label Intensities:", PreferenceSupplier.MIN_SCAN_LABEL_HIGHEST_INTENSITIES, PreferenceSupplier.MAX_SCAN_LABEL_HIGHEST_INTENSITIES, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SCAN_LABEL_MODULO_INTENSITIES, "Add additional intensity labels", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_AUTOFOCUS_SUBTRACT_SCAN_PART, "Autofocus subtract scan part", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SCAN_CHART_ENABLE_COMPRESS, "Enable Compress", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_SCAN_IDENTIFER_MSD, "Scan Identifier (MSD):", ScanIdentifierUI.getScanIdentifierMSD(), getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_SCAN_IDENTIFER_WSD, "Scan Identifier (WSD):", ScanIdentifierUI.getScanIdentifierWSD(), getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_ENABLE_MULTI_SUBTRACT, "Enable multi subtract modus", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SHOW_SUBTRACT_DIALOG, "Show subtract scan preferences dialog", getFieldEditorParent()));
		addField(new SpinnerFieldEditor(PreferenceSupplier.P_MAX_COPY_SCAN_TRACES, "Copy Traces", PreferenceSupplier.MIN_TRACES, PreferenceSupplier.MAX_TRACES, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SORT_COPY_TRACES, "Sort Traces", getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_TRACES_EXPORT_OPTION, "Traces Export Option", TracesExportOption.getOptions(), getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new LabelFieldEditor("The primary X|Y axis scale is used to set the values.", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SCAN_CHART_ENABLE_FIXED_RANGE_X, "Fixed range X", getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_SCAN_CHART_FIXED_RANGE_START_X, "Start X", PreferenceSupplier.MIN_RANGE, PreferenceSupplier.MAX_RANGE, getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_SCAN_CHART_FIXED_RANGE_STOP_X, "Stop X", PreferenceSupplier.MIN_RANGE, PreferenceSupplier.MAX_RANGE, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SCAN_CHART_ENABLE_FIXED_RANGE_Y, "Fixed range Y", getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_SCAN_CHART_FIXED_RANGE_START_Y, "Start Y", PreferenceSupplier.MIN_RANGE, PreferenceSupplier.MAX_RANGE, getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_SCAN_CHART_FIXED_RANGE_STOP_Y, "Stop Y", PreferenceSupplier.MIN_RANGE, PreferenceSupplier.MAX_RANGE, getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_TRACES_VIRTUAL_TABLE, "Traces Virtual Table", PreferenceSupplier.MIN_TRACES_VIRTUAL_TABLE, PreferenceSupplier.MAX_TRACES_VIRTUAL_TABLE, getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}
}
