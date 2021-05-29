package com.example.SMS_TEST;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);
    private final TwilioConfig twilioConfiguration;

    @Autowired
    public TwilioSmsSender(TwilioConfig twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
            MessageCreator creator = Message.creator(
                    new PhoneNumber(smsRequest.getPhoneNumber()),
                    new PhoneNumber(twilioConfiguration.getTrialNumber()),
                    smsRequest.getMessage()
            );
            creator.create();
            LOGGER.info("Send SMS {} " + smsRequest);
        }
        else {
            throw new IllegalArgumentException("Phone Number not valid");
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return true;
    }
}
