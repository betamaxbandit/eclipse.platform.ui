/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.views.internal.markers.problems;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


class ProblemFiltersDialog extends org.eclipse.ui.views.internal.markers.FiltersDialog {

	private DescriptionGroup descriptionGroup;
	private SeverityGroup severityGroup;
	
	private class DescriptionGroup {
		private Label descriptionLabel;
		private Combo combo;
		private Text description;
		private String contains = Messages.getString("filtersDialog.contains"); //$NON-NLS-1$
		private String doesNotContain = Messages.getString("filtersDialog.doesNotContain"); //$NON-NLS-1$
		public DescriptionGroup(Composite parent) {
			descriptionLabel = new Label(parent, SWT.NONE);
			descriptionLabel.setFont(parent.getFont());
			descriptionLabel.setText(Messages.getString("filtersDialog.descriptionLabel")); //$NON-NLS-1$
			
			combo = new Combo(parent, SWT.READ_ONLY);
			combo.setFont(parent.getFont());
			combo.add(contains);
			combo.add(doesNotContain);
			combo.addSelectionListener(selectionListener);
			// Prevent Esc and Return from closing the dialog when the combo is active.
			combo.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_ESCAPE || e.detail == SWT.TRAVERSE_RETURN) {
						e.doit = false;
					}
				}
			});
			
			description = new Text(parent, SWT.SINGLE | SWT.BORDER);
			description.setFont(parent.getFont());
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 3;
			description.setLayoutData(data);
			description.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					ProblemFiltersDialog.this.markDirty();
				}
			});
		}
		public boolean getContains() {
			return combo.getSelectionIndex() == combo.indexOf(contains);
		}
		public void setContains(boolean value) {
			if (value) {
				combo.select(combo.indexOf(contains));
			}
			else {
				combo.select(combo.indexOf(doesNotContain));
			}
		}
		public void setDescription(String text) {
			if (text == null) {
				description.setText(""); //$NON-NLS-1$ 
			}
			else {
				description.setText(text);
			}
		}
		public String getDescription() {
			return description.getText();
		}
		public void updateEnablement() {
			descriptionLabel.setEnabled(isFilterEnabled());
			combo.setEnabled(isFilterEnabled());
			description.setEnabled(isFilterEnabled());
		}
	}
	
	private class SeverityGroup {
		private Button enablementButton;
		private Button errorButton;
		private Button warningButton;
		private Button infoButton;
		public SeverityGroup(Composite parent) {
			SelectionListener listener = new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					updateEnablement();
					ProblemFiltersDialog.this.markDirty();
				}
			};
			
			enablementButton = new Button(parent, SWT.CHECK);
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;
			enablementButton.setLayoutData(data);
			enablementButton.setFont(parent.getFont());
			enablementButton.setText(Messages.getString("filtersDialog.severityLabel")); //$NON-NLS-1$
			enablementButton.addSelectionListener(listener);
			
			errorButton = new Button(parent, SWT.CHECK);
			errorButton.setFont(parent.getFont());
			errorButton.setText(Messages.getString("filtersDialog.severityError")); //$NON-NLS-1$
			errorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			errorButton.addSelectionListener(selectionListener);
			
			warningButton = new Button(parent, SWT.CHECK);
			warningButton.setFont(parent.getFont());
			warningButton.setText(Messages.getString("filtersDialog.severityWarning")); //$NON-NLS-1$
			warningButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			warningButton.addSelectionListener(selectionListener);
			
			infoButton = new Button(parent, SWT.CHECK);
			infoButton.setFont(parent.getFont());
			infoButton.setText(Messages.getString("filtersDialog.severityInfo")); //$NON-NLS-1$
			infoButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			infoButton.addSelectionListener(selectionListener);
		}
		public boolean isEnabled() {
			return enablementButton.getSelection();
		}
		public void setEnabled(boolean enabled) {
			enablementButton.setSelection(enabled);
		}
		public boolean isErrorSelected() {
			return errorButton.getSelection();
		}
		public void setErrorSelected(boolean selected) {
			errorButton.setSelection(selected);
		}
		public boolean isWarningSelected() {
			return warningButton.getSelection();
		}
		public void setWarningSelected(boolean selected) {
			warningButton.setSelection(selected);
		}
		public boolean isInfoSelected() {
			return infoButton.getSelection();
		}
		public void setInfoSelected(boolean selected) {
			infoButton.setSelection(selected);
		}
		public void updateEnablement() {
			enablementButton.setEnabled(isFilterEnabled());
			errorButton.setEnabled(enablementButton.isEnabled() && isEnabled());
			warningButton.setEnabled(enablementButton.isEnabled() && isEnabled());
			infoButton.setEnabled(enablementButton.isEnabled() && isEnabled());
		}
	}
	
	/**
	 * @param parentShell
	 * @param filter
	 */
	public ProblemFiltersDialog(Shell parentShell, ProblemFilter filter) {
		super(parentShell, filter);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.markerview.FiltersDialog#createAttributesArea(org.eclipse.swt.widgets.Composite)
	 */
	protected void createAttributesArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout(5, false);
		layout.verticalSpacing = 7;
		composite.setLayout(layout);
		
		descriptionGroup = new DescriptionGroup(composite);
		severityGroup = new SeverityGroup(composite);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.markerview.FiltersDialog#updateFilterFromUI(org.eclipse.ui.views.markerview.MarkerFilter)
	 */
	protected void updateFilterFromUI() {
		ProblemFilter filter = (ProblemFilter) getFilter();
		
		filter.setContains(descriptionGroup.getContains());
		filter.setDescription(descriptionGroup.getDescription().trim());
		
		filter.setSelectBySeverity(severityGroup.isEnabled());
		int severity = 0;
		if (severityGroup.isErrorSelected()) {
			severity = severity | ProblemFilter.SEVERITY_ERROR;
		}
		if (severityGroup.isWarningSelected()) {
			severity = severity | ProblemFilter.SEVERITY_WARNING;
		}
		if (severityGroup.isInfoSelected()) {
			severity = severity | ProblemFilter.SEVERITY_INFO;
		}
		filter.setSeverity(severity);
		
		super.updateFilterFromUI();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.markerview.FiltersDialog#updateUIFromFilter(org.eclipse.ui.views.markerview.MarkerFilter)
	 */
	protected void updateUIFromFilter() {
		ProblemFilter filter = (ProblemFilter) getFilter();
		
		descriptionGroup.setContains(filter.getContains());
		descriptionGroup.setDescription(filter.getDescription());
		
		severityGroup.setEnabled(filter.getSelectBySeverity());
		int severity = filter.getSeverity();
		severityGroup.setErrorSelected((severity & ProblemFilter.SEVERITY_ERROR) > 0);
		severityGroup.setWarningSelected((severity & ProblemFilter.SEVERITY_WARNING) > 0);
		severityGroup.setInfoSelected((severity & ProblemFilter.SEVERITY_INFO) > 0);
		
		super.updateUIFromFilter();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.markerview.FiltersDialog#updateEnabledState()
	 */
	protected void updateEnabledState() {
		super.updateEnabledState();
		descriptionGroup.updateEnablement();
		severityGroup.updateEnablement();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.markerview.FiltersDialog#resetPressed()
	 */
	protected void resetPressed() {
		descriptionGroup.setContains(ProblemFilter.DEFAULT_CONTAINS);
		descriptionGroup.setDescription(""); //$NON-NLS-1$
		
		severityGroup.setEnabled(ProblemFilter.DEFAULT_SELECT_BY_SEVERITY);
		severityGroup.setErrorSelected((ProblemFilter.DEFAULT_SEVERITY & ProblemFilter.SEVERITY_ERROR) > 0);
		severityGroup.setWarningSelected((ProblemFilter.DEFAULT_SEVERITY & ProblemFilter.SEVERITY_WARNING) > 0);
		severityGroup.setInfoSelected((ProblemFilter.DEFAULT_SEVERITY & ProblemFilter.SEVERITY_INFO) > 0);

		super.resetPressed();
	}

}
