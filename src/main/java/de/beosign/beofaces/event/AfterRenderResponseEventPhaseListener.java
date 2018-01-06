package de.beosign.beofaces.event;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.event.PreRenderComponentEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Listener that runs at the end of the RENDER_RESPONSE Phase and publishes appropriate events.
 * The idea is taken from OmniFaces' org.omnifaces.eventlistener.InvokeActionEventListener.
 * </p>
 * <p>
 * PhaseListeners are instantiated once per Application, i.e. they are in essence ApplicationScoped.
 * </p>
 * 
 * @author florian
 */
public class AfterRenderResponseEventPhaseListener implements PhaseListener, SystemEventListener {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(AfterRenderResponseEventPhaseListener.class);

    public AfterRenderResponseEventPhaseListener() {
        FacesContext.getCurrentInstance().getApplication().subscribeToEvent(PreRenderComponentEvent.class, this);
        log.info("Phase Listener {} installed", AfterRenderResponseEventPhaseListener.class.getTypeName());
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        @SuppressWarnings("unchecked")
        Set<UIComponent> components = (Set<UIComponent>) FacesContext.getCurrentInstance().getAttributes().get(AfterRenderResponseEventPhaseListener.class.getName());
        if (components != null) {
            for (UIComponent component : components) {
                event.getFacesContext().getApplication().publishEvent(event.getFacesContext(), PostRenderComponentEvent.class, component);
            }
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        log.debug("Event {} for component {}", event.getClass().getSimpleName(), ((UIComponent) event.getSource()).getClientId());

        @SuppressWarnings("unchecked")
        Set<UIComponent> components = (Set<UIComponent>) FacesContext.getCurrentInstance().getAttributes().get(AfterRenderResponseEventPhaseListener.class.getName());

        if (components == null) {
            components = new LinkedHashSet<>();
        }
        components.add((UIComponent) event.getSource());

        FacesContext.getCurrentInstance().getAttributes().put(AfterRenderResponseEventPhaseListener.class.getName(), components);

    }

    @Override
    public boolean isListenerForSource(Object source) {
        if (!(source instanceof UIComponent)) {
            return false;
        }

        UIComponent component = (UIComponent) source;
        return !isEmpty(component.getListenersForEventClass(PostRenderComponentEvent.class));
    }

    private boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
