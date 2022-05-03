/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 * Philip Wenig - refactoring
 *******************************************************************************/
package org.eclipse.chemclipse.ux.extension.ui.swt;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.eclipse.chemclipse.model.notifier.UpdateNotifier;
import org.eclipse.chemclipse.processing.converter.ISupplier;
import org.eclipse.chemclipse.processing.converter.ISupplierFileIdentifier;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.ux.extension.ui.Activator;
import org.eclipse.chemclipse.ux.extension.ui.preferences.PreferenceConstants;
import org.eclipse.chemclipse.ux.extension.ui.preferences.PreferenceSupplier;
import org.eclipse.chemclipse.ux.extension.ui.provider.DataExplorerContentProvider;
import org.eclipse.chemclipse.ux.extension.ui.provider.ISupplierFileEditorSupport;
import org.eclipse.chemclipse.ux.extension.ui.provider.LazyFileExplorerContentProvider;
import org.eclipse.chemclipse.xxd.process.files.SupplierFileIdentifierCache;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class MultiDataExplorerTreeUI {

	private static final String TAB_KEY_SUFFIX = "selectedTab";
	//
	private final TabFolder tabFolder;
	private final DataExplorerTreeUI[] dataExplorerTreeUIs;
	private final IPreferenceStore preferenceStore;
	//
	private final SupplierFileIdentifierCache supplierFileIdentifierCache = new SupplierFileIdentifierCache(LazyFileExplorerContentProvider.MAX_CACHE_SIZE);

	public MultiDataExplorerTreeUI(Composite parent, IPreferenceStore preferenceStore) {

		this(parent, DataExplorerTreeRoot.getDefaultRoots(), preferenceStore);
	}

	public MultiDataExplorerTreeUI(Composite parent, DataExplorerTreeRoot[] roots, IPreferenceStore preferenceStore) {

		this.preferenceStore = preferenceStore;
		//
		tabFolder = new TabFolder(parent, SWT.NONE);
		dataExplorerTreeUIs = new DataExplorerTreeUI[roots.length];
		for(int i = 0; i < roots.length; i++) {
			dataExplorerTreeUIs[i] = createDataExplorerTreeUI(tabFolder, roots[i]);
		}
	}

	public void setFocus() {

		tabFolder.setFocus();
		for(TabItem item : tabFolder.getSelection()) {
			item.getControl().setFocus();
		}
	}

	public Control getControl() {

		return tabFolder;
	}

	public void setSupplierFileIdentifier(Collection<? extends ISupplierFileIdentifier> supplierFileEditorSupportList) {

		supplierFileIdentifierCache.setIdentifier(supplierFileEditorSupportList);
		for(DataExplorerTreeUI dataExplorerTreeUI : dataExplorerTreeUIs) {
			dataExplorerTreeUI.getTreeViewer().refresh();
		}
	}

	public void expandLastDirectoryPath() {

		for(DataExplorerTreeUI dataExplorerTreeUI : dataExplorerTreeUIs) {
			String preferenceKey = getPreferenceKey(dataExplorerTreeUI.getRoot());
			dataExplorerTreeUI.expandLastDirectoryPath(preferenceStore, preferenceKey);
		}
	}

	public void saveLastDirectoryPath() {

		for(DataExplorerTreeUI dataExplorerTreeUI : dataExplorerTreeUIs) {
			dataExplorerTreeUI.saveLastDirectoryPath(preferenceStore, getPreferenceKey(dataExplorerTreeUI.getRoot()));
		}
		//
		int index = tabFolder.getSelectionIndex();
		preferenceStore.setValue(getSelectedTabPreferenceKey(), index);
		if(preferenceStore.needsSaving()) {
			if(preferenceStore instanceof IPersistentPreferenceStore) {
				try {
					((IPersistentPreferenceStore)preferenceStore).save();
				} catch(IOException e) {
					Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), "Storing preferences failed", e));
				}
			}
		}
	}

	protected Function<File, Map<ISupplierFileIdentifier, Collection<ISupplier>>> getIdentifierSupplier() {

		return supplierFileIdentifierCache;
	}

	protected void handleDoubleClick(File file, DataExplorerTreeUI treeUI) {

		openEditor(file, treeUI);
	}

	protected void handleSelection(File[] files, DataExplorerTreeUI treeUI) {

		if(files.length > 0) {
			openOverview(files[0], treeUI);
		}
	}

	protected String getSelectedTabPreferenceKey() {

		return getPreferenceKey(DataExplorerTreeRoot.USER_LOCATION) + TAB_KEY_SUFFIX;
	}

	protected String getUserLocationPreferenceKey() {

		return PreferenceConstants.P_USER_LOCATION_PATH;
	}

	protected String getPreferenceKey(DataExplorerTreeRoot root) {

		return root.getPreferenceKeyDefaultPath();
	}

	protected void createToolbarMain(Composite parent) {

	}

	protected void initTabComponent(Composite parent, DataExplorerTreeUI treeUI) {

		if(treeUI.getRoot() == DataExplorerTreeRoot.USER_LOCATION) {
			addUserLocationButton(parent, treeUI);
			File directory = new File(preferenceStore.getString(getUserLocationPreferenceKey()));
			if(directory.exists()) {
				treeUI.getTreeViewer().setInput(new File[]{directory});
			}
		}
		createContextMenu(treeUI);
		addBatchOpenButton(parent, treeUI);
	}

	private DataExplorerTreeUI createDataExplorerTreeUI(TabFolder tabFolder, DataExplorerTreeRoot root) {

		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(root.toString());
		//
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout());
		createToolbarMain(composite);
		//
		DataExplorerTreeUI dataExplorerTreeUI = new DataExplorerTreeUI(composite, root, getIdentifierSupplier());
		TreeViewer treeViewer = dataExplorerTreeUI.getTreeViewer();
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		ISelectionChangedListener selectionChangedListener = new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				Object[] array = treeViewer.getStructuredSelection().toArray();
				File[] files = new File[array.length];
				for(int i = 0; i < files.length; i++) {
					files[i] = (File)array[i];
				}
				handleSelection(files, dataExplorerTreeUI);
			}
		};
		//
		treeViewer.addSelectionChangedListener(selectionChangedListener);
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {

				File file = (File)((IStructuredSelection)event.getSelection()).getFirstElement();
				handleDoubleClick(file, dataExplorerTreeUI);
			}
		});
		//
		initTabComponent(composite, dataExplorerTreeUI);
		tabItem.setControl(composite);
		tabFolder.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				TabItem[] selection = tabFolder.getSelection();
				for(TabItem item : selection) {
					if(item == tabItem) {
						selectionChangedListener.selectionChanged(null);
					}
				}
			}
		});
		//
		return dataExplorerTreeUI;
	}

	private void addUserLocationButton(Composite parent, DataExplorerTreeUI treeUI) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select User Location");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_FOLDER_OPENED, IApplicationImage.SIZE_16x16));
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				DirectoryDialog directoryDialog = new DirectoryDialog(e.display.getActiveShell(), SWT.READ_ONLY);
				directoryDialog.setText("Select a directory.");
				String pathname = directoryDialog.open();
				if(pathname != null) {
					File directory = new File(pathname);
					if(directory.exists()) {
						preferenceStore.setValue(getUserLocationPreferenceKey(), directory.getAbsolutePath());
						treeUI.getTreeViewer().setInput(new File[]{directory});
					}
				}
			}
		});
	}

	private void createContextMenu(DataExplorerTreeUI treeUI) {

		TreeViewer treeViewer = treeUI.getTreeViewer();
		MenuManager contextMenu = new MenuManager("#ViewerMenu"); //$NON-NLS-1$
		contextMenu.setRemoveAllWhenShown(true);
		contextMenu.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager mgr) {

				Object[] selection = treeViewer.getStructuredSelection().toArray();
				Map<File, Map<ISupplierFileIdentifier, Collection<ISupplier>>> converterSupplier = new HashMap<>();
				Set<ISupplier> supplierSet = new TreeSet<>(new Comparator<ISupplier>() {

					@Override
					public int compare(ISupplier o1, ISupplier o2) {

						return o1.getId().compareTo(o2.getId());
					}
				});
				//
				for(Object object : selection) {
					if(object instanceof File) {
						File file = (File)object;
						Map<ISupplierFileIdentifier, Collection<ISupplier>> map = getIdentifierSupplier().apply(file);
						converterSupplier.put(file, map);
						for(Collection<ISupplier> s : map.values()) {
							supplierSet.addAll(s);
						}
					}
				}
				contextMenu.add(new Action("Scan for file and folder updates", ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_REFRESH, IApplicationImage.SIZE_16x16)) {

					@Override
					public void run() {

						treeViewer.refresh();
					}
				});
				//
				for(ISupplier activeFileSupplier : supplierSet) {
					contextMenu.add(new Action("Open as: " + activeFileSupplier.getFilterName(), ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_FILE, IApplicationImage.SIZE_16x16)) {

						@Override
						public void run() {

							outer:
							for(Object object : selection) {
								if(object instanceof File) {
									File file = (File)object;
									Map<ISupplierFileIdentifier, Collection<ISupplier>> map = converterSupplier.get(file);
									for(Entry<ISupplierFileIdentifier, Collection<ISupplier>> entry : map.entrySet()) {
										ISupplierFileIdentifier identifier = entry.getKey();
										if(identifier instanceof ISupplierFileEditorSupport) {
											for(ISupplier supplier : entry.getValue()) {
												if(activeFileSupplier.getId().equals(supplier.getId())) {
													openEditorWithSupplier(file, (ISupplierFileEditorSupport)identifier, supplier);
													continue outer;
												}
											}
										}
									}
								}
							}
						}

						@Override
						public String getToolTipText() {

							return activeFileSupplier.getDescription();
						};
					});
				}
				//
				if(selection.length == 1 && selection[0] instanceof File && ((File)selection[0]).isDirectory()) {
					contextMenu.add(new Action("Open all contained measurements in this folder", ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_FOLDER, IApplicationImage.SIZE_16x16)) {

						@Override
						public void run() {

							openRecurse((File)selection[0], treeUI);
						}
					});
				}
			}
		});
		//
		Menu menu = contextMenu.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
	}

	private boolean openRecurse(File file, DataExplorerTreeUI treeUI) {

		boolean opened = false;
		File[] listFiles = file.listFiles();
		if(listFiles != null) {
			for(File f : listFiles) {
				opened |= openEditor(f, treeUI);
			}
			if(!opened) {
				for(File f : listFiles) {
					if(f.isDirectory()) {
						// recurse into sub-directory...
						opened |= openRecurse(f, treeUI);
					}
				}
			}
		}
		return opened;
	};

	private void addBatchOpenButton(Composite parent, DataExplorerTreeUI treeUI) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Open Selected Measurements");
		button.setToolTipText("Try to open all selected files. Handle with care.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_IMPORT, IApplicationImage.SIZE_16x16));
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.addSelectionListener(new SelectionAdapter() {

			@SuppressWarnings("rawtypes")
			@Override
			public void widgetSelected(SelectionEvent e) {

				IStructuredSelection structuredSelection = treeUI.getTreeViewer().getStructuredSelection();
				Iterator iterator = structuredSelection.iterator();
				while(iterator.hasNext()) {
					Object object = iterator.next();
					if(object instanceof File) {
						e.display.asyncExec(new Runnable() {

							@Override
							public void run() {

								File file = (File)object;
								openEditor(file, treeUI);
							}
						});
					}
				}
			}
		});
	}

	private void openOverview(File file, DataExplorerTreeUI dataExplorerTreeUI) {

		if(file != null) {
			DataExplorerContentProvider contentProvider = (DataExplorerContentProvider)dataExplorerTreeUI.getTreeViewer().getContentProvider();
			/*
			 * Update the directories content, until there is
			 * actual no way to monitor the file system outside
			 * of the workbench without using operating system
			 * specific function via e.g. JNI.
			 */
			if(file.isDirectory()) {
				contentProvider.refresh(file);
			}
			//
			Collection<ISupplierFileIdentifier> identifiers = getIdentifierSupplier().apply(file).keySet();
			for(ISupplierFileIdentifier identifier : identifiers) {
				if(identifier instanceof ISupplierFileEditorSupport) {
					ISupplierFileEditorSupport fileEditorSupport = (ISupplierFileEditorSupport)identifier;
					fileEditorSupport.openOverview(file);
					return;
				}
			}
			//
			UpdateNotifier.update(IChemClipseEvents.TOPIC_CHROMATOGRAM_XXD_UPDATE_NONE, null);
		}
	}

	private boolean openEditor(File file, DataExplorerTreeUI treeUI) {

		boolean success = false;
		if(file != null) {
			boolean openFirstDataMatchOnly = PreferenceSupplier.isOpenFirstDataMatchOnly();
			Map<ISupplierFileIdentifier, Collection<ISupplier>> identifiers = getIdentifierSupplier().apply(file);
			for(Entry<ISupplierFileIdentifier, Collection<ISupplier>> entry : identifiers.entrySet()) {
				ISupplierFileIdentifier identifier = entry.getKey();
				if(identifier instanceof ISupplierFileEditorSupport) {
					for(ISupplier converter : entry.getValue()) {
						success = success | openEditorWithSupplier(file, (ISupplierFileEditorSupport)identifier, converter);
						if(success && openFirstDataMatchOnly) {
							return true;
						}
					}
				}
			}
		}
		return success;
	}

	private boolean openEditorWithSupplier(File file, ISupplierFileEditorSupport identifier, ISupplier converter) {

		saveLastDirectoryPath();
		return identifier.openEditor(file, converter);
	}
}
