/*******************************************************************************
 * Copyright (c) 2020, 2024 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Lorenz Gerber - initial API and implementation, IonRange
 * Philip Wenig - add ion round call
 *******************************************************************************/
package org.eclipse.chemclipse.msd.model.matrix;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IIonBounds;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.xic.IonRange;

public class ExtractedMatrix {

	private IChromatogramSelectionMSD selection;
	private List<IScanMSD> scans;
	private IonRange minMaxIon;
	private double[][] signal;

	public ExtractedMatrix(IChromatogramSelectionMSD chromatogramSelection) {

		this.selection = chromatogramSelection;
		this.scans = extractScans();
		if(checkHighRes(10)) {
			throw new IllegalArgumentException("HighRes MSD is currently not suported");
		} else {
			minMaxIon = getMinMaxMz();
			int numberOfScans = selection.getStopScan() - selection.getStartScan() + 1;
			int numberOfIons = this.minMaxIon.getStopIon() - this.minMaxIon.getStartIon() + 1;
			signal = new double[numberOfScans][numberOfIons];
			List<IIon> currentIons;
			for(int scanIndex = 0; scanIndex < numberOfScans; scanIndex++) {
				currentIons = scans.get(scanIndex).getIons();
				for(IIon ion : currentIons) {
					signal[scanIndex][AbstractIon.getIon(ion.getIon() - this.minMaxIon.getStartIon())] = ion.getAbundance();
				}
			}
		}
	}

	private Boolean checkHighRes(int limit) {

		IIonBounds bounds;
		double rangeAbs;
		for(IScanMSD scan : scans) {
			bounds = scan.getIonBounds();
			if(bounds != null) {
				rangeAbs = bounds.getHighestIon().getIon() - bounds.getLowestIon().getIon();
				if(rangeAbs + limit < scan.getIons().size()) {
					return (true);
				}
			}
		}
		return false;
	}

	private List<IScanMSD> extractScans() {

		List<IScanMSD> scans;
		int startScan;
		int stopScan;
		startScan = selection.getStartScan();
		stopScan = selection.getStopScan();
		scans = selection.getChromatogram() //
				.getScans() //
				.stream() //
				.filter(IScanMSD.class::isInstance) //
				.map(IScanMSD.class::cast) //
				.filter(s -> s.getScanNumber() >= startScan) //
				.filter(s -> s.getScanNumber() <= stopScan) //
				.collect(Collectors.toList()); //
		return scans;
	}

	private IonRange getMinMaxMz() {

		Stream<Double> s = scans.stream() //
				.flatMap(scan -> scan.getIons().stream()) //
				.map(x -> (Double)x.getIon());
		DoubleSummaryStatistics d = s.collect(Collectors.summarizingDouble(value -> value));
		return new IonRange(AbstractIon.getIon(d.getMin()), AbstractIon.getIon(d.getMax()));
	}

	/**
	 * Returns the scans/ions array
	 * Scans along rows, ions along columns
	 * 
	 * @return data array scans x ions.
	 */
	public double[][] getMatrix() {

		return this.signal;
	}

	public void updateSignal() {

		IScanMSD currentScan;
		IIon currentIon;
		int startScan = selection.getStartScan();
		int stopScan = selection.getStopScan();
		try {
			for(int i = startScan; i <= stopScan; i++) {
				currentScan = (IScanMSD)selection.getChromatogram().getScan(i);
				currentScan.removeAllIons();
				for(int j = minMaxIon.getStartIon(); j <= minMaxIon.getStopIon(); j++) {
					if(signal[i - startScan][j - minMaxIon.getStartIon()] != 0.0) {
						currentIon = new Ion(j, (float)signal[i - startScan][j - minMaxIon.getStartIon()]);
						currentScan.addIon(currentIon);
					}
				}
			}
		} catch(Exception e) {
			throw new RuntimeException("Updating the Signal failed:", e);
		}
	}

	public int[] getScanNumbers() {

		return scans.stream().mapToInt(IScan::getScanNumber).toArray();
	}

	public int[] getRetentionTimes() {

		return scans.stream().mapToInt(IScan::getRetentionTime).toArray();
	}

	public IonRange getMinMaxIon() {

		return minMaxIon;
	}
}
