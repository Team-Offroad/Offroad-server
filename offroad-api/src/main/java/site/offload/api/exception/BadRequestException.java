package site.offload.api.exception;


import site.offload.enums.response.ErrorMessage;

public class BadRequestException extends OffroadException {

    public BadRequestException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
