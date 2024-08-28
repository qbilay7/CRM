package com.golfsprint.GolfSprint.SMS;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private String twilioAccountSid;
    private String twilioAuthToken;

    public void setTwilioAccountSid(String twilioAccountSid) {
        this.twilioAccountSid = twilioAccountSid;
    }

    public void setTwilioAuthToken(String twilioAuthToken) {
        this.twilioAuthToken = twilioAuthToken;
    }

    public void sendSms(String toPhoneNumber, String fromPhoneNumber, String messageBody) {
        Twilio.init(twilioAccountSid, twilioAuthToken);

        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber), // Your Twilio phone number
                messageBody
        ).create();

        System.out.println("SMS sent with SID: " + message.getSid());
    }
}

