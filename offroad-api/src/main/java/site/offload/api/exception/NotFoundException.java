package site.offload.api.exception;


import site.offload.enums.response.ErrorMessage;

public class NotFoundException extends OffroadException {

    public NotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
