package de.beosign.beofaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.NamedEvent;

/**
 * <p>
 * Event that is created at the end of the render response phase if registered with a component.
 * </p>
 * <p>
 * {@link ComponentSystemEvent}s can be attached to a component in different ways, e.g. by using <code>f:event</code> or by explicitly
 * calling {@link UIComponent#subscribeToEvent(Class, javax.faces.event.ComponentSystemEventListener)}.
 * </p>
 * <p>
 * This event is a {@link NamedEvent} and available as <code>postRender</code>.
 * </p>
 * <p>
 * Example:
 * 
 * <pre>
 *   &lt;h:inputText id="text1" value="#{eventsBean.value}"&gt;
 *     &lt;f:event listener="#{eventsBean.postRenderComponentEvent}" type="postRender" /&gt;
 *   &lt;/h:inputText&gt;
 * </pre>
 * </p>
 * 
 * @author florian
 */
@NamedEvent(shortName = "postRender")
public class PostRenderComponentEvent extends ComponentSystemEvent {
    private static final long serialVersionUID = 1L;

    public PostRenderComponentEvent(UIComponent component) {
        super(component);
    }
}
