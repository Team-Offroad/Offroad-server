package site.offload.offloadserver.api.exception;

import site.offload.offloadserver.api.message.ErrorMessage;

public class ForbiddenException extends OffroadException{

    public ForbiddenException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
