package com.example.backend.service.arrangement;

import com.example.backend.entity.arrangement.ShortArrangement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShortArrangementService {
    void createShortArrangement(ShortArrangement newShortArrangement);

    List<ShortArrangement> getAllShortArrangements();
}
