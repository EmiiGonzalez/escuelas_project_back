package com.escuelas.project.escuelas_project.custom;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommonTestUtils {

    public static void operacionDeleteExitosa(MockMvc mockMvc, String ruta, HttpStatus status) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ruta).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(status.value()))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(0));
    }

    public static void verificarErrorPostSinDatos(MockMvc mockMvc, String content, String ruta, int errorCode,
            HttpStatus status) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ruta).contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().is(status.value()))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].errorCode").value(errorCode));
    }

    public static void verificarErrorPostDuplicado(MockMvc mockMvc, String content, String ruta, int errorCode,
            HttpStatus status) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ruta).contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().is(status.value()))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(errorCode));
    }

    public static void verificarErrorGetSinId(MockMvc mockMvc, String ruta, int errorCode, HttpStatus status)
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ruta).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(status.value()))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(errorCode));
    }

    public static void verificarErrorDeleteSinId(MockMvc mockMvc, String ruta, int errorCode, HttpStatus status)
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ruta).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(status.value()))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(errorCode));
    }

    public static void verificarErrorPut(MockMvc mockMvc, String content, String ruta, int errorCode,
            HttpStatus status) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(ruta).contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().is(status.value()))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(errorCode));
    }

}
