package site.offload.offloadserver.api.exception;

import site.offload.offloadserver.api.message.ErrorMessage;

public class NotFoundException extends OffroadException{

    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
