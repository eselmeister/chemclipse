/*******************************************************************************
 * Copyright (c) 2018, 2020 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.ux.extension.xxd.ui.preferences;

import org.eclipse.chemclipse.ux.extension.xxd.ui.toolbar.GroupHandler;
import org.eclipse.chemclipse.ux.extension.xxd.ui.toolbar.GroupHandlerESTD;

public class PreferencePageTaskESTD extends AbstractPreferencePageTask {

	public PreferencePageTaskESTD() {

		super(GroupHandler.getGroupHandler(GroupHandlerESTD.NAME));
	}
}
