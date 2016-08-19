package exception;

import oracle.jbo.ValidationException;
import oracle.jbo.common.ResourceBundleDef;

public class UserTokenAuthenticationException extends ValidationException {
    private static final long serialVersionUID = 1;

    public UserTokenAuthenticationException(ResourceBundleDef resourceBundleDef, String string, Object[] object) {
        super(resourceBundleDef, string, object);
    }

    public UserTokenAuthenticationException(String string, String string2, Object[] object) {
        super(string, string2, object);
    }

    public UserTokenAuthenticationException(String string) {
        super(string);
    }

    public UserTokenAuthenticationException(Class c, String string, Object[] object) {
        super(c, string, object);
    }
}

