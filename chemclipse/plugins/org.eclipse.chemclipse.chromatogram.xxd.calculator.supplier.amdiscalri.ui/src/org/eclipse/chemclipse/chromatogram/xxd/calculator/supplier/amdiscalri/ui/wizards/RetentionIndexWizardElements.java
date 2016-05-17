/*******************************************************************************
 * Copyright (c) 2016 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.xxd.calculator.supplier.amdiscalri.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.calculator.supplier.amdiscalri.io.StandardsReader;
import org.eclipse.chemclipse.chromatogram.xxd.calculator.supplier.amdiscalri.model.IRetentionIndexEntry;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.support.ui.wizards.ChromatogramWizardElements;

public class RetentionIndexWizardElements extends ChromatogramWizardElements implements IRetentionIndexWizardElements {

	private List<IRetentionIndexEntry> retentionIndexEntries;
	private String[] availableStandards;
	/*
	 * Settings
	 */
	private boolean useExistingRetentionIndexFile;
	private String filterPathRetentionIndexFile; // used for File Dialog
	private String pathRetentionIndexFile;
	private String startIndexName;
	private String stopIndexName;
	private boolean useAlreadyDetectedPeaks;
	//
	private IChromatogramSelectionMSD chromatogramSelectionMSD;
	private List<IRetentionIndexEntry> extractedRetentionIndexEntries;

	public RetentionIndexWizardElements() {
		initialize();
	}

	@Override
	public float getRetentionIndex(String name) {

		float retentionIndex = 0.0f;
		for(IRetentionIndexEntry retentionIndexEntry : retentionIndexEntries) {
			if(retentionIndexEntry.getName().equals(name)) {
				return retentionIndexEntry.getRetentionIndex();
			}
		}
		return retentionIndex;
	}

	@Override
	public String[] getAvailableStandards() {

		return availableStandards;
	}

	@Override
	public boolean isUseExistingRetentionIndexFile() {

		return useExistingRetentionIndexFile;
	}

	@Override
	public void setUseExistingRetentionIndexFile(boolean useExistingRetentionIndexFile) {

		this.useExistingRetentionIndexFile = useExistingRetentionIndexFile;
	}

	@Override
	public String getFilterPathCalibrationFile() {

		return filterPathRetentionIndexFile;
	}

	@Override
	public void setFilterPathCalibrationFile(String filterPathCalibrationFile) {

		this.filterPathRetentionIndexFile = filterPathCalibrationFile;
	}

	@Override
	public String getPathRetentionIndexFile() {

		return pathRetentionIndexFile;
	}

	@Override
	public void setPathRetentionIndexFile(String pathRetentionIndexFile) {

		this.pathRetentionIndexFile = pathRetentionIndexFile;
	}

	@Override
	public String getStartIndexName() {

		return startIndexName;
	}

	@Override
	public void setStartIndexName(String startIndexName) {

		this.startIndexName = startIndexName;
	}

	@Override
	public String getStopIndexName() {

		return stopIndexName;
	}

	@Override
	public void setStopIndexName(String stopIndexName) {

		this.stopIndexName = stopIndexName;
	}

	@Override
	public boolean isUseAlreadyDetectedPeaks() {

		return useAlreadyDetectedPeaks;
	}

	@Override
	public void setUseAlreadyDetectedPeaks(boolean useAlreadyDetectedPeaks) {

		this.useAlreadyDetectedPeaks = useAlreadyDetectedPeaks;
	}

	@Override
	public IChromatogramSelectionMSD getChromatogramSelectionMSD() {

		return chromatogramSelectionMSD;
	}

	@Override
	public void setChromatogramSelectionMSD(IChromatogramSelectionMSD chromatogramSelectionMSD) {

		this.chromatogramSelectionMSD = chromatogramSelectionMSD;
	}

	@Override
	public List<IRetentionIndexEntry> getExtractedRetentionIndexEntries() {

		return extractedRetentionIndexEntries;
	}

	@Override
	public void setExtractedRetentionIndexEntries(List<IRetentionIndexEntry> extractedRetentionIndexEntries) {

		this.extractedRetentionIndexEntries = extractedRetentionIndexEntries;
	}

	private void initialize() {

		StandardsReader standardsReader = new StandardsReader();
		retentionIndexEntries = standardsReader.getStandardsList();
		int size = retentionIndexEntries.size();
		availableStandards = new String[size];
		for(int i = 0; i < size; i++) {
			availableStandards[i] = retentionIndexEntries.get(i).getName();
		}
		//
		useExistingRetentionIndexFile = false;
		pathRetentionIndexFile = "";
		startIndexName = "";
		stopIndexName = "";
		useAlreadyDetectedPeaks = false;
		//
		extractedRetentionIndexEntries = new ArrayList<IRetentionIndexEntry>();
	}
}