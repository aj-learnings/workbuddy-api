package com.ajlearnings.workbuddy.helpers;

public class EmailTemplates {
    public static class OTP {
        public static String Subject = "Email Verification | Workbuddy";
        public static String Body = """
            <html>
                   <body style="font-family: Arial, sans-serif; color: #333;">
                       <div style="text-align: left;">
                           <p style="font-size: 14px; color: #555;">
                               Hey %s !<br><br>
                               Please verify your email using the below OTP.
                           </p>
                           <h1 style="color: #007BFF; font-size: 36px; margin: 0;">%d</h1>
                           <p style="font-size: 14px; color: #555;">
                               It is valid for <strong>%d seconds</strong>.
                           </p>
                           <p style="font-size: 12px; color: #999; margin-top: 20px;">
                               Thanks,<br>
                               The Workbuddy Team
                           </p>
                       </div>
                   </body>
            </html>
            """;
    }
    public static class UserReaction {
        public static String Subject = "User Reaction | Workbuddy";
        public static String Body = """
            <html>
               <body style="font-family: Arial, sans-serif; color: #333;">
                   <div style="text-align: left;">
                       <p style="font-size: 14px; color: #555;">
                           Hey %s !<br><br>
                           %s has <strong>%s</strong> the below comment.
                       </p>
                       <div style="border: 1px solid #c5c2c2; padding: 0px 4px; border-radius: 4px;">
                           %s
                       </div>
                       <p style="font-size: 12px; color: #999; margin-top: 20px;">
                           Thanks,<br>
                           The Workbuddy Team
                       </p>
                   </div>
               </body>
            </html>
            """;
    }

}
