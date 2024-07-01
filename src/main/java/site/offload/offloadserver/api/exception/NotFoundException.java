package site.offload.offloadserver.api.exception;

import site.offload.offloadserver.api.message.ErrorMessage;

public class NotFoundException extends OffroadException{

    public NotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
