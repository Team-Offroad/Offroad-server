package site.offload.offloadserver.api.exception;

import site.offload.offloadserver.api.message.ErrorMessage;

public class InternalServerException extends OffroadException{

    public InternalServerException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
