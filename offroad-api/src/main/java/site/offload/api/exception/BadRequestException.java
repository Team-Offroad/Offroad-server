package site.offload.api.exception;


import sites.offload.enums.ErrorMessage;

public class BadRequestException extends OffroadException {

    public BadRequestException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
