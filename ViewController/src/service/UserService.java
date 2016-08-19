package service;

import exception.UserTokenAuthenticationException;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import vo.UserInfo;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPBinding;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UserService {
    static Logger logger = Logger.getLogger((Class)UserService.class);
        private String username;
        private String password;
        private String jwtUserToken;
        private String userServiceEndPoint;

        public UserService(String userServiceEndPoint, String username, String password) {
            if (logger.isDebugEnabled()) {
                logger.debug((Object)"constructor - username/password");
            }
            this.username = username;
            this.password = password;
            this.userServiceEndPoint = userServiceEndPoint;
        }

        public UserService(String userServiceEndPoint, String jwtUserToken) {
            if (logger.isDebugEnabled()) {
                logger.debug((Object)"constructor - jwtUserToken");
                logger.debug((Object)("endpoint: " + userServiceEndPoint + " - jwtUserToken: " + jwtUserToken));
            }
            this.userServiceEndPoint = userServiceEndPoint;
            this.jwtUserToken = jwtUserToken;
        }

        public UserInfo findSelfUserDetails() throws UserTokenAuthenticationException, Exception {
            if (logger.isDebugEnabled()) {
                logger.debug((Object)"findSelfUserDetails()");
            }
            UserInfo currentUser = new UserInfo();
            try {
                Dispatch<SOAPMessage> dispatch;
                Node n;
                QName serviceName = new QName("http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "UserDetailsService");
                QName portName = new QName("http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "UserDetailsServiceSoapHttpPort");
                Service service = Service.create(serviceName);
                service.addPort(portName, "http://schemas.xmlsoap.org/wsdl/soap/http", this.userServiceEndPoint);
                Dispatch<SOAPMessage> bp = dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
                Map<String, Object> rc = bp.getRequestContext();
                rc.put("javax.xml.ws.soap.http.soapaction.use", Boolean.TRUE);
                rc.put("javax.xml.ws.soap.http.soapaction.uri", "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/findSelfUserDetails");
                if (this.jwtUserToken != null && this.jwtUserToken.length() > 0) {
                    String authZParameterValue = "Bearer " + this.jwtUserToken;
                    HashMap authMap = new HashMap();
                    ArrayList<String> authZList = new ArrayList<String>();
                    authZList.add(authZParameterValue);
                    authMap.put("Authorization", authZList);
                    rc.put("javax.xml.ws.http.request.headers", authMap);
                    if (logger.isDebugEnabled()) {
                        logger.debug((Object)("AuthHeader: " + authMap));
                    }
                } else {
                    rc.put("javax.xml.ws.security.auth.username", this.username);
                    rc.put("javax.xml.ws.security.auth.password", this.password);
                }
                MessageFactory factory = ((SOAPBinding)bp.getBinding()).getMessageFactory();
                SOAPMessage request1 = factory.createMessage();
                SOAPHeader header = request1.getSOAPHeader();
                SOAPBody body = request1.getSOAPBody();
                QName payloadName = new QName("http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/types/", "findSelfUserDetails", "typ");
                SOAPBodyElement payload = body.addBodyElement(payloadName);
                SOAPMessage reply = null;
                if (logger.isDebugEnabled()) {
                    ByteArrayOutputStream ba = new ByteArrayOutputStream();
                    request1.writeTo(ba);
                    logger.debug((Object)"XML Content:");
                    logger.debug((Object)ba.toString());
                    Map<String, Object> req = bp.getRequestContext();
                    for (String key : req.keySet()) {
                        logger.debug((Object)("Key: " + key + " = " + req.get(key)));
                    }
                }
                try {
                    reply = dispatch.invoke(request1);
                }
                catch (WebServiceException wse) {
                    wse.printStackTrace();
                    if (wse.getMessage() != null && wse.getMessage().contains((CharSequence)"401") && wse.getMessage().contains((CharSequence)"Unauthorized")) {
                        throw new UserTokenAuthenticationException("Unauthorized: User Token Invalid or Expired.");
                    }
                    throw wse;
                }
                Document doc = reply.getSOAPBody().extractContentAsDocument();
                currentUser.setPersonId(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "PersonId"));
                currentUser.setPersonNumber(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "PersonNumber"));
                currentUser.setUserId(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "UserId"));
                currentUser.setUserName(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "Username"));
                currentUser.setUserGUID(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "UserGUID"));
                currentUser.setActiveFlag(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "ActiveFlag"));
                currentUser.setUserDistinguishedName(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "UserDistinguishedName"));
                currentUser.setTitle(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "Title"));
                currentUser.setLastName(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "LastName"));
                currentUser.setFirstName(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "FirstName"));
                currentUser.setMiddleNames(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "MiddleNames"));
                currentUser.setDisplayName(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "DisplayName"));
                currentUser.setSuffix(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "Suffix"));
                currentUser.setHonors(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "Honors"));
                currentUser.setPreNameAdjunct(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "PreNameAdjunct"));
                currentUser.setKnownAs(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "KnownAs"));
                currentUser.setEmailAddress(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "EmailAddress"));
                currentUser.setPhoneId(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "PhoneId"));
                currentUser.setPhoneAreaCode(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "PhoneAreaCode"));
                currentUser.setPhoneCountryCodeNumber(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "PhoneCountryCodeNumber"));
                currentUser.setPhoneNumber(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "PhoneNumber"));
                currentUser.setLanguage(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "Language"));
                currentUser.setDateFormat(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "DateFormat"));
                currentUser.setTimeFormat(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "TimeFormat"));
                currentUser.setCurrency(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "Currency"));
                currentUser.setGroupingSeparator(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "GroupingSeparator"));
                currentUser.setDecimalSeparator(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "DecimalSeparator"));
                currentUser.setTimeZone(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "TimeZone"));
                currentUser.setClientEncoding(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "ClientEncoding"));
                currentUser.setTerritory(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "Territory"));
                currentUser.setFontSize(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "FontSize"));
                NodeList nlist = doc.getElementsByTagNameNS("http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "AccessibilityMode");
                if (nlist != null && nlist.getLength() > 0 && nlist.item(0).getChildNodes().getLength() > 0 && (n = nlist.item(0).getFirstChild()).getNodeValue() != null) {
                    currentUser.setAccessibilityMode(n.getNodeValue());
                }
                currentUser.setColorContrast(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "ColorContrast"));
                currentUser.setServerTime(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "ServerTime"));
                currentUser.setServerTimeForUserInfo(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "ServerTimeForUserInfo"));
                currentUser.setBusinessUnitId(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "BusinessUnitId"));
                currentUser.setBusinessUnitName(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "BusinessUnitName"));
                currentUser.setLegalEntityName(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "LegalEntityName"));
                currentUser.setPersonTypeId(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "PersonTypeId"));
                currentUser.setSystemPersonType(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "SystemPersonType"));
                currentUser.setUserPersonType(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "UserPersonType"));
                currentUser.setJobName(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "JobName"));
                currentUser.setDepartmentName(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "DepartmentName"));
                currentUser.setLocationTownOrCity(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "LocationTownOrCity"));
                currentUser.setLocationPostalCode(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "LocationPostalCode"));
                currentUser.setLocationCountry(this.getFirstChildNodeValue(doc, "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/", "LocationCountry"));
                return currentUser;
            }
            catch (UserTokenAuthenticationException ue) {
                throw ue;
            }
            catch (Exception e) {
                throw new Exception("Could not validate JWT token. Check the detailed error message in the log file.", e);
            }
        }

        private String getFirstChildNodeValue(Document doc, String namespaceURI, String namespaceLocalName) {
            Node n;
            NodeList nlist;
            if (logger.isDebugEnabled()) {
                logger.debug((Object)("getFirstChildNodeValue (namespaceURI: '" + namespaceURI + "' - namespaceLocalName: '" + namespaceLocalName + "')"));
            }
            String nodeValue = null;
            if (doc != null && (nlist = doc.getElementsByTagNameNS(namespaceURI, namespaceLocalName)) != null && nlist.getLength() > 0 && nlist.item(0).getChildNodes().getLength() > 0 && (n = nlist.item(0).getFirstChild()).getNodeValue() != null) {
                nodeValue = nlist.item(0).getFirstChild().getNodeValue();
            }
            return nodeValue;
        }

        public static void main(String[] args) {
            String endpoint = "https://my-server/hcmPeopleRolesV2/UserDetailsService";
            String jwtUserToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IjlYd05JNWJjZ3hqQjVHZTJjb0UwZ1NkeUl5WSJ9.eyJleHAiOjEzOTc2MjM3NTk1ODEsImlzcyI6Ind3dy5vcmFjbGUuY29tIiwicHJuIjoiTElTQS5KT05FUyIsImlhdCI6MTM5NzYwOTM1OTU4MX0.W-2TVxyqabELyBrrjmRIhLVcXGkFBS4LReKhy7Mq4SOn9ichlzKMOfJrZlPk9i2vYbqF32HzGes7lOY6uP_EMCFL5u1gyoG8bmgP4xtWgDbaiipAyE6AwqarQQLo6TGISPeJDLqQ-vhRpMxi5VfSLFUR4Yg3-NwkFORweUknSi4";
            UserService svc = new UserService(endpoint, jwtUserToken);
            try {
                svc.findSelfUserDetails();
            }
            catch (UserTokenAuthenticationException e) {
                System.out.println("Invalid Token exception!" + e.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
}
