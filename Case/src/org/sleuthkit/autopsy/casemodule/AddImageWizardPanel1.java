/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sleuthkit.autopsy.casemodule;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

/**
 * The "Add Image" wizard panel1 handling the logic of selecting image file(s)
 * to add to Case, and pick the time zone.
 */
class AddImageWizardPanel1 implements WizardDescriptor.Panel<WizardDescriptor>, PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private AddImageVisualPanel1 component;
    private boolean isNextEnable = false;

    /**
     * Get the visual component for the panel. In this template, the component
     * is kept separate. This can be more efficient: if the wizard is created
     * but never displayed, or not all panels are displayed, it is better to
     * create only those which really need to be visible.
     *
     * @return component  the UI component of this wizard panel
     */
    @Override
    public AddImageVisualPanel1 getComponent() {
        if (component == null) {
            component = new AddImageVisualPanel1(this);
        }
        component.addPropertyChangeListener(this);
        return component;
    }

    /**
     * Help for this panel. When the panel is active, this is used as the help
     * for the wizard dialog.
     *
     * @return HelpCtx.DEFAULT_HELP  the help for this panel
     */
    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx(SampleWizardPanel1.class);
    }

    /**
     * Tests whether the panel is finished and it is safe to proceed to the next
     * one. If the panel is valid, the "Next" button will be enabled.
     *
     * @return boolean  true if all the fields are correctly filled, false otherwise
     */
    @Override
    public boolean isValid() {

        // set the focus to the next button of the wizard dialog if it's enabled
        if(isNextEnable){
            Lookup.getDefault().lookup(AddImageAction.class).requestFocusButton("Next >");
        }

        return isNextEnable;
    }

    /**
     * Enable the "Next" button and fireChangeEvent to update the GUI
     *
     * @param isEnabled  true if next button can be enabled, false otherwise
     */
    public void enableNextButton(boolean isEnabled){
        isNextEnable = isEnabled;
        fireChangeEvent();
    }

    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    /**
     * Adds a listener to changes of the panel's validity.
     *
     * @param l  the change listener to add
     */
    @Override
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    /**
     * Removes a listener to changes of the panel's validity.
     *
     * @param l  the change listener to move
     */
    @Override
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    /**
     * This method is auto-generated. It seems that this method is used to listen
     * to any change in this wizard panel.
     */
    protected final void fireChangeEvent() {
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }
     

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.

    /**
     * Provides the wizard panel with the current data--either the default data
     * or already-modified settings, if the user used the previous and/or next
     * buttons. This method can be called multiple times on one instance of
     * WizardDescriptor.Panel.
     *s
     * @param settings  the setting to be read from
     */
    @Override
    public void readSettings(WizardDescriptor settings) {
    }

    /**
     * Provides the wizard panel with the opportunity to update the settings
     * with its current customized state. Rather than updating its settings
     * with every change in the GUI, it should collect them, and then only save
     * them when requested to by this method. This method can be called multiple
     * times on one instance of WizardDescriptor.Panel.
     *
     * @param settings  the setting to be stored to
     */
    @Override
    public void storeSettings(WizardDescriptor settings) {
        settings.putProperty(AddImageAction.IMGPATHS_PROP, getComponent().getImagePaths());
        settings.putProperty(AddImageAction.TIMEZONE_PROP, getComponent().getSelectedTimezone()); // store the timezone
    }


    /**
     * The "listener" for any property change in this panel. Any property changes
     * will invoke the "fireChangeEvent()" method.
     *
     * @param evt  the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        fireChangeEvent();
    }
}