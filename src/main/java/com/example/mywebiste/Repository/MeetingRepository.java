package com.example.mywebiste.Repository;

import com.example.mywebiste.Model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Meeting> findByHostEmailOrAttendeeEmail(String hostEmail, String attendeeEmail);
}
