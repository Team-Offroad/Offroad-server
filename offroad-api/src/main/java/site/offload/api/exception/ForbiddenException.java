package site.offload.api.exception;

import site.offload.enums.response.ErrorMessage;

public class ForbiddenException extends OffroadException {

    public ForbiddenException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
