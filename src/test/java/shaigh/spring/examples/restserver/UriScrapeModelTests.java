package shaigh.spring.examples.restserver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UriScrapeModelTests {

    @Test
    public void simpleDocumentWithValidLinksShouldReturnAllLinks() throws IOException {
        HttpClient httpClient = getMockHttpClient("valid.html");
        UriScrapeModel modelUnderTest = new UriScrapeModel(httpClient);
        final Collection<String> links = modelUnderTest.getLinks("http://dummyvalue");
        Assert.assertTrue("Should find 3 links.", links.size() == 3);
    }

    @Test
    public void simpleDocumentWithNoLinksShouldReturnEmptyList() throws IOException {
        HttpClient httpClient = getMockHttpClient("valid_but_empty.html");
        UriScrapeModel modelUnderTest = new UriScrapeModel(httpClient);
        final Collection<String> links = modelUnderTest.getLinks("http://dummyvalue");
        Assert.assertTrue("Should find no links.", links.size() == 0);
    }

    @Test
    public void invalidHtmlShouldReturnEmptyList() throws IOException {
        HttpClient httpClient = getMockHttpClient("not_even_valid.html");
        UriScrapeModel modelUnderTest = new UriScrapeModel(httpClient);
        final Collection<String> links = modelUnderTest.getLinks("http://dummyvalue");
        Assert.assertTrue("Should find no links.", links.size() == 0);
    }

    /**
     *
     * @param htmlFileName - the contents to be returned when the request executes.
     * @return
     * @throws IOException
     */
    private HttpClient getMockHttpClient(String htmlFileName) throws IOException {
        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse response = mock(HttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        final String htmlFile = getClass().getResource("/" + htmlFileName).getFile();
        final String responseText = new String(Files.readAllBytes(Paths.get(htmlFile)));
        final ByteArrayInputStream responseStream = new ByteArrayInputStream(new String(responseText).getBytes());

        when(entity.getContent()).thenReturn(responseStream);
        when(response.getEntity()).thenReturn(entity);
        when(httpClient.execute(any())).thenReturn(response);

        return httpClient;
    }
}
