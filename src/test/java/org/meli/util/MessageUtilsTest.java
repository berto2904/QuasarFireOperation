package org.meli.util;

import org.junit.jupiter.api.Test;
import org.meli.dto.SatelliteDataDTO;
import org.meli.service.MessageService;
import org.meli.service.impl.MessageServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageUtilsTest {

    private final MessageService service = new MessageServiceImpl();

    @Test
    void shouldReturnCorrectSentenceIfHasDuplicatePrevMessage() {
        String[][] messages = {
            {"", "este", "es", "", "mensaje"},
            {"este", "", "es", "un", ""},
            {"", "", "", "un", "mensaje"}
        };

        String result = service.getMessage(messages);

        assertNotNull(result);
        assertEquals("este es un mensaje", result);
    }

    @Test
    void shouldReturnNullIfConflict() {
        String[][] messages = {
            {"", "hello"},
            {"hello", ""},
            {"hi", ""}
        };

        String result = service.getMessage(messages);

        assertNull(result);
    }
}