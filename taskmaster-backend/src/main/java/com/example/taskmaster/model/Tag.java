package com.example.taskmaster.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Table(name = "tags")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(nullable = false, unique = true, length = 21)
    private String uid;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(7) default '#3B82F6'")
    private String color = "#3B82F6";

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.uid == null || this.uid.isEmpty()) {
            this.uid = NanoIdUtils.randomNanoId(new Random(), NanoIdUtils.DEFAULT_ALPHABET, 21);
        }
    }
}