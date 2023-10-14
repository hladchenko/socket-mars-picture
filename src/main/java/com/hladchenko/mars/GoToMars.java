package com.hladchenko.mars;

import com.hladchenko.jupiter.client.Jupiter;
import com.hladchenko.jupiter.client.JupiterClient;
import com.hladchenko.jupiter.response.JupiterResponse;

public class GoToMars {

    private static final String TOKEN = "";
    private static final String HOST = "api.nasa.gov";
    private static final String PATH = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=%s".formatted(TOKEN);


    public static void main(String[] args) {

//        JsonNode jsonNode = SocketUtil.sendSSLRequest(HOST, PATH);

        Jupiter jupiter = new JupiterClient();
        JupiterResponse response = jupiter.head(PATH);



//        Iterator<JsonNode> elements = jsonNode.elements();
//        List<String> list = new ArrayList<>();
//        while (elements.hasNext()) {
//            JsonNode element = elements.next();
//            String imageURL = element.get("img_src").asText();
//            list.add(imageURL);
//        }
//        URI uri = URI.create(list.get(0));
//        String s = SocketUtil.sendRequestAndGetHeaders(uri.getHost(), uri.getPath());
//        System.out.println(list);
//    }
    }
}