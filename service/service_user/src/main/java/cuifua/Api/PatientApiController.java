package cuifua.Api;

import cuifua.service.PatientService;
import cuifua.result.Result;
import cuifua.utils.AuthContextHolder;
import cuifua.model.user.Patient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

//就诊人管理接口
@Api(tags = "就诊人管理接口")
@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController
{
    @Autowired
    private PatientService patientService;

    //获取就诊人列表
    @GetMapping("auth/findAll")
    public Result findAll(HttpServletRequest request)
    {
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        List<Patient> list = patientService.findAllUserId(userId);
        return Result.ok(list);
    }
    //根据id获取就诊人信息
    @GetMapping("auth/get/{id}")
    public Result getPatient(@PathVariable Long id)
    {
        Patient patient = patientService.getPatientId(id);
        return Result.ok(patient);
    }

    //添加就诊人
    @PostMapping("auth/save")
    public Result savePatient(@RequestBody Patient patient, HttpServletRequest request)
    {
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        patient.setUserId(userId);
        patientService.save(patient);
        return Result.ok();
    }

    //修改就诊人
    @PostMapping("auth/update")
    public Result updatePatient(@RequestBody Patient patient)
    {
        patientService.updateById(patient);
        return Result.ok();
    }

    //删除就诊人
    @DeleteMapping("auth/remove/{id}")
    public Result removePatient(@PathVariable Long id)
    {
        patientService.removeById(id);
        return Result.ok();
    }


    //根据就诊人id获取就诊人信息【order订单模块里，需要下单者的身份信息，所以此方法写在service-user模块里】
    @ApiOperation(value = "获取就诊人")
    @GetMapping("inner/get/{id}")
    public Patient getPatientOrder(@PathVariable Long id)
    {
        Patient patient = patientService.getPatientId(id);
        return patient;
    }

}
