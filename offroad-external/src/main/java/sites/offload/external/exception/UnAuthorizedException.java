package sites.offload.external.exception;


import sites.offload.external.enums.ErrorMessage;

public class UnAuthorizedException extends OffroadException {

    public UnAuthorizedException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
