package cuifua;

import cuifua.model.user.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user")
@Repository
public interface PatientFeignClient
{
    //获取就诊人【order订单模块里，需要下单者的身份信息，所以需要service-user模块】
    //根据就诊人id获取就诊人信息
    @GetMapping("/api/user/patient/inner/get/{id}")
    Patient getPatientOrder(@PathVariable("id") Long id);


    //Patient getPatient(Long patientId);
}
