package site.offload.external.exception;


import site.offload.external.enums.ErrorMessage;

public class UnAuthorizedException extends OffroadException {

    public UnAuthorizedException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
