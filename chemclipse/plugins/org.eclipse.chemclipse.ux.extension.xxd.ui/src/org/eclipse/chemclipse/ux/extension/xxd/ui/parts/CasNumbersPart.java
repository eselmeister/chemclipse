/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.ux.extension.xxd.ui.parts;

import javax.inject.Inject;

import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ExtendedCasNumbersUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class CasNumbersPart extends AbstractLibraryInformationPart<ExtendedCasNumbersUI> {

	@Inject
	public CasNumbersPart(Composite parent) {

		super(parent);
	}

	@Override
	protected ExtendedCasNumbersUI createControl(Composite parent) {

		return new ExtendedCasNumbersUI(parent, SWT.NONE);
	}
}