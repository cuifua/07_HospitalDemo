package cuifua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cuifua.model.user.Patient;
import cuifua.model.user.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author cuifua
 * @Date 2021/8/3 14:09
 * @Version 1.0
 */
@Repository
public interface PatientService extends IService<Patient>
{
    //获取就诊人列表
    List<Patient> findAllUserId(Long userId);

    //根据id获取就诊人信息
    Patient getPatientId(Long id);
}
