package com.example.backend.service.model;

import com.example.backend.entity.model.Laboratory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LaboratoryService {

    List<Laboratory> getLabsByType(String labType);

    List<Laboratory> getLabsByCapacity(Integer studentCount);
}
