/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.converter.methods;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.chemclipse.converter.core.IExportConverter;
import org.eclipse.chemclipse.model.methods.ProcessMethod;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IMethodExportConverter extends IExportConverter {

	IProcessingInfo convert(File file, ProcessMethod processMethod, IProgressMonitor monitor);

	/**
	 * writes the given method to the {@link OutputStream}
	 * 
	 * @param stream
	 * @param nameHint
	 * @param monitor
	 * @return a {@link IProcessingInfo} containing the result of the conversion process
	 * @throws IOException
	 *             in case of an IOError while reading streams
	 */
	default IProcessingInfo convert(OutputStream stream, String nameHint, ProcessMethod processMethod, IProgressMonitor monitor) throws IOException {

		ProcessingInfo processingInfo = new ProcessingInfo();
		processingInfo.addErrorMessage("Can't write " + nameHint, "This Converter does currentyl not supports the new stream API");
		return processingInfo;
	}
}
