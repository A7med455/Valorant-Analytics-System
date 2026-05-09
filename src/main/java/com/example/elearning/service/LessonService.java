package com.example.elearning.service;

import com.example.elearning.model.Course;
import com.example.elearning.model.Lesson;
import com.example.elearning.repository.LessonRepository;
import com.example.elearning.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class LessonService
{
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonService(LessonRepository lessonRepository,CourseRepository courseRepository)
    {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    public void addLessonByUrl(Long courseId,String title,String videoUrl)
    {
        Course course=courseRepository.findById(courseId).orElse(null);
        if(course==null)
        {
            throw new IllegalArgumentException("Course not found");
        }

        if(title==null || title.trim().isEmpty())
        {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if(videoUrl==null || videoUrl.trim().isEmpty())
        {
            throw new IllegalArgumentException("Video URL cannot be empty");
        }

        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setVideoUrl(videoUrl);
        lesson.setCourse(course);

        lessonRepository.save(lesson);
    }

    public void addLessonByFile(Long courseId, String title, MultipartFile lessonFile)
    {
        //multiPart was used bec the browser send files on parts
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course==null)
        {
            throw new IllegalArgumentException("Course not found");
        }
        if(title==null || title.trim().isEmpty())
        {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if(lessonFile==null || lessonFile.isEmpty())
        {
            throw new IllegalArgumentException("Video file cannot be empty");
        }

        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        //store its original name to easily retrieve it later
        lesson.setVideoName(lessonFile.getOriginalFilename());
        //tell the browser how to play it , Ex:video/mp4 or video/webm
        String contentType = lessonFile.getContentType();
        if (contentType == null || !contentType.startsWith("video/")) {
            throw new IllegalArgumentException("File must be a video (mp4, webm, etc.)");
        }
        try
        {
            //set the actual file/video as array of bytes
            lesson.setVideoData(lessonFile.getBytes());
        }catch (IOException e)
        {
            throw new IllegalArgumentException("Video file not found");
        }
        lesson.setCourse(course);
        lessonRepository.save(lesson);
    }

    public List<Lesson> getLessonsByCourse(Long courseId)
    {
        if(courseId==null)
        {
            throw new IllegalArgumentException("Course ID cannot be null");
        }
        return lessonRepository.findByCourse_Id(courseId);
    }


}
