package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JSONController.class)
public class JSONControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Test
    public void testFlight() throws Exception {
        this.mvc.perform(
                get("/flights/flight")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$.Tickets[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$.Tickets[0].Passenger.LastName", is("Some other name")))
                .andExpect(jsonPath("$.Tickets[0].Price", is(200)));
    }

    @Test
    public void testFlights() throws Exception {
        this.mvc.perform(
                get("/flights")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].Departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$[0].Tickets[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$[0].Tickets[0].Passenger.LastName", is("Some other name")))
                .andExpect(jsonPath("$[0].Tickets[0].Price", is(200)))
                .andExpect(jsonPath("$[1].Departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$[1].Tickets[0].Passenger.FirstName", is("Some other name")))
                .andExpect(jsonPath("$[1].Tickets[0].Price", is(400)));
    }

    @Test
    public void testTicketsTotalObjectMapper() throws Exception {
            MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("  {\n" +
                            "    \"Tickets\": [\n" +
                            "      {\n" +
                            "        \"Passenger\": {\n" +
                            "          \"FirstName\": \"Some name\",\n" +
                            "          \"LastName\": \"Some other name\"\n" +
                            "        },\n" +
                            "        \"Price\": 200\n" +
                            "      },\n" +
                            "      {\n" +
                            "        \"Passenger\": {\n" +
                            "          \"FirstName\": \"Name B\",\n" +
                            "          \"LastName\": \"Name C\"\n" +
                            "        },\n" +
                            "        \"Price\": 150\n" +
                            "      }\n" +
                            "    ]\n" +
                            "  }");

            this.mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().string("{\n"+"\"result\": 350\n"+"}"));
    }
}
