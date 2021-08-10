/*******************************************************************************
 * Copyright (c) 2016, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - auto detection for chromatography files
 *******************************************************************************/
package org.eclipse.chemclipse.msd.converter.supplier.mzml.converter;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.eclipse.chemclipse.msd.converter.supplier.mzml.internal.converter.IConstants;
import org.eclipse.chemclipse.msd.converter.supplier.mzml.internal.converter.SpecificationValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ChromatogramMagicNumberMatcher extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		try {
			file = SpecificationValidator.validateSpecification(file);
			if(!file.exists()) {
				return isValidFormat;
			}
			if(!checkFileExtension(file, ".mzML")) {
				return isValidFormat;
			}
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList root = document.getElementsByTagName(IConstants.NODE_MZML);
			if(root.getLength() != 1) {
				return isValidFormat;
			}
			NodeList chromatogramList = document.getElementsByTagName(IConstants.NODE_CHROMATOGRAM_LIST);
			if(chromatogramList.getLength() > 0) {
				Element element = (Element)chromatogramList.item(0);
				int chromatogramCount = Integer.parseInt(element.getAttribute("count"));
				if(chromatogramCount > 0) {
					isValidFormat = true;
				}
			}
		} catch(Exception e) {
			// fail silently
		}
		return isValidFormat;
	}
}