package cz.zcu.kiv.sehr.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghessova on 17.12.16.
 */
public class FileUploaderClient {


    public static void main(String[] args) {
        //deleteDocument();
        uploadFile();


    }

    private static void uploadFile() {
        // the file we want to upload
        File inFile = new File("/home/gabi/openEHR-EHR-CLUSTER.address.v1.adl");
        //File inFile = new File("/data/git/SemanticEHR/data/openEHR-EHR-CLUSTER.address.v1.adl");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(inFile);
            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

            // server back-end URL
            HttpPost httppost = new HttpPost("http://localhost:8080/webapi/archetypes");
            MultipartEntity entity = new MultipartEntity();
            // set the file input stream and file name as arguments
            entity.addPart("file", new InputStreamBody(fis, inFile.getName()));
            httppost.setEntity(entity);
            // execute the request
            HttpResponse response = httpclient.execute(httppost);

            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");

            System.out.println("[" + statusCode + "] " + responseString);

        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file");
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
            }
        }
    }

    public static void deleteDocument() {
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

            //HttpDelete httpDelete = new HttpDelete("http://localhost:8080/webapi/archetypes&archetypeId=" + "5858fcf7adb83d12ce02bacb");
            HttpDelete httpDelete = new HttpDelete("http://localhost:8080/webapi/archetypes");
            HttpResponse response = httpclient.execute(httpDelete);

            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");

            System.out.println("[" + statusCode + "] " + responseString);
        /*List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("archetypeId", "5858fcf7adb83d12ce02bacb"));
        try {
            httpDelete.setsetEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
