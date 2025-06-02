package org.meli.service.impl;

import org.meli.dto.SatelliteDataDTO;
import org.meli.service.MessageService;
import org.meli.util.MessageUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public String getMessage(String[][] messages) {
        return MessageUtils.reconstructMessage(messages);
    }
}
