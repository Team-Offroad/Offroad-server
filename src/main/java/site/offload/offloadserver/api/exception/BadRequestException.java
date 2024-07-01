package site.offload.offloadserver.api.exception;

import site.offload.offloadserver.api.message.ErrorMessage;

public class BadRequestException extends OffroadException{

    public BadRequestException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
