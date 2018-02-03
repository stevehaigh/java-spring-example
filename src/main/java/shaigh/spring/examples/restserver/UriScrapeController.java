package shaigh.spring.examples.restserver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UriScrapeController {

    private UriScrapeModel model = new UriScrapeModel();

    /**
     * Searches the document at the specified URI for outgoing links and return them as a list.
     * @param uri
     * @return A collection of URIs, which may be empty, found at the URI specified.
     */
    @RequestMapping("/links")
    public Collection<String> scrapeUri(@RequestParam(value="uri") String uri) {
        return model.getLinks(uri);
    }
}
