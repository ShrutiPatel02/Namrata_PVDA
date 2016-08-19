package bean;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.share.logging.ADFLogger;
import utils.JSFUtils;
public class ConfigBean {
    private ADFLogger logger;

    public ConfigBean() {
        super();
        logger = ADFLogger.createADFLogger(this.getClass());
    }

    public void loadFusionProperties() throws IOException {
        logger.info("loading properties");

        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        loadPropertiesForWebServiceCalls(resourceBundle);


        //JWT Token Authentication endpoint for UI validation
        
        JSFUtils.setExpressionValue("#{pageFlowScope.fusionJWTTokenEndpoint}",
                                    resourceBundle.getString("fusionJWTTokenEndpoint"));
        logger.info("ss:"+JSFUtils.resolveExpressionAsString("#{sessionScope.fusionJWTTokenEndpoint}"));

    }

    private void loadPropertiesForWebServiceCalls(ResourceBundle resourceBundle) {
        //properties: fusionUsername, fusionPassword, fusionEndpointURL, fusionFetchSize

        Map<String, String> pHeader = new HashMap<String, String>();
        pHeader.put("fusionUsername", resourceBundle.getString("fusionUsername"));
        pHeader.put("fusionPassword", resourceBundle.getString("fusionPassword"));
        pHeader.put("fusionEndpointURL", resourceBundle.getString("fusionEndpointURL"));

        pHeader.put("fusionCRMOpportunityEndpoint", resourceBundle.getString("fusionCRMOpportunityEndpoint"));

        pHeader.put("fusionFetchSize", resourceBundle.getString("fusionFetchSize"));


        String jwt = JSFUtils.resolveExpressionAsString("#{pageFlowScope.jwt}");

        if (jwt != null) {
            pHeader.put("fusionJWTToken", jwt);
        }

        logger.fine("pHeader=" + pHeader);
        JSFUtils.setExpressionValue("#{pageFlowScope.pHeader}", pHeader);

    }
    public String logoutAction() throws IOException{
          FacesContext fctx = FacesContext.getCurrentInstance();  
        ExternalContext ectx = fctx.getExternalContext();
        HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
        HttpSession session = (HttpSession)ectx.getSession(false);
        session.invalidate();
        
        //response.sendRedirect("SRWelcome.jspx");
          String url = ectx.getRequestContextPath() + "/faces/pages/logout.jsf";  
               try{  
                 ectx.redirect(url);  
               }  
               catch(Exception e){  
                 e.printStackTrace();  
               }  
               fctx.responseComplete();  
                 
               return null;  
      }
}
