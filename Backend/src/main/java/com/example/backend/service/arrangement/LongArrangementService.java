package com.example.backend.service.arrangement;

import com.example.backend.entity.arrangement.LongArrangement;
import org.springframework.stereotype.Service;

@Service
public interface LongArrangementService {
    void createLongArrangement(LongArrangement newLongArrangement);
}
