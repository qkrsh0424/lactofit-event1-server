package com.eventpage.lactofit.controller.api;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.amazonaws.services.transcribe.model.Media;
import com.eventpage.lactofit.model.xss.XssRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class RestApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private TestRestTemplate restTemplate;

    // @Test
    // public void 태그_치환() {
    //     String content = "<li>content</li>";
    //     String expected = "&lt;li&gt;content&lt;/li&gt;";
    //     ResponseEntity<XssRequestDto> response = restTemplate.postForEntity(
    //             "/api/xss",
    //             new XssRequestDto(content),
    //             XssRequestDto.class);
    //     // assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    //     // assertThat(response.getBody().getContent()).isEqualTo(expected);
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals(expected, response.getBody().getContent());
    // }

    // @Test
    // public void application_form_전송() {
    //     String content = "<li>content</li>";
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    //     MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    //     map.add("content", content);

    //     HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

    //     ResponseEntity<String> response = restTemplate.exchange("/api/xss/form",
    //             HttpMethod.POST,
    //             entity,
    //             String.class);

    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals("text/plain;charset=UTF-8",response.getHeaders().getContentType().toString());
    //     assertEquals(content, response.getBody());
    // }

    // @Test
    // public void 태그_치환() throws Exception {
    //     String content = "<li>content</li>";
    //     String expected = "&lt;li&gt;content&lt;/li&gt;";

    //     XssRequestDto dto = new XssRequestDto();
    //     dto.setContent(content);
    //     ObjectMapper om = new ObjectMapper();
    //     String json = om.writeValueAsString(dto);
    //     System.out.println(json);
    //     MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/xss")
    //         .accept(MediaType.APPLICATION_JSON)
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .content(json))
    //         .andReturn()
    //         ;
    //     log.info("resonse status= {}", result.getResponse().getStatus());
    //     log.info("resonse content= {}", result.getResponse().getContentAsString().replace("\"", ""));
    //     System.out.println(result.getResponse().getContentAsString());
    //     assertEquals(200, result.getResponse().getStatus());
    //     assertEquals(expected, result.getResponse().getContentAsString().replace("\"", ""));
    // }

    // @Test
    // public void form_전송() throws Exception {
    //     String content = "<li>content</li>";
    //     String expected = "&lt;li&gt;content&lt;/li&gt;";

    //     MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    //     map.add("content", content);

    //     MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/xss/form")
    //         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    //         .param("content", content))
    //         .andDo(MockMvcResultHandlers.print())
    //         .andReturn()
    //         ;
        
            
    //     log.info("resonse status= {}", result.getResponse().getStatus());
    //     log.info("resonse content= {}", result.getResponse().getContentAsString().replace("\"", ""));
    //     log.info("resonse content type= {}", result.getResponse().getContentType());
    //     assertEquals(200, result.getResponse().getStatus());
    //     assertEquals("text/plain;charset=UTF-8",result.getResponse().getContentType());
    //     assertEquals(content, result.getResponse().getContentAsString().replace("\"", ""));
    // }
}
