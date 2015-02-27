package tests.units.server;

import junit.framework.TestCase;
import main.server.request.RequestCreator;
import org.junit.Test;

public class RequestCreatorTest extends TestCase {

    @Test
    public void testInstanciate(){
        new RequestCreator();
    }
}