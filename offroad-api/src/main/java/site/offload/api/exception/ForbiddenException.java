package site.offload.api.exception;

import sites.offload.enums.ErrorMessage;

public class ForbiddenException extends OffroadException {

    public ForbiddenException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
