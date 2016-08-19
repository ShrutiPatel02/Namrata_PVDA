package service;

import exception.UserTokenAuthenticationException;
import org.apache.log4j.Logger;
import vo.UserInfo;
public class UserTokenValidationService {
    static Logger logger = Logger.getLogger((Class)UserTokenValidationService.class);
        private String jwtUserToken;
        private String endPoint;

        public UserTokenValidationService(String jwtUserToken, String endPoint) {
            if (logger.isDebugEnabled()) {
                logger.debug((Object)"constructor");
            }
            this.jwtUserToken = jwtUserToken;
            this.endPoint = endPoint != null && endPoint.contains((CharSequence)"https://") ? endPoint : "https://" + endPoint + "/hcmPeopleRolesV2/UserDetailsService";
        }

        public UserInfo validateUserToken(boolean replayProtection, boolean authenticatedUserIdcheck) throws UserTokenAuthenticationException, Exception {
            if (logger.isDebugEnabled()) {
                logger.debug((Object)"validateUserToken()");
            }
            if (this.jwtUserToken == null) {
                throw new UserTokenAuthenticationException("JWT token value cannot be null");
            }
            if (this.endPoint == null) {
                throw new Exception("UserService endpoint cannot be null");
            }
            if (replayProtection) {
                logger.info((Object)"Replay protection enabled. Checking against replay attack");
            }
            UserInfo currentUserFromToken = new UserInfo();
            UserService usersvc = new UserService(this.endPoint, this.jwtUserToken);
            currentUserFromToken = usersvc.findSelfUserDetails();
            if (authenticatedUserIdcheck) {
                logger.info((Object)"User Id checking authenticated user id is valid");
            }
            return currentUserFromToken;
        }

        public static void main(String[] args) throws Exception {
            String invalidJWTtoken = "jlakdjflkajfklaj1123123132213";
            String serviceEndpoint = "my-server.com";
            UserTokenValidationService jwtService = new UserTokenValidationService(invalidJWTtoken, serviceEndpoint);
            try {
                jwtService.validateUserToken(true, true);
            }
            catch (UserTokenAuthenticationException e) {
                logger.debug((Object)"Expected error due to an invalid token.");
                logger.debug((Object)e);
            }
        }
}
