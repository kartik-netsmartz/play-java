import org.junit.Test;
import services.FileServices;
import services.StringServices;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * JUnit Test cases for Services
 * Created by kartik.raina on 12/28/2016.
 */
public class ServicesTest {

    @Test
    public void reverseStringTest(){
        String testString = "This is a test string";
        String reverseString;
        StringServices stringServices = new StringServices();
        reverseString = stringServices.reverseString(testString);

        assertEquals(new StringBuffer(reverseString).reverse().toString(), testString);
    }

    @Test
    public void getFileDataTest(){
        try {
            File testFile = new File("../../test_data_repository/DemoText.txt");
            FileServices fileServices = new FileServices();

            assertTrue(fileServices.getFileData(testFile).length() > 0);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void setFileDataTest(){
        try {
            File testFile = new File("../../test_data_repository/DemoText.txt");
            String testOutputFile = "../../test_data_repository/DemoTextResult.txt";
            FileServices fileServices = new FileServices();

            assertNotNull(fileServices.setFileData(fileServices.getFileData(testFile), testOutputFile));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
