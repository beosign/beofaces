package de.beosign.beofaces.event;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Listens for "PostAddToView" Event of ViewRoot. This is the earliest possible moment where it is safe to traverse the component tree because it has been fully
 * built. This listener is installed via faces-config.xml.
 * </p>
 * <p>
 * See <a href=
 * "https://stackoverflow.com/questions/29691625/the-earliest-moment-to-visittree-on-fully-built-component-tree">https://stackoverflow.com/questions/29691625/the-earliest-moment-to-visittree-on-fully-built-component-tree</a>
 * </p>
 * 
 * @author florian
 */
public class ViewRootAddedToViewEventListener implements SystemEventListener {
    private static final Logger log = LoggerFactory.getLogger(ViewRootAddedToViewEventListener.class);

    public ViewRootAddedToViewEventListener() {
        log.info("{} instantiated", ViewRootAddedToViewEventListener.class.getTypeName());
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return (source instanceof UIViewRoot);
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        log.debug("Event {} for component {}", event.getClass().getSimpleName(), ((UIComponent) event.getSource()).getClientId());

        FacesContext.getCurrentInstance().getApplication().publishEvent(FacesContext.getCurrentInstance(), ComponentTreeBuiltEvent.class, FacesContext.getCurrentInstance().getViewRoot());
    }
}
