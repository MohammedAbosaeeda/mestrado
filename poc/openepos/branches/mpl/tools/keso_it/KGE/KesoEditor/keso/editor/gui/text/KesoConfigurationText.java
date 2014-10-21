package keso.editor.gui.text;

import keso.editor.data.IKesoData;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.output.ConfigurationKesoOutput;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextPrintOptions;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.widgets.*;

/**
 * @author  Wilhelm Haas
 */
public class KesoConfigurationText implements SelectionListener {
	Composite parent;
	Shell shell;
	int style;
	KesoMainWindow parentwindow;
	
	StyledText styledtext;
	
	IKesoData data;
	
	int horizontal_selection = -1;
	int vertical_selection = -1;
	
	public KesoConfigurationText(Composite parent, int style) {
		this.parent = parent;
		this.shell = this.parent.getShell();
		this.style = style;
		
		this.styledtext = new StyledText(parent, SWT.MULTI | SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL);
		this.styledtext.getHorizontalBar().addSelectionListener(this);
		this.styledtext.getVerticalBar().addSelectionListener(this);
		
		final KesoTextStyleListener stylelistener = new KesoTextStyleListener();

		this.styledtext.addLineStyleListener(stylelistener);
		this.styledtext.addExtendedModifyListener(new ExtendedModifyListener() {
			public void modifyText(ExtendedModifyEvent event) {
				// Recalculate the comments
				stylelistener.refresh(styledtext.getText());
				// Redraw the text
				styledtext.redraw();
			}
		});
		
	}
	
	public void print(Printer printer) {
		this.updateText();
		StyledTextPrintOptions options = new StyledTextPrintOptions();
		options.footer = "\t <page> \t";
		this.styledtext.print(printer, options).run();
	}
	
	public void setParentWindow(KesoMainWindow mainwindow) {
		this.parentwindow = mainwindow;
	}
	
	public Control getWidget() {
		return this.styledtext;
	}
	
	/**
	 * @param data  the data to set
	 * @uml.property  name="data"
	 */
	public void setData(IKesoData data) {
		this.data = data;
		this.updateText();
	}

	public void updateText() {
		if (this.data == null) {
			this.styledtext.setText("");
			this.horizontal_selection = -1;
			this.vertical_selection = -1;
		} else {
			ConfigurationKesoOutput output = new ConfigurationKesoOutput();
			output.setData(this.data);
			this.styledtext.setText(output.toString());
		}
	}

	public void widgetSelected(SelectionEvent e) {
		if (e.widget == this.styledtext.getHorizontalBar()) {
			this.horizontal_selection = this.styledtext.getHorizontalBar().getSelection();
		} else if (e.widget == this.styledtext.getVerticalBar()) {
			this.vertical_selection = this.styledtext.getVerticalBar().getSelection();
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
