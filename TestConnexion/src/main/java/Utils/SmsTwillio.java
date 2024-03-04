package Utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsTwillio {

    public static final String ACCOUNT_SID = "AC388df8c464badee2b9fbb2cbb752bcab";
    public static final String AUTH_TOKEN = "7cdd9af51e979da2be6a3a15252dfd69";
    public static void sms(String s) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21658270646"),
                        new com.twilio.type.PhoneNumber("+14848709235"),
                        " Un tournoi sous le nom " +s+ "a été ajouté").create();
        System.out.println(message.getSid());
    }
    /* public static void smsCancelRent(String s,String s1) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21692294022"),
                new com.twilio.type.PhoneNumber("+19896608233"),
                " your rent from"+s+"to"+s1+" has been canceled ").create();
        System.out.println(message.getSid());
    }

    public static void smsRent(String s,String s1) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21692294022"),
                new com.twilio.type.PhoneNumber("+19896608233"),
                " your rent from "+s+" to "+s1+" has been done ").create();
        System.out.println(message.getSid());
    }*/
}
