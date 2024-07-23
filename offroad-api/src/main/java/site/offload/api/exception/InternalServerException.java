package site.offload.api.exception;


import sites.offload.enums.ErrorMessage;

public class InternalServerException extends OffroadException {

    public InternalServerException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
