package com.ajlearnings.workbuddy;

public class Constants {

    public static String NUMBERS = "0123456789";

    public static class Email {
        public static class OTP {
            public static String Subject = "Email Verification | Workbuddy";
            public static String Body = "Hey!\n\nThe otp for email verification is %d. It is valid for %d seconds.\n\nThanks";
        }
        public static class UserReaction {
            public static String Subject = "User Reaction | Workbuddy";
            public static String Body = "Hey!\n\n%s has %s your comment.\n\nThanks";
        }
    }

    public static class Kafka {
        public static class Topics {
            public static final String UserReaction = "user-reaction-notification";
        }
    }
}

