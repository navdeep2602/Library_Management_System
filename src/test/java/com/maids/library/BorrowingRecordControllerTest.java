package com.maids.library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.library.DTO.BorrowRequest;
import com.maids.library.DTO.ReturnRequest;
import com.maids.library.controller.BorrowingRecordController;
import com.maids.library.service.BorrowingRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BorrowingRecordController.class)
public class BorrowingRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser
    @Test
    public void borrowBook_ShouldReturnOk() throws Exception {
        BorrowRequest borrowRequest = new BorrowRequest(1L, 1L, new Date());
        given(borrowingRecordService.borrowBook(any(Long.class), any(Long.class), any(Date.class)))
                .willReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/api/borrow")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void returnBook_ShouldReturnOk() throws Exception {
        ReturnRequest returnRequest = new ReturnRequest(1L, 1L, new Date());
        given(borrowingRecordService.returnBook(any(Long.class), any(Long.class), any(Date.class)))
                .willReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/api/return")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnRequest)))
                .andExpect(status().isOk());
    }

}
