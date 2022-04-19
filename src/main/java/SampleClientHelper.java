import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SampleClientHelper {
    public List<String> getLastNamesFromFile(String fileName){
        List<String> lastNames = new ArrayList<>();
        try {
            String absolutePath = getResourceFilePath(fileName);
            File f = new File(absolutePath);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                String lastNameTrimmed = readLine.trim();
                if(StringUtils.isNotEmpty(lastNameTrimmed)){
                    lastNames.add(lastNameTrimmed);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastNames;
    }

    public String getResourceFilePath(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        return file.getAbsolutePath();
    }
}
