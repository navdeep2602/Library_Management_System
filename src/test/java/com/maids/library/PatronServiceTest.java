package com.maids.library;

import com.maids.library.DAO.PatronRepository;
import com.maids.library.entity.Patron;
import com.maids.library.exception.PatronAlreadyExistsException;
import com.maids.library.exception.PatronNotFoundException;
import com.maids.library.service.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatronServiceTest {
    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll_ShouldReturnAllPatrons() {
        Patron patron1 = new Patron("Patron One", "contact1@example.com", null);
        Patron patron2 = new Patron("Patron Two", "contact2@example.com", null);
        when(patronRepository.findAll()).thenReturn(Arrays.asList(patron1, patron2));

        List<Patron> result = patronService.findAll();

        assertEquals(2, result.size());
        verify(patronRepository, times(1)).findAll();
    }

    @Test
    public void findById_ExistingId_ShouldReturnPatron() {
        Patron patron = new Patron("Patron One", "contact1@example.com", null);
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        Patron result = patronService.findById(1L);

        assertNotNull(result);
        assertEquals("Patron One", result.getName());
    }

    @Test
    public void findById_NonExistingId_ShouldThrowException() {
        when(patronRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PatronNotFoundException.class, () -> {
            patronService.findById(99L);
        });

        assertTrue(exception.getMessage().contains("Patron with ID 99 not found"));
    }

    @Test
    public void save_NewPatron_ShouldReturnSavedPatron() {
        Patron newPatron = new Patron("vitta", "vitta@example.com", null);
        when(patronRepository.findByContactInformation("vitta@example.com")).thenReturn(null);
        when(patronRepository.save(any(Patron.class))).thenReturn(newPatron);

        Patron result = patronService.save(newPatron);

        assertNotNull(result);
    }

    @Test
    public void save_ExistingPatron_ShouldThrowException() {
        Patron existingPatron = new Patron("Existing Patron", "existing@example.com", null);
        when(patronRepository.findByContactInformation("existing@example.com")).thenReturn(existingPatron);

        Exception exception = assertThrows(PatronAlreadyExistsException.class, () -> {
            patronService.save(existingPatron);
        });

        assertTrue(exception.getMessage().contains("A patron with the email existing@example.com already exists."));
    }

    @Test
    public void deleteById_ExistingId_ShouldDeletePatron() {
        when(patronRepository.existsById(1L)).thenReturn(true);
        doNothing().when(patronRepository).deleteById(1L);

        patronService.deleteById(1L);

        verify(patronRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteById_NonExistingId_ShouldThrowException() {
        when(patronRepository.existsById(99L)).thenReturn(false);

        Exception exception = assertThrows(PatronNotFoundException.class, () -> {
            patronService.deleteById(99L);
        });

        assertTrue(exception.getMessage().contains("Patron with ID 99 not found"));
    }
}