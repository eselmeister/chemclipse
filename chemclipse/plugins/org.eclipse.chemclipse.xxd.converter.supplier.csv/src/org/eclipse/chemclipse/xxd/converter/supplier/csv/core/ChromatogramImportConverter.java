/*******************************************************************************
 * Copyright (c) 2011, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - add generics
 *******************************************************************************/
package org.eclipse.chemclipse.xxd.converter.supplier.csv.core;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramImportConverter;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.xxd.converter.supplier.csv.io.core.ChromatogramReader;
import org.eclipse.chemclipse.xxd.converter.supplier.csv.io.core.ChromatogramWriter;
import org.eclipse.core.runtime.IProgressMonitor;

public class ChromatogramImportConverter extends AbstractChromatogramImportConverter<IChromatogramMSD> {

	private static final Logger logger = Logger.getLogger(ChromatogramImportConverter.class);
	private static final String DESCRIPTION = "CSV Import Converter";

	@Override
	public IProcessingInfo<IChromatogramMSD> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramMSD> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			try {
				if(file.getName().toLowerCase().endsWith(ChromatogramWriter.FILE_EXTENSION)) {
					IChromatogramMSDReader reader = new ChromatogramReader();
					IChromatogramMSD chromatogram = reader.read(file, monitor);
					processingInfo.setProcessingResult(chromatogram);
				}
			} catch(InterruptedException e) {
				logger.warn(e);
				Thread.currentThread().interrupt();
				processingInfo.addErrorMessage(DESCRIPTION, "Failed to convert: " + file.getAbsolutePath());
			} catch(IOException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Failed to convert: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<IChromatogramOverview> convertOverview(File file, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramOverview> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			try {
				if(file.getName().toLowerCase().endsWith(ChromatogramWriter.FILE_EXTENSION)) {
					IChromatogramMSDReader reader = new ChromatogramReader();
					IChromatogramOverview chromatogramOverview = reader.readOverview(file, monitor);
					processingInfo.setProcessingResult(chromatogramOverview);
				}
			} catch(IOException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}