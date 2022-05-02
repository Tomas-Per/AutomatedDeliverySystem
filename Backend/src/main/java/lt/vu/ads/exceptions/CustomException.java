package lt.vu.ads.exceptions;

public class CustomException extends RuntimeException {


        private String message;

        public CustomException(String msg)
        {
            super(msg);
            this.message = msg;
        }

}
