package com.example.backend.service.arrangement;

import com.example.backend.controller.responsebody.LongArrangementResponseDataForDisplay;
import com.example.backend.entity.arrangement.LongArrangement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LongArrangementService {
    void createLongArrangement(LongArrangement newLongArrangement);

    List<LongArrangementResponseDataForDisplay> getTableData();
}
