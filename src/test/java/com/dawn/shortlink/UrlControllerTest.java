package com.dawn.shortlink;

import com.dawn.shortlink.controller.UrlController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/url/save_url")
                        .param("URL","t4634.sioa.dfa.sd25/daw4")
                        .param("description","ttdaskduh2385d3a你撒谎都st")
                        .param("timeout","6846350")
        ).andExpect(MockMvcResultMatchers.status().isOk())
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
