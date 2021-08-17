package cuifua.Api;

import cuifua.exception.YyghException;
import cuifua.helper.HttpRequestHelper;
import cuifua.model.hosp.Department;
import cuifua.model.hosp.Hospital;
import cuifua.model.hosp.Schedule;
import cuifua.result.Result;
import cuifua.result.ResultCodeEnum;
import cuifua.service.DepartmentService;
import cuifua.service.HospitalServise;
import cuifua.service.HospitalSetService;
import cuifua.service.ScheduleService;
import cuifua.utils.MD5;
import cuifua.vo.hosp.DepartmentQueryVo;
import cuifua.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author cuifua
 * @Date 2021/7/26 13:46
 * @Version 1.0
 */
@Api(tags = "医院接口" )
@RestController
@RequestMapping("/api/hosp")
//@CrossOrigin
public class ApiController
{
    @Autowired
    private HospitalServise hospitalServise;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;

    //1.1、查询医院接口
    @ApiOperation(value = "查询医院接口")
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request)
    {
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String,Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取传递过来的对象里的医院编号属性
        String hoscode = (String)paramMap.get("hoscode");

        //1 获取医院系统传递过来的签名,签名进行MD5加密
        String hospSign = (String)paramMap.get("sign");

        //2 根据传递过来医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3 把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4 判断签名是否一致
        if(!hospSign.equals(signKeyMd5))
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        //调用service方法实现根据医院编号查询
        Hospital hospital = hospitalServise.getByHoscode(hoscode);
        return Result.ok(hospital);

    }
    //1.2、上传医院接口
    @ApiOperation(value = "上传医院接口")
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request)
    {
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String,Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //1、获取医院系统传过来的接口里的密钥对象进行对比(次签名已经被MD5加密)
        String hospSign =(String) paramMap.get("sign");

        //2、根据医院系统传过来的对象里的编码属性，查询数据库，进而查询数据库里的签名
        String hoscode=(String) paramMap.get("hoscode");
        String singKey = hospitalSetService.getSignKey(hoscode);

        //3、把数据库查出来的签名进行MD5加密
        String singKeyMD5 = MD5.encrypt(singKey);

        //4、判断签名是否一致
        if(!hospSign.equals(singKeyMD5))
        {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //5、将传输数据里的图片格式转回来
        String logoData = (String)paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData",logoData);
        //调用service方法
        hospitalServise.save(paramMap);
        return Result.ok();
    }



    //2.1、查询科室
    @ApiOperation(value = "查询科室接口")
    @PostMapping("department/list")
    public Result findDdepartment(HttpServletRequest request)
    {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");

        //当前页 和 每页记录数
        int page = ObjectUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit =ObjectUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //TODO 签名校验

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        //调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);
        return Result.ok(pageModel);
    }
    //2.2、上传科室
    @ApiOperation(value = "上传科室接口")
    @PostMapping("saveDepartment")
    public Result savaDepartment(HttpServletRequest request)
    {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号
        String hoscode = (String)paramMap.get("hoscode");

        //1 获取医院系统传递过来的签名,签名进行MD5加密
        String hospSign = (String)paramMap.get("sign");

        //2 根据传递过来医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3 把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4 判断签名是否一致
        if(!hospSign.equals(signKeyMd5))
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        //调用service的方法
        departmentService.save(paramMap);
        return Result.ok();
    }
    //2.3、删除科室
    @ApiOperation(value = "删除科室接口")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request)
    {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //医院编号 和 科室编号
        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");
        //TODO 签名校验
        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }



    //3.1、查询排班接口
    @PostMapping("schedule/list")
    @ApiOperation(value = "查询排班接口")
    public Result findSchedule(HttpServletRequest request)
    {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");

        //科室编号
        String depcode = (String)paramMap.get("depcode");

        //当前页 和 每页记录数
        int page = ObjectUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = ObjectUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //TODO 签名校验

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);

        //调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }
    //3.2、上传排班接口
    @ApiOperation(value = "上传排班接口")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request)
    {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //TODO 签名校验
        scheduleService.save(paramMap);
        return Result.ok();
    }
    //3.3、删除排班接口
    @ApiOperation(value = "删除排班接口")
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request)
    {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号和排班编号
        String hoscode = (String)paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        //TODO 签名校验

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }
}
