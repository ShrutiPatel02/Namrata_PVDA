package bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;
import vo.UserInfo;
import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;
import org.apache.log4j.Logger;

import service.UserTokenValidationService;

import utils.JSFUtils;
public class UserTokenBean {
    static Logger logger = Logger.getLogger((Class) UserTokenBean.class);
    private String serviceEndpoint;

    public UserTokenBean()
    {
      if (logger.isDebugEnabled())
      {
        logger.debug((Object) "constructor");
      }
      this.serviceEndpoint = null;
    }

    public UserTokenBean(String serviceEndpoint)
    {
      if (logger.isDebugEnabled())
      {
        logger.debug((Object) ("constructor = " + serviceEndpoint));
      }
      this.serviceEndpoint = serviceEndpoint;
    }

    public void validateUserToken()
      throws Exception
    {
      if (logger.isDebugEnabled())
      {
        logger.debug((Object) "validateUserToken()");
      }
//                  String userId="4";//"300000020499805";
      FacesContext fctx = FacesContext.getCurrentInstance();
      ExternalContext ectx = fctx.getExternalContext();
                 //HttpSession userSession = (HttpSession)ectx.getSession(true);
//                  userSession.setAttribute("userId",userId);
//                  userSession.setAttribute("loginEmpId",userId);
      if ("".equals(this.serviceEndpoint) || this.serviceEndpoint == null)
      {
        System.out.println("SS::" + JSFUtils.resolveExpressionAsString("#{pageFlowScope.fusionJWTTokenEndpoint}"));
        this.serviceEndpoint = (String) JSFUtils.resolveExpression(("#{pageFlowScope.fusionJWTTokenEndpoint}"));
        if ("".equals(this.serviceEndpoint) || this.serviceEndpoint == null)
        {
          this.serviceEndpoint = (String) JSFUtils.resolveExpression((String) "#{pageFlowScope.serviceEndPoint}");
        }
        if (logger.isDebugEnabled())
        {
          logger.info((Object) ("Service Endpoint for JWT User Token = " + this.serviceEndpoint));
        }
      }
      if ("".equals(this.serviceEndpoint) || this.serviceEndpoint == null)
      {
        throw new Exception("Service Endpoint not setup for JWT User token");
      }
      //            serviceEndpoint = "https://my-server/hcmPeopleRolesV2/UserDetailsService";
      //          String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IjlYd05JNWJjZ3hqQjVHZTJjb0UwZ1NkeUl5WSJ9.eyJleHAiOjEzOTc2MjM3NTk1ODEsImlzcyI6Ind3dy5vcmFjbGUuY29tIiwicHJuIjoiTElTQS5KT05FUyIsImlhdCI6MTM5NzYwOTM1OTU4MX0.W-2TVxyqabELyBrrjmRIhLVcXGkFBS4LReKhy7Mq4SOn9ichlzKMOfJrZlPk9i2vYbqF32HzGes7lOY6uP_EMCFL5u1gyoG8bmgP4xtWgDbaiipAyE6AwqarQQLo6TGISPeJDLqQ-vhRpMxi5VfSLFUR4Yg3-NwkFORweUknSi4";
      String jwtToken = (String) JSFUtils.resolveExpression((String) "#{pageFlowScope.jwt}");
      if (logger.isDebugEnabled())
      {
        logger.debug((Object) ("jwtToken Value = " + jwtToken));
        if (jwtToken != null)
        {
          logger.debug((Object) ("jwtToken size = " + jwtToken.length()));
        }
      }
      UserTokenValidationService userTokenService = new UserTokenValidationService(jwtToken, this.serviceEndpoint);
      boolean replayProtection = true;
      boolean authenticatedUserIdcheck = true;
      //UserInfo userInfo = userTokenService.validateUserToken(replayProtection, authenticatedUserIdcheck);
      
      /*Short Circuit code*/  //TODO
            UserInfo userInfo = new UserInfo();
            userInfo.setPersonId("4");
            userInfo.setUserName("David");
            userInfo.setUserId("4");
            userInfo.setTitle("Mr");
            userInfo.setBusinessUnitName("Production");
            userInfo.setDepartmentName("Production");
            userInfo.setDisplayName("David");
            userInfo.setJobName("");
            userInfo.setPersonTypeId("4");
            userInfo.setEmailAddress("david@gmail.com");
      
      JSFUtils.setExpressionValue((String) "#{pageFlowScope.userInfo}", (Object) userInfo);
      HttpSession userSession = (HttpSession) ectx.getSession(true);
      System.out.println("PersonId:"+userInfo.getPersonId()+"UserId"+userInfo.getUserId());
      userSession.setAttribute("userId", userInfo.getPersonId());
      userSession.setAttribute("fullName", userInfo.getUserName());
      userSession.setAttribute("userLoginId", userInfo.getUserId());
      userSession.setAttribute("title", userInfo.getTitle());
      userSession.setAttribute("businessName", userInfo.getBusinessUnitName());
      userSession.setAttribute("deptName", userInfo.getDepartmentName());
      userSession.setAttribute("displayName", userInfo.getDisplayName());
      userSession.setAttribute("jobName", userInfo.getJobName());
      userSession.setAttribute("personTypeId", userInfo.getPersonTypeId());
      userSession.setAttribute("loginEmpId", userInfo.getPersonId());
      if (logger.isDebugEnabled())
      {
        logger.debug((Object) ("DisplayName: " + userInfo.getDisplayName()));
        logger.debug((Object) ("Username: " + userInfo.getUserName()));
        logger.debug((Object) ("Email: " + userInfo.getEmailAddress()));
      }
    }

    public void handleInvalidToken()
    {
      if (logger.isDebugEnabled())
      {
        logger.debug((Object) "handleInvalidToken");
      }
      String jwt = (String) JSFUtils.resolveExpression((String) "#{pageFlowScope.jwt}");
      if (logger.isDebugEnabled())
      {
        logger.debug((Object) ("jwt = " + jwt));
      }
      if ("".equals(jwt) || jwt == null)
      {
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null,
                      new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                       "Invalid JWT token value for server " + this.getServiceEndpoint()));
      }
      try
      {
        ControllerContext context = ControllerContext.getInstance();
        ViewPortContext currentRootViewPort = context.getCurrentRootViewPort();
        if (currentRootViewPort.isExceptionPresent())
        {
          Exception exData = currentRootViewPort.getExceptionData();
          if (exData == null && context.getCurrentViewPort().isExceptionPresent())
          {
            exData = context.getCurrentViewPort().getExceptionData();
          }
          if (exData != null)
          {
            if (logger.isDebugEnabled())
            {
              logger.debug((Object) "Exception Captured: ", (Throwable) exData);
            }
            currentRootViewPort.clearException();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", exData.getMessage()));
          }
          else if (logger.isDebugEnabled())
          {
            logger.debug((Object) "Could not capture the exception");
          }
        }
      }
      catch (Exception e)
      {
        logger.error((Object) "Error while printing excepitons", (Throwable) e);
      }
    }

    public String getServiceEndpoint()
    {
      return this.serviceEndpoint;
    }
}
