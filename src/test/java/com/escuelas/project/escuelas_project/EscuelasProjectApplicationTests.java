package com.escuelas.project.escuelas_project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.escuelas.project.escuelas_project.custom.CommonTestUtils;
import com.escuelas.project.escuelas_project.escuela.entities.EscuelaResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EscuelasProjectApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	// Post test start

	@Test
	@Transactional
	void llamada_a_post_escuelas_devuelve_escuela_guardada_200() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/escuelas/save").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"nombre\": \"test2\" }")).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("test2"));
	}

	@Test
	void llamada_a_post_escuelas_sin_nombre_devuelve_error_400_codeError_1() throws Exception {
		CommonTestUtils.verificarErrorPostSinDatos(mockMvc, "{ \"nombre\": \"\" }", "/api/v1/escuelas/save", 1,
				HttpStatus.BAD_REQUEST);
	}

	@Test
	@Transactional
	void llamada_a_post_escuelas_sin_nombre_devuelve_error_400_codeError_4() throws Exception {
		this.crearEscuelas(mockMvc);

		CommonTestUtils.verificarErrorPostDuplicado(mockMvc, "{ \"nombre\": \"test\" }", "/api/v1/escuelas/save", 4,
				HttpStatus.BAD_REQUEST);
	}
	// Post test end

	// Get tests by id start

	@Test
	void llamada_a_get_escuelas_devuelve_escuelas_activas_200() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/escuelas/find/all").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	@Transactional
	void llamada_a_get_escuelas_por_id_devuelve_escuela_activa_200() throws Exception {
		String id = this.crearEscuelas(mockMvc);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/escuelas/find/" + id ).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("test"));
	}

	@Test
	@Transactional
	void llamada_a_get_escuelas_por_id_devuelve_escuela_inactiva_400() throws Exception {
		String id = this.crearEscuelas(mockMvc);

		CommonTestUtils.operacionDeleteExitosa(mockMvc, "/api/v1/escuelas/disable/" + id, HttpStatus.OK);

		CommonTestUtils.verificarErrorGetSinId(mockMvc, "/api/v1/escuelas/find/" + id, 2, HttpStatus.BAD_REQUEST);
	}

	@Test
	void llamada_a_get_escuelas_por_id_devuelve_bad_request_400() throws Exception {
		CommonTestUtils.verificarErrorGetSinId(mockMvc, "/api/v1/escuelas/find/error", 5, HttpStatus.BAD_REQUEST);
	}

	@Test
	void llamada_a_get_escuelas_por_id_devuelve_not_found_404() throws Exception {
		CommonTestUtils.verificarErrorGetSinId(mockMvc, "/api/v1/escuelas/find/99999", 3, HttpStatus.NOT_FOUND);
	}

	// Get tests by id end
	// Delete test start

	@Test
	void llamada_a_delete_escuelas_sin_id_devuelve_error_404() throws Exception {
		CommonTestUtils.verificarErrorDeleteSinId(mockMvc, "/api/v1/escuelas/disable/", 6, HttpStatus.NOT_FOUND);
	}

	@Test
	void llamada_a_delete_escuelas_id_erroneo_devuelve_error_404() throws Exception {
		CommonTestUtils.verificarErrorDeleteSinId(mockMvc, "/api/v1/escuelas/disable/error", 5, HttpStatus.BAD_REQUEST);
	}

	@Test
	void llamada_a_delete_escuelas_id_no_existe_devuelve_error_404() throws Exception {
		CommonTestUtils.verificarErrorDeleteSinId(mockMvc, "/api/v1/escuelas/disable/9999999", 3, HttpStatus.NOT_FOUND);
	}

	@Test
	@Transactional
	void llamada_a_delete_escuelas_id_desactivada_devuelve_error_400() throws Exception {
		String id = this.crearEscuelas(mockMvc);

		CommonTestUtils.operacionDeleteExitosa(mockMvc, "/api/v1/escuelas/disable/" + id, HttpStatus.OK);

		CommonTestUtils.verificarErrorDeleteSinId(mockMvc, "/api/v1/escuelas/disable/" + id, 2, HttpStatus.BAD_REQUEST);
	}

	@Transactional
	@Test
	void llamada_a_delete_escuelas_id_activada_devuelve_200() throws Exception {
		String id = this.crearEscuelas(mockMvc);
		CommonTestUtils.operacionDeleteExitosa(mockMvc, "/api/v1/escuelas/disable/" + id, HttpStatus.OK);
	}

	// Delete test end

	// Put test start

	@Test
	@Transactional
	void llamada_a_put_update_escuelas_sin_id_devuelve_error_404() throws Exception {
		CommonTestUtils.verificarErrorPut(mockMvc, "{ \"nombre\": \"test2 temporal\" }" , "/api/v1/escuelas/update/", 6, HttpStatus.NOT_FOUND);
	}

	@Test
	@Transactional
	void llamada_a_put_update_escuelas_sin_contenido_devuelve_400() throws Exception {
		CommonTestUtils.verificarErrorPut(mockMvc, "" , "/api/v1/escuelas/update/99999", 1, HttpStatus.BAD_REQUEST);
	}

	@Test
	@Transactional
	void llamada_a_put_update_escuelas_id_erroneo_devuelve_error_404() throws Exception {
		CommonTestUtils.verificarErrorPut(mockMvc, "{ \"nombre\": \"test2 temporal\" }" , "/api/v1/escuelas/update/error", 5, HttpStatus.BAD_REQUEST);
	}

	@Test
	@Transactional
	void llamada_a_put_update_escuelas_id_no_existe_devuelve_error_404() throws Exception {
		CommonTestUtils.verificarErrorPut(mockMvc, "{ \"nombre\": \"test2 temporal\" }" , "/api/v1/escuelas/update/9999999", 3, HttpStatus.NOT_FOUND);
	}

	@Test
	@Transactional
	void llamada_a_put_update_escuelas_id_desactivada_devuelve_error_400() throws Exception {
		String id = this.crearEscuelas(mockMvc);

		CommonTestUtils.operacionDeleteExitosa(mockMvc, "/api/v1/escuelas/disable/" + id, HttpStatus.OK);

		CommonTestUtils.verificarErrorPut(mockMvc, "{ \"nombre\": \"test2 temporal\" }" , "/api/v1/escuelas/update/" + id, 2, HttpStatus.BAD_REQUEST);
	}

	@Test
	@Transactional
	void llamada_a_put_update_escuelas_id_activada_devuelve_200() throws Exception {
		String id = this.crearEscuelas(mockMvc);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/escuelas/update/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"nombre\": \"test2 temporal\" }"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("test2 temporal"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
	}

	@Test
	@Transactional
	void llamada_a_put_enable_escuelas_id_enable_devuelve_200() throws Exception {
		String id = this.crearEscuelas(mockMvc);

		CommonTestUtils.operacionDeleteExitosa(mockMvc, "/api/v1/escuelas/disable/" + id, HttpStatus.OK);

		CommonTestUtils.verificarErrorPut(mockMvc, "{}" , "/api/v1/escuelas/enable/" + id, 0, HttpStatus.OK);
	}

	@Test
	@Transactional
	void llamada_a_put_enable_escuelas_sin_id_devuelve_error_404() throws Exception {
		CommonTestUtils.verificarErrorPut(mockMvc, "{}" , "/api/v1/escuelas/enable/", 6, HttpStatus.NOT_FOUND);
	}

	@Test
	@Transactional
	void llamada_a_put_enable_escuelas_id_erroneo_devuelve_error_404() throws Exception {
		CommonTestUtils.verificarErrorPut(mockMvc, "{}" , "/api/v1/escuelas/enable/error", 5, HttpStatus.BAD_REQUEST);
	}

	@Test
	@Transactional
	void llamada_a_put_enable_escuelas_id_no_existe_devuelve_error_404() throws Exception {
		CommonTestUtils.verificarErrorPut(mockMvc, "{}" , "/api/v1/escuelas/enable/9999999", 3, HttpStatus.NOT_FOUND);
	}

	@Test
	@Transactional
	void llamada_a_put_enable_escuelas_id_enable_devuelve_error_400() throws Exception {
		String id = this.crearEscuelas(mockMvc);

		CommonTestUtils.verificarErrorPut(mockMvc, "{}" , "/api/v1/escuelas/enable/" + id, 2, HttpStatus.BAD_REQUEST);
	}
	// Put test end


	private String crearEscuelas( MockMvc mockMvc) throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/escuelas/save").contentType(MediaType.APPLICATION_JSON)
		.content("{ \"nombre\": \"test\" }")).andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("test"))
		.andReturn();

		String content = result.getResponse().getContentAsString();

		EscuelaResponseDto escuelaResponseDto = objectMapper.readValue(content, EscuelaResponseDto.class);
		
		return escuelaResponseDto.id().toString();
	}

}
