# JSF

Very good tutorials about the JSF lifecycle can be found here:
* http://balusc.omnifaces.org/2006/09/debug-jsf-lifecycle.html
* http://palkonyves.blogspot.de/2013/08/demistifying-jsf-21-lifecycle-mojarra.html?m=1

## Lifecycle

### Phases

| ID | Phase                |
|----|----------------------|
| 1  | RESTORE_VIEW         |
| 2  | APPLY_REQUEST_VALUES |
| 3  | PROCESS_VALIDATIONS  |
| 4  | UPDATE_MODEL_VALUES  |
| 5  | INVOKE_APPLICATION   |
| 6  | RENDER_RESPONSE      |

### Overview ###
| Phase | Action                                                                                                                                     |
|-------|-------------------------------------------------------------------------|
| 1     | Read view from context or create new view                                                                                                  |
| 2     | **Decode**: Calculate **submitted value** from http request parameters                                                                         |
| 3     | Convert **submitted value** into **converted value**                                                                                       |
| 3     | Validate the **converted value**                                                                                                           |
| 3     | If validation passed, set the **converted value** to the **local value** of the component                                                  |
| 4     | Set the **local value** into the **model** (backing bean, calling the setter) This is only done for the _value_ attribute of the component |
| 6     | **Encode**: Call encodeBegin, encodeChildren, encodeEnd (typically) and generate html code
| 6     | In case of an error in any previous stage, render the same page again    |

### Initial request vs. postbacks
When accessing a facelet (xhtml) for the first time, ie. when invoking http GET, only the **Restore View** and the **Render Response** phases are executed.
When a http POST is executed, e.g. during ajax calls or form submits, all phases are executed.

### Restore View
On an initial request, a new and empty _view_ gets created which is stored into the _FacesContext_. JSF then proceeds to the Render Response phase during which the empty view is populated with the components referenced by the tags in the page.
On a postback, the view already existed before; it is then restored by state information saved on the server or the client.
Validators and converters are wired to their components.

### Apply Request Values
Each component decodes the information from the HTTP request parameters and stores the extracted values as local value. If any decode method or event listener has called _renderResponse_, JSF skips to the Render Response phase.
Any queued events for this phase are broadcast to its listeners.
For components that are marked _immediate_, the validations, conversions, and events are now processed.

#### Methods

1. **UIComponent.processDecodes**  
   If _rendered_, recursively calls _processDecodes()_ on the children and then _decode()_ on this component (the order may be implementation specific!).
   If _immediate_ is true, calls _validate()_ on this component afterwards.
1. **UIComponent.decode**  
   Delegates work to renderer if a renderer is present.
   Uses information from the Http Request (like the request map) to extract the values that are relevant to this component and then set this _submitted_ value into the component by calling _setSubmittedValue(Object)_.
1. **EditableValueHolder.setSubmittedValue**
1. **EditableValueHolder.setValid**  
  Sets the _valid_ flag back to ```true```.

### Process Validations
JSF converts the submitted value and then validates the converted value. If any error occurs, JSF skips to the Render Response phase, rendering the same page again.

#### Methods     
1. **UIInput.processValidators** 
  If _rendered_, recursively calls _processValidators()_ on the facets and children. If not _immediate_, calls _validate()_ on this component (because if immediate, then validation has already been done in the Aplly Request Values phase.
1. **UIInput.validate**  
Check the _submitted value_ for null and emptiness; if null, validation is successful; if not null, check if empty String is considered ```null```. If the string is indeed empty, set the submitted value to ```null```; calls _getConvertedValue(FacesContext, Object)_ and then with the converted value _UIInput.validateValue(FacesContext, Object)_. If the value is still _valid_, call _UIInput.setValue(Object)_, reset _submitted value_ to ```null``` and queue a ```ValueChangeEvent```. 
1. **UIInput.getConvertedValue(FacesContext, Object)**    
  _Convert_ the _submitted value_. Delegates to a renderer if present. Otherwise checks if the submitted value is of type ```String``` and if a converter is present. In this case, call the converter and return that value (of type ```Object```). If the submitted value is not a ```String```, no conversion is done and the submitted value is equal to the converted value.
  In case of an exception, create a conversion error message and set _valid_ to ```false```.
1. **UIInput.validateValue(FacesContext, Object)**  
  Passed in object is the converted value. Checks for _required_ flag. Then invokes each validator in a loop and sets _valid_ to ```false``` if any validator throws an exception. Creates FacesMessages.
1. **UIInput.setValue(FacesContext, Object)**  
  Passed in object is the converted and validated value. Sets it into the state helper and marks the values as locally set.

### Update model values
JSF traverses the component tree and sets the local values to the server side objects, i.e. the binding targets, so setters are called, but only the setters that are pointed at from the component's ```value``` attribute.

#### Methods
1. **UIInput.processUpdates** 
  If _rendered_, recursively calls _processUpdates()_ on the facets and children. Then calls _updateModel()_ on this component.
1. **UIInput.updateModel**
  If the component is not _valid_ or the value is not marked as _locally set_, does nothing. Else reads the value expression that is supplied to the ```value``` attribute ('value' is hard-coded, so the attribute must be named 'value'!) and evaluates it, which causes the setter to be called on the model.
1. **UIInput.resetValues**  
  Removes the _locally set_ flag, the _value_ and the _valid_ property from the state helper. Sets the _submitted value_ to ```null```.
  
### Invoke Application
During this phase, the JavaServer Faces implementation handles any application-level 
events, such as submitting a form or linking to another page.
At this point, if the application needs to redirect to a different web application resource 
or generate a response that does not contain any JavaServer Faces components, it can 
call the FacesContext.responseComplete method.
  
### Render Response
On an initial request, the components will now be added to the component tree - if it is a postback, this is not necessary.
On a postback, if there have been errors during any previous phase, the original page is rendered again. Queued messages on components are displayed on the page by e.g. _h:messages_.
The state is saved so the next Restore View phase can access it.

#### Methods

1. **UIComponent.setId()**
1. **UIComponent.setValueExpression(name, ValueExpression)**  
   If component's attribute value is set by a value expression (```value="#{list.id}"``` for example), then it will be stored in the components state helper.
1. **UIComponent.encodeBegin()**  
   Delegates work to renderer if a renderer is present
1. **UIComponent.getRendersChildren()**    
   Delegates work to renderer if a renderer is present. If true, _UIComponent.encodeChildren()_ will be called.
1. **UIComponent.getValue()**  
   Returns the _local value_ (only for UIInput) if not null, else returns the _value_ stored in the state helper.
1. **UIComponent.encodeChildren()**    
   Delegates work to renderer if a renderer is present. Only called if _UIComponent.getRendersChildren()_ returned true or _UIComponent.encodeAll()_ has been called.
1. **UIComponent.encodeEnd()**   
   Delegates work to renderer if a renderer is present
 





 