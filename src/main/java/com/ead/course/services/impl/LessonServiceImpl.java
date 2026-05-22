package com.ead.course.services.impl;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.exceptions.NotFoundException;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    final LessonRepository lessonRepository;


    public LessonServiceImpl(LessonRepository lessonRepository) {

        this.lessonRepository = lessonRepository;

    }

    @Override
    public LessonModel save(LessonRecordDto lessonRecordDto, ModuleModel moduleModel) {
        var lesson = new LessonModel();
        BeanUtils.copyProperties(lessonRecordDto, lesson);
        lesson.setModule(moduleModel);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return lessonRepository.save(lesson);
    }

    @Override
    public List<LessonModel> findAllLessonsIntoModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        Optional<LessonModel> lessonModel = lessonRepository.findLessonIntoModule(moduleId, lessonId);
        if (lessonModel.isEmpty()){
            throw new NotFoundException("Error: Lesson not found for this module!");
        }
        return lessonModel;
    }

    @Override
    public void delete(LessonModel lessonModel) {
        lessonRepository.delete(lessonModel);
    }

    @Override
    public LessonModel update(LessonRecordDto lessonRecordDto, LessonModel lessonModel) {
        BeanUtils.copyProperties(lessonRecordDto, lessonModel);
        return lessonRepository.save(lessonModel);
    }

    @Override
    public Page<LessonModel> findAllLessonsIntoModule(Specification<LessonModel> specification, Pageable pageable) {
        return lessonRepository.findAll(specification, pageable);
    }
}
