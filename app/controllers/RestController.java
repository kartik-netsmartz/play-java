package controllers;

import io.netty.util.internal.StringUtil;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.FileServices;
import services.StringServices;

import java.io.File;
import java.io.IOException;

/**
 * Created by kartik.raina on 12/28/2016.
 */
public class RestController extends Controller{

    public Result uploadAndReverse(){
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        if(body.getFiles().size() <= 0){
            return badRequest("No File Sent!!!");
        }
        Http.MultipartFormData.FilePart<File> textFile = body.getFiles().get(0);
        File outgoingFile = null;
        File incomingFile;
        StringServices stringServicesObj;
        FileServices fileServices;
        String incomingFileData;

        if (textFile.getFilename().endsWith(".txt")) {
            try {
                incomingFile = textFile.getFile();
                stringServicesObj = new StringServices();
                fileServices = new FileServices();
                incomingFileData = fileServices.getFileData(incomingFile);

                if (StringUtil.isNullOrEmpty(incomingFileData)){
                    return badRequest("Please check your file!!!");
                }

                // No check required for null as .txt check has been applied above.
                outgoingFile = fileServices.setFileData(stringServicesObj.reverseString(incomingFileData), textFile.getFilename());

                response().setHeader("Content-disposition","attachment; filename="+outgoingFile.getName());

                return ok(outgoingFile);
            } catch (IOException ex) {
                return badRequest("OOPS something went wrong"+ ex.getLocalizedMessage());
            } finally {
                try {
                    if(outgoingFile != null && outgoingFile.exists()){
                        outgoingFile.deleteOnExit();
                    }
                } catch (Exception ex) {
                    /*ignore*/
                }
            }

        } else {
            return badRequest("Please send a .txt file only!!!");
        }
    }
}
