package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceTest {
    @Test
    public void checkBloodPressure_WhenBloodPressureIsNotOk() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.any()))
                .thenReturn(getPatientInfo());

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure(Mockito.any(), new BloodPressure(130, 90));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService).send(captor.capture());

        Assertions.assertEquals("Warning, patient with id: 1, need help", captor.getValue());
    }

    private PatientInfo getPatientInfo() {
        return new PatientInfo(
                "1",
                "Vasya",
                "Vasin",
                LocalDate.of(1982, 1, 16),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78))
        );
    }

    @Test
    public void checkBloodPressure_WhenBloodPressureIsOk() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.any()))
                .thenReturn(getPatientInfo());

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure(Mockito.any(), new BloodPressure(125, 78));

        Mockito.verify(sendAlertService, Mockito.never()).send(Mockito.any());
    }

    @Test
    public void checkTemperature_WhenTemperatureIsNotOk() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.any()))
                .thenReturn(getPatientInfo());

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature(Mockito.any(), new BigDecimal("34.6"));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService).send(captor.capture());

        Assertions.assertEquals("Warning, patient with id: 1, need help", captor.getValue());
    }

    @Test
    public void checkTemperature_WhenTemperatureIsOk() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.any()))
                .thenReturn(getPatientInfo());

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature(Mockito.any(), new BigDecimal("35.6"));

        Mockito.verify(sendAlertService, Mockito.never()).send(Mockito.any());
    }
}