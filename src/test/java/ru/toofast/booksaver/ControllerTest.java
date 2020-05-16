package ru.toofast.booksaver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.toofast.booksaver.counter.dto.WordCountResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void endToEndTest() throws Exception {
        byte[] book = new ClassPathResource("static/dune.txt").getInputStream().readAllBytes();

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/")
                .file("file", book)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/wordCount").param("filename", "file"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        WordCountResponse response = objectMapper.readValue(contentAsString, WordCountResponse.class);

        Assertions.assertEquals(response.getWordCount().get("a"), 4739);
    }


}
