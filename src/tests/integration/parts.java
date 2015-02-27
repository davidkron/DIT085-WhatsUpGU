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
class parts {

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
                        "<delete>" +
                        "<messageID>" + messageid + "</messageID>" +
                        "</delete>");

        out.flush();
        assertEquals((String) in.readObject(), "<deleted>" + messageid + "</deleted>");
    }

    public static void asserted_delete_failed(ObjectInputStream in,ObjectOutputStream out,int messageid) throws IOException, ClassNotFoundException {
        out.writeObject(
                        "<delete>" +
                        "<messageID>" + messageid + "</messageID>" +
                        "</delete>");
        out.flush();
        TestCase.assertTrue(((String) in.readObject()).matches("<error>.*</error>"));
    }


    public static int asserted_add(ObjectInputStream in,ObjectOutputStream out,String receiver) throws IOException, ClassNotFoundException {
        out.flush();
        out.writeObject(
                        "<add>" +
                        "<receiverID>" + receiver + "</receiverID>" +
                        "<content>HELLO</content>" +
                        "</add>"
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

    public static void asserted_add_fail(ObjectInputStream in,ObjectOutputStream out,String receiver,String content) throws IOException, ClassNotFoundException {
        out.flush();
        out.writeObject(
                "<add>" +
                        "<receiverID>" + receiver + "</receiverID>" +
                        "<content>"+ content +"</content>" +
                        "</add>"
        );
        out.flush();
        String message = (String)in.readObject();
        TestCase.assertTrue((message).matches("<error>.*</error>"));
    }

    public static void asserted_fetch(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeObject("<fetch>" +
                "true" +
                "</fetch>");
        String message = (String)in.readObject();
        TestCase.assertTrue(message.matches("<fetched>.+</fetched>"));
    }

    public static void asserted_replace(ObjectInputStream in, ObjectOutputStream out, int messageID) throws IOException, ClassNotFoundException {
        out.writeObject("<replace>" +
                        "<content>STUFF</content>" +
                        "<messageID>" + messageID + "</messageID>" +
                        "</replace>"
                        );

        out.flush();
        assertEquals((String) in.readObject(), "<replaced>" + messageID + "</replaced>");
    }

    public static void asserted_connect_fail(ObjectInputStream in, ObjectOutputStream out, String id) throws IOException, ClassNotFoundException {
        out.writeObject(
                "<connection>" +
                        "<request>"+ id +"</request>" +
                        "</connection>"
        );
        out.flush();
        TestCase.assertTrue(((String)in.readObject()).matches("<error>.*</error>"));
    }


    public static void asserted_fetch_fail(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeObject("<fetch>" +
                "true" +
                "</fetch>");
        String message = (String)in.readObject();
        TestCase.assertTrue(message.matches("<error>.*</error>"));
    }

    public static void asserted_replace_fail(ObjectInputStream in, ObjectOutputStream out, int msg, String content) throws IOException, ClassNotFoundException {
        out.writeObject("<replace>" +
                        "<content>" + content +"</content>" +
                        "<messageID>" + msg + "</messageID>" +
                        "</replace>"
        );
        String message = (String)in.readObject();
        TestCase.assertTrue(message.matches("<error>.*</error>"));
    }

    public static void asserted_fetchcomplete(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeObject("<fetchComplete>" +
                "true" +
                "</fetchComplete>");
        String message = (String)in.readObject();
        TestCase.assertTrue(message.matches("<fetchCompleted>.+</fetchCompleted>"));
    }

    public static void asserted_fetchcomplete_fail(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeObject("<fetchComplete>" +
                "true" +
                "</fetchComplete>");
        String message = (String)in.readObject();
        TestCase.assertTrue(message.matches("<error>.*</error>"));
    }
}
