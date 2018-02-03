package shaigh.spring.examples.restserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UriScrapeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Not really a unit test as this uses the real http client and will attempt to make a real
     * external connection.
     */
    @Test
    public void loadingNonExistantUriShouldReturnErrorValue() throws Exception {
        this.mockMvc.perform(get("/links").param("uri", "http://foo.bar"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value("Oops. Something went wrong. Please ensure the request is of the form '/links?uri=<a valid URI>'"));
    }

    /**
     * Not really a unit test as this uses the real http client and will attempt to make a real
     * external connection.
     */
    @Test
    public void loadingInvalidtUriShouldReturnErrorValue() throws Exception {
        this.mockMvc.perform(get("/links").param("uri", "not_a_uri"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value("Oops. Something went wrong. Please ensure the request is of the form '/links?uri=<a valid URI>'"));
    }

    /**
     * Not really a unit test as this uses the real http client and will attempt to make a real
     * external connection.
     */
    @Test
    public void noUriShould400Response() throws Exception {
        this.mockMvc.perform(get("/links"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    /**
     * Not really a unit test as this uses the real http client and will attempt to make a real
     * external connection.
     */
    @Test
    public void wrongControllerPathShould400Response() throws Exception {
        this.mockMvc.perform(get("/foo"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }



}
