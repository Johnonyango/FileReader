package john.com.servlets;

import john.com.utilitiesDb.DbConnection;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

@WebServlet("Upload")
public class UploadFile extends HttpServlet{

    DbConnection dbConnection;
    public void init() throws ServletException {
        ServletContext scx = getServletContext();
        dbConnection = (DbConnection) scx.getAttribute("dbConnection");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        File file ;
        int maxFileSize = 5000 * 1024;
        int maxMemSize = 5000 * 1024;
        ServletContext context = getServletContext();
        String filePath = context.getInitParameter("file-upload");

        // Verify the content type
        String contentType = request.getContentType();

        if ((contentType.indexOf("multipart/form-data") >= 0)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(maxMemSize);
            factory.setRepository(new File("c:\\temp"));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax( maxFileSize );

            try {
                List fileItems = upload.parseRequest(request);
                Iterator i = fileItems.iterator();

                writer.println("<html>");
                writer.println("<head>");
                writer.println("<title>JSP File upload</title>");
                writer.println("</head>");
                writer.println("<body>");

                while ( i.hasNext () ) {
                    FileItem fi = (FileItem)i.next();
                    if ( !fi.isFormField () ) {
                        // Get the uploaded file parameters
                        String fieldName = fi.getFieldName();
                        String fileName = fi.getName();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();

                        // Write the file
                        if( fileName.lastIndexOf("\\") >= 0 ) {
                            file = new File( filePath +
                                    fileName.substring( fileName.lastIndexOf("\\"))) ;
                        } else {
                            file = new File( filePath +
                                    fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                        }
                        fi.write( file ) ;
                        writer.println("Uploaded Filename: " + filePath +
                                fileName + "<br>");
                    }
                }
                writer.println("</body>");
                writer.println("</html>");
            } catch(Exception ex) {
                System.out.println(ex);
            }
        } else {
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>Servlet upload</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<p>No file uploaded</p>");
            writer.println("</body>");
            writer.println("</html>");
        }
    }
}
