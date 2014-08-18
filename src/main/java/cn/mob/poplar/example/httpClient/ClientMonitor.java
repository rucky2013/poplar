package cn.mob.poplar.example.httpClient;

import cn.mob.poplar.util.HttpClient;

/**
 * @version 1.0 date: 2014/8/18
 * @author: Dempe
 */
public class ClientMonitor {

    public static void main(String args[]) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.setMethod("POST");
        httpClient.setContentType(HttpClient.ContentType.application_x_www_form_urlencoded);
        httpClient.open("http://localhost:8889/dempe/hello");
        httpClient.addPostParameter("name", "dempe");
        httpClient.post();
        httpClient.getResponseCode();
        String message = httpClient.getResponseMessage();
        System.out.println(message);

//        Scanner scanner = new Scanner(new FileInputStream("F:\\IdeaProjects\\poplar\\README.md"));
//        scanner.useDelimiter("\\A");
//        String str = scanner.next();
//        System.out.println(str);
    }
}
