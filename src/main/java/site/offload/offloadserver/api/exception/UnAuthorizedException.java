package site.offload.offloadserver.api.exception;

import site.offload.offloadserver.api.message.ErrorMessage;

public class UnAuthorizedException extends OffroadException{

    public UnAuthorizedException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
