package com.example.elearning.model;

import jakarta.persistence.*;

@Entity
@Table(name="lesson")
public class Lesson {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private String title;
   private String videoUrl; //if admin pastes url lel content//

   //admin uploads vid,store as binary in db @lob tells JPA en dy large obj//
    @Lob
    @Column(name="video_data", columnDefinition = "LONGBLOB")
    private byte[] videoData;
    private String videoName;
    private String videoType;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    //setters w getters//
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
public String getTitle(){
        return title;
}
public void setTitle(String title){
        this.title=title;
}
public String getVideoUrl(){
        return videoUrl;
}
public void setVideoUrl(String videoUrl){
        this.videoUrl=videoUrl;
}
public byte[] getVideoData(){
        return videoData;
}
public void setVideoData(byte[] videoData){
        this.videoData=videoData;
}
public String getVideoName(){
        return videoName;
}
public void setVideoName(String videoName){
        this.videoName=videoName;
}
public String getVideoType(){
        return videoType;
}
public void setVideoType(String videoType){
        this.videoType=videoType;
    }
    public Course getCourse(){
        return course;
    }
    public void setCourse(Course course){
        this.course=course;
    }

}