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

    @Test
    void testMessageWithGapsAndOffsets() {
        String[][] messages = {
                {"este", "", "", "mensaje", ""},
                {"", "es", "", "", "secreto"},
                {"este", "", "un", "", ""}
        };

        MessageServiceImpl service = new MessageServiceImpl();
        String result = service.getMessage(messages);

        assertEquals("este es un mensaje secreto", result);
    }

    @Test
    void testMessageWithOffsets() {
        String[][] messages = {
                {"", "este", "es", "un", "mensaje"},
                {"este", "", "un", "mensaje"},
                {"", "", "es", "", "mensaje"}
        };

        MessageServiceImpl service = new MessageServiceImpl();
        String result = service.getMessage(messages);

        assertEquals("este es un mensaje", result);
    }

    @Test
    void testMessageWithOffsets2() {
        String[][] messages = {
                {"este","","","mensaje"},
                {"este", "", "un", "mensaje"},
                {"", "", "es", "", "mensaje"}
        };

        MessageServiceImpl service = new MessageServiceImpl();
        String result = service.getMessage(messages);

        assertEquals("este es un mensaje", result);
    }
}