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
package org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.file.ui.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IdentifierFileListUtil {

	public static final String SEPARATOR_TOKEN = ";";

	public String createList(String[] items) {

		List<String> fileList = getFileList(items);
		String files = "";
		for(String file : fileList) {
			files = files.concat(file + SEPARATOR_TOKEN);
		}
		return files;
	}

	public String[] parseString(String stringList) {

		String[] decodedArray;
		if(stringList.contains(SEPARATOR_TOKEN)) {
			decodedArray = stringList.split(SEPARATOR_TOKEN);
		} else {
			decodedArray = new String[0];
		}
		return decodedArray;
	}

	public List<String> getFiles(String preferenceEntry) {

		List<String> files = new ArrayList<String>();
		if(preferenceEntry != "") {
			String[] items = parseString(preferenceEntry);
			if(items.length > 0) {
				for(String item : items) {
					files.add(item);
				}
			}
		}
		Collections.sort(files);
		return files;
	}

	private List<String> getFileList(String[] items) {

		List<String> files = new ArrayList<String>();
		if(items != null) {
			int size = items.length;
			for(int i = 0; i < size; i++) {
				files.add(items[i]);
			}
		}
		return files;
	}
}
