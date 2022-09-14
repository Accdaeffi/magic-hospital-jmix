package ru.itmo.mpi.hospital.testsupport.testdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestData {

    @Autowired
    PrayerTestData prayerTestData;
}
