/*******************************************************************************
 * Copyright (c) 2008, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.msd.model.core;

import org.eclipse.chemclipse.model.core.AbstractPeakModel;
import org.eclipse.chemclipse.model.core.IPeakIntensityValues;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.msd.model.implementation.PeakMassSpectrum;

public abstract class AbstractPeakModelMSD extends AbstractPeakModel implements IPeakModelMSD {

	public AbstractPeakModelMSD(IPeakMassSpectrum peakMaximum, IPeakIntensityValues peakIntensityValues, float startBackgroundAbundance, float stopBackgroundAbundance) throws IllegalArgumentException, PeakException {
		super(peakMaximum, peakIntensityValues, startBackgroundAbundance, stopBackgroundAbundance);
	}

	@Override
	public IPeakMassSpectrum getPeakMassSpectrum() {

		IScan peakMaximum = getPeakMaximum();
		if(peakMaximum instanceof IPeakMassSpectrum) {
			return (IPeakMassSpectrum)peakMaximum;
		}
		return null;
	}

	@Override
	public IPeakMassSpectrum getPeakMassSpectrum(int retentionTime) {

		IPeakMassSpectrum peakMaximum = getPeakMassSpectrum();
		if(peakMaximum != null) {
			/*
			 * Calculate the mass spectrum at a given retention time.
			 */
			if(retentionTime >= getStartRetentionTime() && retentionTime <= getStopRetentionTime()) {
				float intensity = getIntensity(retentionTime);
				if(intensity >= 0) {
					IPeakMassSpectrum ms = new PeakMassSpectrum(peakMaximum, intensity);
					return ms;
				}
			}
		}
		return null;
	}
}
