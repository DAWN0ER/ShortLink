package com.dawn.shortlink;

import com.dawn.shortlink.controller.UrlController;
import com.dawn.shortlink.domain.ShortUrlRequestBody;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

public class UrlControllerTest extends ShortLinkApplicationTests{

    @Autowired
    UrlController urlController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
    }

    @Test
    public void saveTest() throws Exception {
//        ShortUrlRequestBody r = new ShortUrlRequestBody("test_url","test_des",100);
        String jsonstr = "{\"description\":\"test_des\",\"url\":\"test_url\",\"timeout\":100}";
        System.out.println(jsonstr);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/shortlink/generate_short_url")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .contentType("application/json")
                        .content(jsonstr)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString(Charset.defaultCharset()));
    }

    @Test
    public void getUrlTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/url/get_short_url")
                        .param("URL","www.repecdsat.com")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString(Charset.defaultCharset()));
    }

}
