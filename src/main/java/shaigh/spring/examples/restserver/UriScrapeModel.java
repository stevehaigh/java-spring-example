package shaigh.spring.examples.restserver;

import com.sun.jndi.toolkit.url.Uri;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UriScrapeModel {

    private HttpClient client;

    /**
     * Regular constructor will use a real HttpClient.
     */
    public UriScrapeModel() {
        this.client = HttpClientBuilder.create().build();
    }

    /**
     * Allow an instance to be generated for test purposes.
     * @param client
     */
    public UriScrapeModel(HttpClient client) {
        this.client = client;
    }

    /**
     * Grab the document from the specified URI and find any links in it.
     * @param uri
     * @return a list of links found in the document.
     */
    public Collection<String> getLinks(String uri) {
        Collection<String> urisFound = new ArrayList<>();

        try {
            String document = requestDocument(uri);
            urisFound = findAllUrisInDocument(document);
        } catch (IOException e) {
            urisFound.clear();
            urisFound.add("Oops. Something went wrong. Please ensure the request is of the form '/links?uri=<a valid URI>'");
        }

        return urisFound;
    }


    /**
     * Retrieve a document from the specified URI.
     * @param uri
     * @return The document found at the specified URI.
     * @throws IOException
     */
    private String requestDocument(String uri) throws IOException {
        HttpGet request = new HttpGet(uri);

        HttpResponse response = this.client.execute(request);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        String result = rd.lines().collect(Collectors.joining());

        return result;
    }

    /**
     * Find links in the document.
     * @param document
     * @return a list, possibly empty, of all href links found in the document.
     */
    private Collection<String> findAllUrisInDocument(String document) {
        Collection<String> result = new ArrayList<>();
        Document parsedDoc = Jsoup.parse(document);
        Elements links = parsedDoc.select("a[href]"); 

        for(Element link: links) {
            String href = link.absUrl("href");
            if (!href.isEmpty()) {
                result.add(href);
            }
        }

        return result;
    }
}
