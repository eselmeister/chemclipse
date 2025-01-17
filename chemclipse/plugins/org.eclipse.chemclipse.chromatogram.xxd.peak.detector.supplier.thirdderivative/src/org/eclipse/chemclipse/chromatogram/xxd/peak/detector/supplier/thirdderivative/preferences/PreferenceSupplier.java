/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.xxd.peak.detector.supplier.thirdderivative.preferences;

import org.eclipse.chemclipse.chromatogram.xxd.peak.detector.supplier.thirdderivative.Activator;
import org.eclipse.chemclipse.chromatogram.xxd.peak.detector.supplier.thirdderivative.settings.PeakDetectorSettings;
import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_THRESHOLD = "threshold";
	public static final String P_INCLUDE_BACKGROUND = "includeBackground";
	public static final boolean DEF_INCLUDE_BACKGROUND = false; // false will use BV oder VB, if true VV will be used.
	public static final String P_MIN_SN_RATIO = "minSNRatio";
	public static final float DEF_MIN_SN_RATIO = 0.0f; // 0 = all peaks will be added

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_MIN_SN_RATIO, Float.toString(DEF_MIN_SN_RATIO));
		putDefault(P_INCLUDE_BACKGROUND, Boolean.toString(DEF_INCLUDE_BACKGROUND));
	}

	public static PeakDetectorSettings getPeakDetectorSettings() {

		PeakDetectorSettings peakDetectorSettings = new PeakDetectorSettings();
		peakDetectorSettings.setIncludeBackground(INSTANCE().getBoolean(P_INCLUDE_BACKGROUND, DEF_INCLUDE_BACKGROUND));
		peakDetectorSettings.setMinimumSignalToNoiseRatio(INSTANCE().getFloat(P_MIN_SN_RATIO, DEF_MIN_SN_RATIO));
		return peakDetectorSettings;
	}
}