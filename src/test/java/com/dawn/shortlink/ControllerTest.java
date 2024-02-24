package com.dawn.shortlink;

import com.dawn.shortlink.controller.ShortLinkApiController;

import com.dawn.shortlink.domain.pojo.ShortUrlRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class ControllerTest extends ShortLinkApplicationTests{

    @Autowired
    ShortLinkApiController shortLinkApiController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(shortLinkApiController).build();
    }

    @Test
    public void saveTest() throws Exception {

        ShortUrlRequestBody req = new ShortUrlRequestBody("http://localhost:8787/api/shortlink/test","本机测试",100000000L);
        ObjectMapper mapper = new ObjectMapper();
        String jsonstr = mapper.writeValueAsString(req);
        System.out.println(jsonstr);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/shortlink/generate_short_url")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .contentType("application/json")
                        .content(jsonstr)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString(Charset.defaultCharset()));
    }

    /*
    uGh6a1

     */
    @Test
    public void getUrlTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/shortlink/get_origin_url")
                        .param("short_url","k0irG3")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString(Charset.defaultCharset()));
    }

    @Test
    public void getRespTest() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/shortlink/test")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString(Charset.defaultCharset()));
    }

}
