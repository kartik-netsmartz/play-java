package controllers;

import io.netty.util.internal.StringUtil;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.FileServices;
import services.StringServices;
import views.html.index;

import java.io.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {

        return ok(index.render("File Invert REST Demo"));
    }

    /**
     * This method first upload the txt file that is coming from
     * the form then reverses the content of the file
     * and the responds back with the same file having reversed data.
     * @return {@link Result}
     */
    public Result uploadAndReverse() {
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> textFile = body.getFile("text");
        File outgoingFile = null;
        File incomingFile;
        StringServices stringServicesObj;
        FileServices fileServices;
        String incomingFileData;

        if(textFile.getFilename().equals("")){
            flash("error", "Please select a file!!!");
            return redirect("/");
        }

        if (textFile.getFilename().endsWith(".txt")) {
            try {
                incomingFile = textFile.getFile();
                stringServicesObj = new StringServices();
                fileServices = new FileServices();
                incomingFileData = fileServices.getFileData(incomingFile);

                if (StringUtil.isNullOrEmpty(incomingFileData)){
                   flash("error", "Please check your file!!!");
                   return redirect("/");
                }

                outgoingFile = fileServices.setFileData(stringServicesObj.reverseString(incomingFileData), textFile.getFilename());

                response().setHeader("Content-disposition","attachment; filename="+outgoingFile.getName());

                return ok(outgoingFile);
            } catch (IOException ex) {
                flash("error", "OOPS something went wrong"+ ex.getLocalizedMessage());
                return redirect("/");
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
            flash("error", "Please select a .txt file only!!!");
            return redirect("/");
        }
    }
}
