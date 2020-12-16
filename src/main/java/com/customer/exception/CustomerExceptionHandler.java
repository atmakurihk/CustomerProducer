    package com.customer.exception;

    import com.customer.model.FailureResponse;
    import com.customer.utils.ApplicationConstants;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.AuthenticationException;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.ServletRequestBindingException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.context.request.WebRequest;

    import javax.servlet.http.HttpServletRequest;
    import javax.validation.ConstraintViolationException;

    @ControllerAdvice
    public class CustomerExceptionHandler  {

        @ExceptionHandler(GenericException.class)
        public ResponseEntity<FailureResponse> handleException(RuntimeException ex, WebRequest webRequest)
        {
            FailureResponse failureResponse = new FailureResponse();
            failureResponse.setErrorType(ex.getClass().getName());
            failureResponse.setMessage(ex.getMessage());
            failureResponse.setStatus(ApplicationConstants.FAILURE);
        return new ResponseEntity<>(failureResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity requestValidation(MethodArgumentNotValidException ex) {

            FailureResponse response = new FailureResponse();
            response.setStatus(ApplicationConstants.FAILURE);
            response.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
            response.setErrorType(ex.getClass().getSimpleName());

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<FailureResponse> handleValidationException(RuntimeException ex, WebRequest webRequest)
        {
            FailureResponse failureResponse = new FailureResponse();
            failureResponse.setErrorType(ex.getClass().getName());
            failureResponse.setMessage(ex.getMessage());
            failureResponse.setStatus(ApplicationConstants.FAILURE);
            return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<FailureResponse> handleAuthException(HttpServletRequest request, AuthenticationException ex) {
            FailureResponse response = new FailureResponse();
            response.setErrorType(ex.getClass().getSimpleName());
            response.setStatus(ApplicationConstants.FAILURE);
            response.setMessage("Authorization required. "+ex.getMessage());
            return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
        }
        @ExceptionHandler(ServletRequestBindingException.class)
        public ResponseEntity<FailureResponse> handleBindingException(HttpServletRequest request, ServletRequestBindingException ex) {
            FailureResponse response = new FailureResponse();
            response.setErrorType(ex.getClass().getSimpleName());
            response.setStatus(ApplicationConstants.FAILURE);
            response.setMessage("Request headers are missing "+ex.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

    }
