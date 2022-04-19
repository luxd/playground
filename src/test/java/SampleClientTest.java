import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SampleClientTest {

    @Test
    public void getLastNamesFromFileTest() {
        String resourceName = "lastNamesTest.txt";
        SampleClientHelper clientHelper = new SampleClientHelper();
        List<String> lastNames = clientHelper.getLastNamesFromFile(resourceName);
        assertEquals("should have 20 name in the list", 20, lastNames.size());
        assertEquals("last item in the list should be Norris", "Norris", lastNames.get(lastNames.size()-1));
    }
}
