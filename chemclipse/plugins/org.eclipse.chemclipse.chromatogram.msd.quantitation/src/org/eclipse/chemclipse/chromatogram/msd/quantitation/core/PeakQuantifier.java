/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.quantitation.core;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;

import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.processing.IPeakQuantifierProcessingInfo;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.processing.PeakQuantifierProcessingInfo;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.settings.IPeakQuantifierSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.processing.core.IProcessingMessage;
import org.eclipse.chemclipse.processing.core.MessageType;
import org.eclipse.chemclipse.processing.core.ProcessingMessage;

public class PeakQuantifier {

	private static final Logger logger = Logger.getLogger(PeakQuantifier.class);
	private static final String EXTENSION_POINT = "org.eclipse.chemclipse.chromatogram.msd.quantifier.peakQuantifierSupplier";
	/*
	 * These are the attributes of the extension point elements.
	 */
	private static final String ID = "id";
	private static final String DESCRIPTION = "description";
	private static final String PEAK_QUANTIFIER_NAME = "peakQuantifierName";
	private static final String PEAK_QUANTIFIER = "peakQuantifier";
	/*
	 * Processing Info
	 */
	private static final String NO_PEAK_QUANTIFIER_AVAILABLE = "There is no peak quantifier available.";

	/**
	 * This class has only static methods.
	 */
	private PeakQuantifier() {
	}

	/**
	 * Quantifies the peak.
	 * 
	 * @param peak
	 * @param peakQuantifierSettings
	 * @param peakQuantifierId
	 * @param monitor
	 * @return IPeakQuantifierProcessingInfo
	 */
	public static IPeakQuantifierProcessingInfo quantify(IPeakMSD peak, IPeakQuantifierSettings peakQuantifierSettings, final String peakQuantifierId, IProgressMonitor monitor) {

		IPeakQuantifierProcessingInfo processingInfo;
		IPeakQuantifier peakQuantifier = getPeakQuantifier(peakQuantifierId);
		if(peakQuantifier != null) {
			processingInfo = peakQuantifier.quantify(peak, peakQuantifierSettings, monitor);
		} else {
			processingInfo = getNoPeakQuantifierProcessingInfo();
		}
		return processingInfo;
	}

	/**
	 * Quantifies the peak.
	 * 
	 * @param peak
	 * @param peakQuantifierId
	 * @param monitor
	 * @return {@link IPeakQuantifierProcessingInfo}
	 */
	public static IPeakQuantifierProcessingInfo quantify(IPeakMSD peak, final String peakQuantifierId, IProgressMonitor monitor) {

		IPeakQuantifierProcessingInfo processingInfo;
		IPeakQuantifier peakQuantifier = getPeakQuantifier(peakQuantifierId);
		if(peakQuantifier != null) {
			processingInfo = peakQuantifier.quantify(peak, monitor);
		} else {
			processingInfo = getNoPeakQuantifierProcessingInfo();
		}
		return processingInfo;
	}

	/**
	 * Quantifies the list of peaks.
	 * 
	 * @param peaks
	 * @param peakQuantifierSettings
	 * @param peakQuantifierId
	 * @param monitor
	 * @return IPeakQuantifierProcessingInfo
	 */
	public static IPeakQuantifierProcessingInfo quantify(List<IPeakMSD> peaks, IPeakQuantifierSettings peakQuantifierSettings, final String peakQuantifierId, IProgressMonitor monitor) {

		IPeakQuantifierProcessingInfo processingInfo;
		IPeakQuantifier peakQuantifier = getPeakQuantifier(peakQuantifierId);
		if(peakQuantifier != null) {
			processingInfo = peakQuantifier.quantify(peaks, peakQuantifierSettings, monitor);
		} else {
			processingInfo = getNoPeakQuantifierProcessingInfo();
		}
		return processingInfo;
	}

	/**
	 * Quantifies the list of peaks.
	 * 
	 * @param peaks
	 * @param peakQuantifierId
	 * @param monitor
	 * @return {@link IPeakQuantifierProcessingInfo}
	 */
	public static IPeakQuantifierProcessingInfo quantify(List<IPeakMSD> peaks, final String peakQuantifierId, IProgressMonitor monitor) {

		IPeakQuantifierProcessingInfo processingInfo;
		IPeakQuantifier peakQuantifier = getPeakQuantifier(peakQuantifierId);
		if(peakQuantifier != null) {
			processingInfo = peakQuantifier.quantify(peaks, monitor);
		} else {
			processingInfo = getNoPeakQuantifierProcessingInfo();
		}
		return processingInfo;
	}

	public static IPeakQuantifierSupport getPeakQuantifierSupport() {

		PeakQuantifierSupplier supplier;
		PeakQuantifierSupport baselineDetectorSupport = new PeakQuantifierSupport();
		/*
		 * Search in the extension registry and fill the comparison support
		 * object with supplier information.
		 */
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = registry.getConfigurationElementsFor(EXTENSION_POINT);
		for(IConfigurationElement element : extensions) {
			supplier = new PeakQuantifierSupplier();
			supplier.setId(element.getAttribute(ID));
			supplier.setDescription(element.getAttribute(DESCRIPTION));
			supplier.setPeakQuantifierName(element.getAttribute(PEAK_QUANTIFIER_NAME));
			baselineDetectorSupport.add(supplier);
		}
		return baselineDetectorSupport;
	}

	// --------------------------------------------private methods
	private static IPeakQuantifier getPeakQuantifier(final String peakQuantifierId) {

		IConfigurationElement element;
		element = getConfigurationElement(peakQuantifierId);
		IPeakQuantifier instance = null;
		if(element != null) {
			try {
				instance = (IPeakQuantifier)element.createExecutableExtension(PEAK_QUANTIFIER);
			} catch(CoreException e) {
				logger.warn(e);
			}
		}
		return instance;
	}

	/**
	 * Returns an IPeakQuantifier instance or null if none is available.
	 * 
	 * @param peakQuantifierId
	 * @return IConfigurationElement
	 */
	private static IConfigurationElement getConfigurationElement(final String peakQuantifierId) {

		if("".equals(peakQuantifierId)) {
			return null;
		}
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(EXTENSION_POINT);
		for(IConfigurationElement element : elements) {
			if(element.getAttribute(ID).equals(peakQuantifierId)) {
				return element;
			}
		}
		return null;
	}

	// --------------------------------------------private methods
	private static IPeakQuantifierProcessingInfo getNoPeakQuantifierProcessingInfo() {

		IPeakQuantifierProcessingInfo processingInfo = new PeakQuantifierProcessingInfo();
		IProcessingMessage processingMessage = new ProcessingMessage(MessageType.ERROR, "Peak Quantifier", NO_PEAK_QUANTIFIER_AVAILABLE);
		processingInfo.addMessage(processingMessage);
		return processingInfo;
	}
}
