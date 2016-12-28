package services;

import java.io.*;

/**
 * This service class is used for doing Manipulation to files.
 * Created by kartik.raina on 12/28/2016.
 */
public class FileServices {

    /**
     * This method is used for getting the data from file.
     * @param fileObj File from which data has to be fetched.
     * @return The text in the .txt file.
     */
    public String getFileData(File fileObj) throws IOException{
        StringBuilder strBuilder = new StringBuilder();
        String line;

        BufferedReader input = new BufferedReader(new FileReader(fileObj));

        while ((line = input.readLine()) != null) {
            strBuilder.append(line);
            strBuilder.append("\n");
        }
        //remove the last \n
        strBuilder.deleteCharAt(strBuilder.length()-1);

        //TODO: Remove it after testing
        System.out.print(strBuilder.toString().trim());

        input.close();
        return strBuilder.toString().trim();
    }

    /**
     * This method is used for putting the text data in a file.
     * @param fileData The text data that has to be inserted.
     * @param fileNameWithDir The name of the file with location that has to be created.
     * @return The object of {@link File}.
     */
    public File setFileData(String fileData, String fileNameWithDir) throws IOException{

        File outputFile = new File(fileNameWithDir);
        if(!outputFile.exists()){
            outputFile.createNewFile();
        }

        FileOutputStream fileOutputStreamObj = new FileOutputStream(outputFile);
        fileOutputStreamObj.write(fileData.getBytes());
        fileOutputStreamObj.close();
        return outputFile;
    }
}
