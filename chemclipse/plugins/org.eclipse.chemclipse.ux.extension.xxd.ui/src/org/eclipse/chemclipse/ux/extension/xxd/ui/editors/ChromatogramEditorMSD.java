/*******************************************************************************
 * Copyright (c) 2018, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.ux.extension.xxd.ui.editors;

import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.processing.ui.E4ProcessSupplierContext;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import jakarta.inject.Inject;

public class ChromatogramEditorMSD extends ChromatogramEditor {

	public static final String ID = "org.eclipse.chemclipse.ux.extension.xxd.ui.part.chromatogramEditorMSD";
	public static final String CONTRIBUTION_URI = "bundleclass://org.eclipse.chemclipse.ux.extension.xxd.ui/org.eclipse.chemclipse.ux.extension.xxd.ui.editors.ChromatogramEditorMSD";

	@Inject
	public ChromatogramEditorMSD(Composite parent, MPart part, MDirtyable dirtyable, Shell shell, E4ProcessSupplierContext processSupplierContext, IEclipseContext eclipseContext) {

		super(DataType.MSD, parent, part, dirtyable, shell, processSupplierContext, eclipseContext);
	}
}
