package utils;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.ClientListenerSet;

import oracle.jbo.common.StringManager;

public class JSFUtils {
    private JSFUtils() {
        super();
    }
    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(JSFUtils.class);

    /**
     * field NO_RESOURCE_FOUND - String - hold missing resource.
     */
    private static final String NO_RESOURCE_FOUND = "Missing resource: ";

    /**
     * This Method is for taking a reference to a JSF binding expression.
     * This method also returning the matching object (or creating it).
     * @param expression EL expression
     * @return Managed object
     */
    public static Object resolveExpression(final String expression) {
        FacesContext facesContext = getFacesContext();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        return valueExp.getValue(elContext);
    }

    /**
     * This Method for resolveRemoteUser.
     * @return String
     */
    public static String resolveRemoteUser() {
        FacesContext facesContext = getFacesContext();
        ExternalContext ectx = facesContext.getExternalContext();
        return ectx.getRemoteUser();
    }

    /**
     * This Method for resolveUserPrincipal.
     * @return String
     */
    public static String resolveUserPrincipal() {
        FacesContext facesContext = getFacesContext();
        ExternalContext ectx = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
        return request.getUserPrincipal().getName();
    }

    /**
     * This Method for resloveMethodExpression.
     * @param expression parameter
     * @param returnType parameter
     * @param argTypes parameter
     * @param argValues parameter
     * @return Object
     */
    public static Object resloveMethodExpression(final String expression, final Class returnType,
                                                 final Class[] argTypes, final Object[] argValues) {
        FacesContext facesContext = getFacesContext();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        MethodExpression methodExpression =
            elFactory.createMethodExpression(elContext, expression, returnType, argTypes);
        return methodExpression.invoke(elContext, argValues);
    }

    /**
     * This Method for taking a reference to a JSF binding expression and returning.
     * the matching Boolean.
     * @param expression EL expression
     * @return Managed object
     */
    public static Boolean resolveExpressionAsBoolean(final String expression) {
        return (Boolean) resolveExpression(expression);
    }

    /**
     * This Method for taking a reference to a JSF binding expression and returning
     * the matching String (or creating it).
     * @param expression EL expression
     * @return Managed object
     */
    public static String resolveExpressionAsString(final String expression) {
        return (String) resolveExpression(expression);
    }

    /**
     * This is a Convenience method for resolving a reference to a managed bean by name.
     * rather than by expression.
     * @param beanName name of managed bean
     * @return Managed object
     */
    public static Object getManagedBeanValue(final String beanName) {
        StringBuffer buff = new StringBuffer("#{");
        buff.append(beanName);
        buff.append('}');
        return resolveExpression(buff.toString());
    }

    /**
     * This Method for setting a new object into a JSF managed bean.
     * Note: will fail silently if the supplied object does.
     * not match the type of the managed bean.
     * @param expression EL expression
     * @param newValue new value to set
     */
    public static void setExpressionValue(final String expression, final Object newValue) {
        FacesContext facesContext = getFacesContext();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        //Check that the input newValue can be cast to the property type
        //expected by the managed bean.
        //If the managed Bean expects a primitive we rely on Auto-Unboxing
        Class bindClass = valueExp.getType(elContext);
        if (bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
            valueExp.setValue(elContext, newValue);
        }
    }

    /**
     * This Convenience method for setting the value of a managed bean by name
     * rather than by expression.
     * @param beanName name of managed bean
     * @param newValue new value to set
     */
    public static void setManagedBeanValue(final String beanName, final Object newValue) {
        StringBuffer buff;
        buff = new StringBuffer("#{");
        buff.append(beanName);
        buff.append('}');
        setExpressionValue(buff.toString(), newValue);
    }

    /**
     * This Convenience method for setting Session variables.
     * @param key object key
     * @param object value to store
     */
    public static void storeOnSession(final String key, final Object object) {
        FacesContext ctx = getFacesContext();
        Map sessionState = ctx.getExternalContext().getSessionMap();
        sessionState.put(key, object);
    }

    /**
     * This Convenience method for getting Session variables.
     * @param key object key
     * @return session object for key
     */
    public static Object getFromSession(final String key) {
        FacesContext ctx = getFacesContext();
        Map sessionState = ctx.getExternalContext().getSessionMap();
        return sessionState.get(key);
    }

    /**
     * This Convenience method for removing Session variables.
     * @param key object key
     * @return session object for key
     */
    public static Object removeFromSession(final String key) {
        FacesContext ctx = getFacesContext();
        Map sessionState = ctx.getExternalContext().getSessionMap();
        return sessionState.remove(key);
    }

    /**
     * This method for getFromHeader.
     * @param key String key
     * @return String
     */
    public static String getFromHeader(final String key) {
        FacesContext ctx = getFacesContext();
        ExternalContext ectx = ctx.getExternalContext();
        return ectx.getRequestHeaderMap().get(key);
    }

    /**
     * This Convenience method for getting Request variables.
     * @param key object key
     * @return session object for key
     */
    public static Object getFromRequest(final String key) {
        FacesContext ctx = getFacesContext();
        Map sessionState = ctx.getExternalContext().getRequestMap();
        return sessionState.get(key);
    }

    /**
     * This method Pulls a String resource from the property bundle that
     * is defined under the application &lt;message-bundle&gt; element in
     * the faces config. Respects Locale.
     * @param key string message key
     * @param bundleName as String
     * @return Resource value or placeholder error String
     */
    //    public static String getStringFromBundle(final String key, final String bundleName) {
    //        ResourceBundle bundle = getResourceBundle(bundleName);
    //        return getStringSafely(bundle, key, BaseConstant.GENERIC_EXCEPTION);
    //    }

    /**
     * This Convenience method to construct a <code>FacesMesssage</code>
     * from a defined error key and severity
     * This assumes that the error keys follow the convention of
     * using <b>_detail</b> for the detailed part of the
     * message, otherwise the main message is returned for the
     * detail as well.
     * @param key for the error message in the resource bundle
     * @param severity severity of message
     * @return Faces Message object
     */
    public static FacesMessage getMessageFromBundle(final String key, final FacesMessage.Severity severity) {
        ResourceBundle bundle = getBundle();
        String summary = getStringSafely(bundle, key, null);
        String detail = getStringSafely(bundle, key + "_detail", summary);
        FacesMessage message = new FacesMessage(summary, detail);
        message.setSeverity(severity);
        return message;
    }

    /**
     * This method Adds JSF info message.
     * @param msg info message string
     * @param bundleName as String
     * @param component as String
     */
    public static void addFacesWarningMessage(final String msg, final String bundleName, final String component) {

        addMessage(msg, bundleName, FacesMessage.SEVERITY_WARN, component);
    }

    /**
     * This method Adds JSF info message.
     * @param msg info message string
     * @param bundleName as String
     */
    public static void addFacesInformationMessage(final String msg, final String bundleName) {
        addMessage(msg, bundleName, FacesMessage.SEVERITY_INFO, null);

    }

    /**
     * This method Adds JSF error message.
     * @param msg error message string
     * @param bundleName as String
     */
    public static void addFacesErrorMessage(final String msg, final String bundleName) {
        addMessage(msg, bundleName, FacesMessage.SEVERITY_ERROR, null);
    }


    /**
     * This method Adds JSF info message.
     * @param msg info message string
     */
    public static void addFacesErrorMessage(final String msg) {
        addMessage(msg, FacesMessage.SEVERITY_ERROR);
    }


    /**
     * This method Adds JSF error message to a given component.
     * component Should be getClientId() for the comonent  binding.
     * @param msg error message string
     * @param bundleName as String
     * @param component as String
     */
    public static void addFacesErrorMessage(final String msg, final String bundleName, final String component) {
        if (null == component) {
            addMessage(msg, bundleName, FacesMessage.SEVERITY_ERROR, null);
        } else {
            addMessage(msg, bundleName, FacesMessage.SEVERITY_ERROR, component);
        }
    }

    /**
     * This method is used to call the method "addMessage" to display messages based on
     * severity for a particular component (if it exists).
     * @param msg as String
     * @param severity as Severity
     * @param component as String
     */
    public static void addMessage(final String msg, final javax.faces.application.FacesMessage.Severity severity,
                                  final String component) {
        addMessage(msg, null, severity, component);
    }

    /**
     * This message adds a message by retrieving from the resource Bundle if it exists.
     * @param msg as String
     * @param bundleName as String
     * @param severity as Severity
     * @param component as String
     */
    private static void addMessage(final String msg, final String bundleName,
                                   final javax.faces.application.FacesMessage.Severity severity,
                                   final String component) {
        String actualMsg;
        FacesContext ctx = getFacesContext();
        if (bundleName != null) {
            ResourceBundle bundle = getResourceBundle(bundleName);
            actualMsg = getStringSafely(bundle, msg, "GENERIC_EXCEPTION");
        } else {
            actualMsg = msg;
        }
        FacesMessage facesMessage = null;
        if (component != null) {
            facesMessage = new FacesMessage(severity, "", actualMsg);
            ctx.addMessage(component, facesMessage);
        } else {
            facesMessage = new FacesMessage(severity, actualMsg, "");
            ctx.addMessage(null, facesMessage);
            Object messagesBean = resolveExpression("#{messagesBean}");
            if (messagesBean != null) {
                setExpressionValue("#{messagesBean.isGlobal}", true);
            }
            if (FacesMessage.SEVERITY_INFO.equals(severity)) {
                showMessagesPopup(true);
            } else {
                showMessagesPopup(false);
            }
        }
    }


    /**
     * Method add the message.
     * @param msg as String
     * @param severity as Severity
     */
    private static void addMessage(final String msg, final javax.faces.application.FacesMessage.Severity severity) {
        FacesContext ctx = getFacesContext();
        FacesMessage facesMessage = new FacesMessage(severity, msg, "");
        ctx.addMessage(null, facesMessage);
        Object messagesBean = resolveExpression("#{messagesBean}");
        if (messagesBean != null) {
            setExpressionValue("#{messagesBean.isGlobal}", true);
        }
        if (FacesMessage.SEVERITY_ERROR.equals(severity)) {
            showPurMessagesPopup(true);
        }

    }


    /**
     * This method is responsible for showing Popup message.
     * @param isConfirmationPopup as boolean
     */
    public static void showPurMessagesPopup(boolean isConfirmationPopup) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent popup = fc.getViewRoot().findComponent("pt1:purMsgPopUp");
        RichPopup rp = (RichPopup) popup;
        ClientListenerSet cls = new ClientListenerSet();
        if (isConfirmationPopup) {
            cls.addListener("popupOpened", "autoCancelPopup");
        }
        if (null != rp) {
            rp.setClientListeners(cls);
        }

        if (popup != null) {
            showPopup(popup);
        }
    }


    /**
     * This method is responsible for showing Popup message.
     * @param isConfirmationPopup as boolean
     */
    public static void showMessagesPopup(boolean isConfirmationPopup) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent popup = fc.getViewRoot().findComponent("pt1:msgPopUp");
        RichPopup rp = (RichPopup) popup;
        ClientListenerSet cls = new ClientListenerSet();
        if (isConfirmationPopup) {
            cls.addListener("popupOpened", "autoCancelPopup");
        }
        if (null != rp) {
            rp.setClientListeners(cls);
        }

        if (popup != null) {
            showPopup(popup);
        }
    }

    /**
     * This method is used to cancel Popup messages.
     */
    public static void cancelMessagesPopup() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent popup = fc.getViewRoot().findComponent("pt1:msgPopUp");
        cancelPopup(popup);
    }

    /**
     * This method shows a Popup window to the user for Information purposes.
     * @param argPopup as UIComponent
     */
    public static void showPopup(final UIComponent argPopup) {
        if (argPopup != null) {
            RichPopup rp = (RichPopup) argPopup;
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            rp.show(hints);
        }
    }

    /**
     * This method cancels the Popup operation in the screen.
     * @param argPopup as UIComponent
     */
    public static void cancelPopup(final UIComponent argPopup) {
        if (argPopup != null) {
            RichPopup rp = (RichPopup) argPopup;
            rp.cancel();
        }
    }

    /**
     * This method is used to display Error Messages from the bundle.
     * @param msg as String
     * @param bundleName as String
     * @param params as Object[]
     */
    public static void addFacesErrorMessage(final String msg, final String bundleName, final Object[] params) {
        String defaultMsg = null;
        String actualMsg = null;
        ResourceBundle bundle = getResourceBundle(bundleName);
        defaultMsg = getStringSafely(bundle, "GENERIC_EXCEPTION", "GENERIC_EXCEPTION");
        actualMsg = StringManager.getString(bundleName, msg, defaultMsg, params);
        addMessage(actualMsg, null, FacesMessage.SEVERITY_ERROR, null);
    }

    /**
     * This method Gets view id of the view root.
     * @return view id of the view root
     */
    public static String getRootViewId() {
        return getFacesContext().getViewRoot().getViewId();
    }

    /**
     * This method Gets component id of the view root.
     * @return component id of the view root
     */
    public static String getRootViewComponentId() {
        return getFacesContext().getViewRoot().getId();
    }

    /**
     * This method Gets FacesContext.
     * @return facesContext as FacesContext
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * This Internal method to pull out the correct local.
     * message bundle.
     * @return attribute as ResourceBundle
     */
    private static ResourceBundle getBundle() {
        FacesContext ctx = getFacesContext();
        UIViewRoot uiRoot = ctx.getViewRoot();
        Locale locale = uiRoot.getLocale();
        ClassLoader ldr = Thread.currentThread().getContextClassLoader();
        return ResourceBundle.getBundle("commonBundle", locale, ldr);

    }

    /**
     * This Internal method to pull out the correct local.
     * message bundle.
     * @param bundleName as String
     * @return attribute as ResourceBundle
     */
    private static ResourceBundle getResourceBundle(String bundleName) {
        FacesContext ctx = getFacesContext();

        if (null == bundleName) {
            //bundleName  = BaseConstant.COMMONBUNDLE;
            return ctx.getApplication().getResourceBundle(ctx, "GENERIC_EXCEPTION");
        }
        return ctx.getApplication().getResourceBundle(ctx, bundleName);
    }

    /**
     * This method Gets an HTTP Request attribute.
     * @param name as String
     * @return attribute as Object
     */
    public static Object getRequestAttribute(final String name) {
        return getFacesContext().getExternalContext().getRequestMap().get(name);
    }

    /**
     * This method Sets an HTTP Request attribute.
     * @param name attribute name
     * @param value attribute value
     */
    public static void setRequestAttribute(final String name, final Object value) {
        getFacesContext().getExternalContext().getRequestMap().put(name, value);
    }

    /**
     * This Internal method to proxy for resource keys that don't exist.
     * @param bundle attribute bundle
     * @param key attribute key
     * @param defaultValue attribute defaultValue
     * @return String value
     */
    private static String getStringSafely(final ResourceBundle bundle, final String key, final String defaultValue) {
        String resource = null;
        try {
            resource = bundle.getString(key);
        } catch (MissingResourceException mrex) {
            if (defaultValue != null) {
                resource = defaultValue;
            } else {
                resource = NO_RESOURCE_FOUND + key;
            }
        } catch (Exception ex) {
            if (defaultValue != null) {
                resource = defaultValue;
            } else {
                resource = NO_RESOURCE_FOUND + key;
            }
        }
        return resource;
    }

    /**
     * This method Locate an UIComponent in view root with its component id.
     * Use a recursive way to achieve this.
     * @param componentId UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponentInRoot(final String componentId) {
        UIComponent component = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, componentId);
        }
        return component;
    }

    /**
     * This method Locate an UIComponent from its root component.
     * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
     * @param base root Component (parent)
     * @param componentId UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponent(final UIComponent base, final String componentId) {
        if (componentId.equals(base.getId())) {
            return base;
        }
        UIComponent children = null;
        UIComponent result = null;
        Iterator childrens = base.getFacetsAndChildren();

        // Comment while loop in order to fix 2 critical somar violation By Ajay Kapu
        // Dodgy - Redundant nullcheck of value known to be null
        // Dodgy - Load of known null value
        //  while (childrens.hasNext() && (result == null)) {

        boolean childFlag = true;

        while (childrens.hasNext() && childFlag) {
            children = (UIComponent) childrens.next();
            //             if (componentId.equals(children.getId())) {
            //                 result = children;
            //                 childFlag=false;
            //                // break;
            //             }
            result = findComponent(children, componentId);

            if (result != null) {
                //break

                childFlag = false;
            }
        }
        return result;
    }

    /**
     * This Method to create a redirect URL. The assumption is that the JSF servlet mapping is
     * "faces", which is the default.
     * @param view the JSP or JSPX page to redirect to
     * @return a URL to redirect to
     */
    public static String getPageURL(final String view) {
        FacesContext facesContext = getFacesContext();
        ExternalContext externalContext = facesContext.getExternalContext();
        String url = ((HttpServletRequest) externalContext.getRequest()).getRequestURL().toString();
        StringBuffer newUrlBuffer = new StringBuffer();
        newUrlBuffer.append(url.substring(0, url.lastIndexOf("faces/")));
        newUrlBuffer.append("faces");
        String targetPageUrl = view.startsWith("/") ? view : "/" + view;
        newUrlBuffer.append(targetPageUrl);
        return newUrlBuffer.toString();
    }

    /**
     * This method Create value binding for EL exression.
     * @param expression parameter
     * @return Object
     */
    public static Object getExpressionObjectReference(final String expression) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elctx = facesContext.getELContext();
        ExpressionFactory elFactory = facesContext.getApplication().getExpressionFactory();
        return elFactory.createValueExpression(elctx, expression, Object.class).getValue(elctx);
    }

    /**
     * This method Invokes an expression.
     * @param expr parameter
     * @param returnType parameter
     * @param argTypes parameter
     * @param args parameter
     * @return Object
     */
    public static Object invokeMethodExpression(final String expr, final Class returnType, final Class[] argTypes,
                                                final Object[] args) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elctx = facesContext.getELContext();
        ExpressionFactory elFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression methodExpr = elFactory.createMethodExpression(elctx, expr, returnType, argTypes);
        return methodExpr.invoke(elctx, args);
    }

    /**
     * This method Invokes an expression.
     * @param expr parameter
     * @param returnType parameter
     * @param argType parameter
     * @param argument parameter
     * @return Object
     */
    public static Object invokeMethodExpression(final String expr, final Class returnType, final Class argType,
                                                final Object argument) {
        return invokeMethodExpression(expr, returnType, new Class[] { argType }, new Object[] { argument });
    }

    /**
     * This Method for gettting HttpResonse instance.
     * @return response HttpServletResponse.
     */
    public static HttpServletRequest getHttpRequest() {
        FacesContext facesContext = getFacesContext();
        ExternalContext ectx = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) ectx.getRequest();
        return httpServletRequest;
    }

    /**
     * This Method for gettting HttpResonse instance.
     * @return response HttpServletResponse.
     */
    public static HttpServletResponse getHttpRequestObject() {
        FacesContext facesContext = getFacesContext();
        ExternalContext ectx = facesContext.getExternalContext();
        // HttpServletResponse response = (HttpServletResponse) ectx.getResponse();
        return (HttpServletResponse) ectx.getResponse();
    }

    /**
     * This Method for gettting HttpResonse instance.
     * @return response HttpServletResponse.
     */
    public static HttpSession getHttpSession() {
        return getHttpRequest().getSession();
    }

    /**
     * This method is responsible for refreshing all messages with ID : "arorMsgs"
     * refreshMessages.
     */
    public static void refreshMessages() {
        doMessageRefresh("arorMsgs");
    }

    /**
     * This method calls the doMessageRefresh method to perform refresh operation.
     * @param msgCompId as String
     */
    public static void refreshMessages(String msgCompId) {
        doMessageRefresh(msgCompId);
    }

    /**
     * This method is used to Refresh Message for the particular message component ID value.
     * @param msgCompId as String
     */
    private static void doMessageRefresh(String msgCompId) {
        LOGGER.info("msgCompId is =" + msgCompId);
        //        //UIComponent uc = JSFUtils.findComponentInRoot(msgCompId);
        //        List<UIComponent> viewComponent =
        //                                FacesContext.getCurrentInstance().getViewRoot().getChildren();
        ////        if(viewComponent != null && !viewComponent.isEmpty() && viewComponent.get(0) != null) {
        ////            //AdfFacesContext.getCurrentInstance().addPartialTarget(viewComponent.get(0));
        ////        }
    }

    /**
     * Method to get the userName.
     * @return userName as String
     */
    public static String getFirstAndLastName() {
        return ADFContext.getCurrent().getSecurityContext().getUserName();
    }
}
