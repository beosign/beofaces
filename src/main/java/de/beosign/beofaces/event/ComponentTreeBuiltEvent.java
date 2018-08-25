package de.beosign.beofaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.NamedEvent;

/**
 * <p>
 * Event that is created as soon as the component tree has been fully built. This may be in Phase 1 (on Postback) or in Phase 6 (on GET requests).
 * </p>
 * <p>
 * {@link ComponentSystemEvent}s can be attached to a component in different ways, e.g. by using <code>f:event</code> or by explicitly
 * calling {@link UIComponent#subscribeToEvent(Class, javax.faces.event.ComponentSystemEventListener)}.
 * </p>
 * <p>
 * This event is a {@link NamedEvent} and available as <code>componentTreeBuilt</code>.
 * </p>
 * <p>
 * Example:
 * 
 * <pre>
 *  <f:metadata>
 *       <f:event type="componentTreeBuilt" listener="#{eventsBean.onComponentTreeBuilt}"/>
 *  </f:metadata>
 * </pre>
 * </p>
 * 
 * @author florian
 */
@NamedEvent(shortName = "componentTreeBuilt")
public class ComponentTreeBuiltEvent extends ComponentSystemEvent {
    private static final long serialVersionUID = 1L;

    public ComponentTreeBuiltEvent(UIComponent component) {
        super(component);
    }
}
