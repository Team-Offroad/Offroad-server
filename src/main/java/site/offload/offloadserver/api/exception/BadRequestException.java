package site.offload.offloadserver.api.exception;

import site.offload.offloadserver.api.message.ErrorMessage;

public class BadRequestException extends OffroadException{

    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
