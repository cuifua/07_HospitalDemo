package cuifua.controller;

import cuifua.result.Result;
import cuifua.service.DepartmentService;
import cuifua.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author cuifua
 * @Date 2021/7/29 10:21
 * @Version 1.0
 */
@Api(tags = "医院科室管理")
@RestController
@RequestMapping("/admin/hosp/department")
//@CrossOrigin
public class DepartmentController
{
    @Autowired
    private DepartmentService departmentService;

    //根据医院编号，查询医院所有科室列表
    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode)
    {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }
}
