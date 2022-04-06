package com.example.AppointmentService.Appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
}
