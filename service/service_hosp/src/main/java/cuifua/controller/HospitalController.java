package cuifua.controller;

import cuifua.model.hosp.Hospital;
import cuifua.result.Result;
import cuifua.service.HospitalServise;
import cuifua.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author cuifua
 * @Date 2021/7/28 13:22
 * @Version 1.0
 */
@Api(tags = "医院管理")
@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class HospitalController
{
    @Autowired
    private HospitalServise hospitalServise;

    //医院列表(条件查询分页)
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           HospitalQueryVo hospitalQueryVo)
    {
        Page<Hospital> pageModel = hospitalServise.selectHospPage(page,limit,hospitalQueryVo);

        List<Hospital> content = pageModel.getContent();
        long totalElements = pageModel.getTotalElements();

        return Result.ok(pageModel);
    }

    //更新医院上线状态
    @ApiOperation(value = "更新医院上线状态")
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id,@PathVariable Integer status)
    {
        hospitalServise.updateStatus(id,status);
        return Result.ok();
    }

    //医院详情信息
    @ApiOperation(value = "医院详情信息")
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id)
    {
        Map<String, Object> map = hospitalServise.getHospById(id);
        return Result.ok(map);
    }

}
