package com.example.backend.service.arrangement;

import com.example.backend.entity.arrangement.ShortArrangement;
import org.springframework.stereotype.Service;

@Service
public interface ShortArrangementService {
    void createShortArrangement(ShortArrangement newShortArrangement);
}
