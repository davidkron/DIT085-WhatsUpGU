package tests.integration;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by david on 2/25/15.
 */
public class parts {

    public static void asserted_connect(ObjectInputStream in,ObjectOutputStream out,String id) throws IOException, ClassNotFoundException {
        out.writeObject(
                "<connection>" +
                        "<request>"+ id +"</request>" +
                        "</connection>"
        );
        out.flush();
        assertEquals(
                "<connection>" +
                        "<accepted>"+ id +"</accepted>" +
                        "</connection>",
                (String)in.readObject()
        );
    }

    public static void asserted_delete(ObjectInputStream in,ObjectOutputStream out,int messageid) throws IOException, ClassNotFoundException {
        out.writeObject(
                "<messageAction>" +
                        "<delete>" +
                        "<messageID>" + messageid + "</messageID>" +
                        "</delete>" +
                        "</messageAction>"
        );

        out.flush();
        assertEquals((String) in.readObject(), "<deleted>" + messageid + "</deleted>");
    }


    public static int asserted_add(ObjectInputStream in,ObjectOutputStream out,String receiver) throws IOException, ClassNotFoundException {
        out.flush();
        out.writeObject(
                "<messageAction>" +
                        "<add>" +
                        "<receiverID>" + receiver + "</receiverID>" +
                        "<content>HELLO</content>" +
                        "</add>" +
                        "</messageAction>"
        );
        out.flush();
        String message = (String)in.readObject();
        Pattern pattern = Pattern.compile("<added>(\\d+)</added>");
        Matcher m = pattern.matcher(message);
        assertTrue(m.matches());
        int ret = Integer.parseInt(m.group(1));
        TestCase.assertTrue(ret > 0);
        return ret;
    }

    public static void asserted_fetch(ObjectInputStream in,ObjectOutputStream out,String receiver) throws IOException, ClassNotFoundException {
        out.writeObject("<messageAction>" +
                "<fetch>" +
                "true" +
                "</fetch>" +
                "</messageAction>");
        String message = (String)in.readObject();
        TestCase.assertTrue(message.matches("<fetched>.+</fetched>"));
    }
}
