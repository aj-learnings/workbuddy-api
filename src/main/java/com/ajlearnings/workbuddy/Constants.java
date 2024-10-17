package com.ajlearnings.workbuddy;

public class Constants {

    public static String NUMBERS = "0123456789";

    public static class Email {
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

