package site.offload.api.exception;


import site.offload.enums.response.ErrorMessage;

public class UnAuthorizedException extends OffroadException {

    public UnAuthorizedException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
