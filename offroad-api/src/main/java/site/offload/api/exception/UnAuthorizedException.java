package site.offload.api.exception;


import sites.offload.enums.ErrorMessage;

public class UnAuthorizedException extends OffroadException {

    public UnAuthorizedException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
