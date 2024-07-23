package site.offload.api.exception;


import sites.offload.enums.ErrorMessage;

public class NotFoundException extends OffroadException {

    public NotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
