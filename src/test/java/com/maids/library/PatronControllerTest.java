package com.maids.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.library.controller.PatronController;
import com.maids.library.entity.Patron;
import com.maids.library.exception.GlobalExceptionHandler;
import com.maids.library.service.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;


import java.util.Arrays;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(GlobalExceptionHandler.class)
@WebMvcTest(PatronController.class)
public class PatronControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronServiceImpl patronService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatronController patronController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    public void getAllPatronsTest() throws Exception {
        Patron patron1 = new Patron(1L, "John Doe", "john@example.com", null);
        Patron patron2 = new Patron(2L, "Jane Doe", "jane@example.com", null);
        List<Patron> allPatrons = Arrays.asList(patron1, patron2);

        given(patronService.findAll()).willReturn(allPatrons);

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(patron1.getName())))
                .andExpect(jsonPath("$[1].name", is(patron2.getName())));
    }

    @Test
    public void getPatronByIdTest_whenPatronExists() throws Exception {
        Patron patron = new Patron(1L, "John Doe", "john@example.com", null);

        given(patronService.findById(1L)).willReturn(patron);

        mockMvc.perform(get("/api/patrons/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(patron.getName())))
                .andExpect(jsonPath("$.contactInformation", is(patron.getContactInformation())));
    }



    @Test
    public void createPatronTest() throws Exception {
        Patron patronToCreate = new Patron( 2L,"New Patron", "new@example.com", null);
        Patron savedPatron = new Patron(2L,"New Patron", "new@example.com", null);

        when(patronService.save(any(Patron.class))).thenReturn(savedPatron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(savedPatron.getId().intValue())))
                .andExpect(jsonPath("$.name", is(savedPatron.getName())));
    }

    @Test
    public void updatePatronTest_whenPatronExists() throws Exception {
        Patron existingPatron = new Patron(1L,"Existing Patron", "existing@example.com", null);
        Patron updatedPatron = new Patron(1L,"Updated Patron", "updated@example.com", null);

        when(patronService.findById(existingPatron.getId())).thenReturn(existingPatron);
        when(patronService.save(any(Patron.class))).thenReturn(updatedPatron);

        mockMvc.perform(put("/api/patrons/{id}", existingPatron.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedPatron.getId().intValue())))
                .andExpect(jsonPath("$.name", is(updatedPatron.getName())));
    }

    @Test
    public void deletePatronTest_whenPatronExists() throws Exception {
        Long patronIdToDelete = 1L;
        doNothing().when(patronService).deleteById(patronIdToDelete);

        mockMvc.perform(delete("/api/patrons/{id}", patronIdToDelete))
                .andExpect(status().isOk());
    }
}
