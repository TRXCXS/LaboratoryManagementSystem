package com.example.backend.service.arrangement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.arrangement.ShortArrangement;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.exception.arrangement.shortArrangementException.ShortArrangementHasExistedException;
import com.example.backend.exception.model.laboratoryException.LaboratoryHasBeenArrangedException;
import com.example.backend.exception.model.laboratoryException.LaboratoryNotExistedException;
import com.example.backend.mapper.arrangement.ShortArrangementMapper;
import com.example.backend.mapper.model.LaboratoryMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortArrangementServiceImpl implements ShortArrangementService {
    private final ShortArrangementMapper shortArrangementMapper;
    private final LaboratoryMapper laboratoryMapper;

    @Override
    public void createShortArrangement(@NotNull ShortArrangement newShortArrangement) {
        QueryWrapper<Laboratory> labQueryWrapper = new QueryWrapper<>();
        labQueryWrapper.eq("labID", newShortArrangement.getLabID());
        if (!laboratoryMapper.exists(labQueryWrapper)) {
            throw new LaboratoryNotExistedException("目标实验室不存在，无法创建安排！");
        }

        QueryWrapper<ShortArrangement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentRequestID", newShortArrangement.getStudentRequestID());
        if (shortArrangementMapper.exists(queryWrapper)) {
            throw new ShortArrangementHasExistedException("该申请的安排已经存在，无法再次创建！");
        }

        queryWrapper.clear();
        queryWrapper.eq("week", newShortArrangement.getWeek());
        queryWrapper.eq("weekday", newShortArrangement.getWeekday());
        queryWrapper.eq("slot", newShortArrangement.getSlot());
        queryWrapper.eq("labID", newShortArrangement.getLabID());
        if (shortArrangementMapper.exists(queryWrapper)) {
            throw new LaboratoryHasBeenArrangedException("该实验室在该时间段已经被借用，因此无法创建安排！");
        }

        shortArrangementMapper.insert(newShortArrangement);
    }
}
