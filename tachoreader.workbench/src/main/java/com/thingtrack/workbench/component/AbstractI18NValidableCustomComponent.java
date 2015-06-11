package com.thingtrack.workbench.component;

import com.github.peholmst.i18n4vaadin.I18N;
import com.thingtrack.workbench.WorkbenchUI;
import com.vaadin.ui.CustomComponent;

@SuppressWarnings("serial")
public abstract class AbstractI18NValidableCustomComponent extends CustomComponent {

	public I18N getI18N() {
		return WorkbenchUI.getCurrent().getI18N();
	}
	
	@Override
	public void attach() {
		super.attach();
		updateLabels();
	}
	
	@Override
	public void detach() {
		super.detach();
	}
	
	protected abstract void updateLabels();
	
	protected abstract boolean isValidate();
}
