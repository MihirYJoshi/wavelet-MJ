import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<String>();
    ArrayList<String> strings2 = new ArrayList<String>();


    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return strings.toString();
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strings2.clear();
                for(int i = 0; i < strings.size(); i++){
                    if(strings.get(i).toString().contains(parameters[1])){
                        strings2.add(strings.get(i).toString());
                    }
                }
                if(strings2 == null){
                    return "";
                }else{
                    return strings2.toString();
                }
            }
            return "404 Not Found!";

        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strings.add(parameters[1]);
                    return "Done";
                }
            }
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchHandler());
    }
}
