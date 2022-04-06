package com.example.AppointmentService.Appointment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Table
@Entity
@Data
@NoArgsConstructor
public class AppointmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long confirmationId;

    private Date date;
    private Time time;
    private int userId;
    private int centerId;
    private String notifyType;
    private String notifyString;
    private String cancelId;
    private String status;
}
