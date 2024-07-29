package site.offload.api.exception;


import site.offload.enums.response.ErrorMessage;

public class InternalServerException extends OffroadException {

    public InternalServerException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
