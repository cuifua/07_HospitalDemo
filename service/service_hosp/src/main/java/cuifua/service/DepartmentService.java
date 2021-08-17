package cuifua.service;


import cuifua.model.hosp.Department;
import cuifua.vo.hosp.DepartmentQueryVo;
import cuifua.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Map;

/**
 * @Author cuifua
 * @Date 2021/7/27 21:15
 * @Version 1.0
 */
public interface DepartmentService
{
    //上传科室接口
    void save(Map<String, Object> paramMap);

    //查询科室接口
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    //删除科室
    void remove(String hoscode, String depcode);

    //根据医院编号，查询医院所有科室列表
    List<DepartmentVo> findDeptTree(String hoscode);

    //根据科室编号得到科室名称
    String getDepName(String hoscode, String depcode);

    //科室
    Department getDepartment(String hoscode, String depcode);
}
