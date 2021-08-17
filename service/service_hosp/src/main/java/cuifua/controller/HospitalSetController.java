package cuifua.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cuifua.exception.YyghException;
import cuifua.result.Result;
import cuifua.model.hosp.HospitalSet;
import cuifua.service.HospitalSetService;
import cuifua.utils.MD5;
import cuifua.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @Author cuifua
 * @Date 2021/7/23 14:44
 * @Version 1.0
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
//@CrossOrigin
public class HospitalSetController
{
    //注入Service
    @Autowired
    private HospitalSetService hospitalSetService;

    //1、查询医院设置表所有信息
    @ApiOperation(value = "获取所有的医院设置信息")
    @GetMapping("findAll")
    public Result findAllHospSet()
    {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //2、逻辑删除医院设置
    @ApiOperation(value = "逻辑删除医院设置信息")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable Long id)
    {
        boolean flag = hospitalSetService.removeById(id);
        if(flag)
            return Result.ok();
        else
            return Result.fail();
    }

    //3、条件查询带分页
    @ApiOperation(value = "查询条件带分页")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPage(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo)
                                        //required = false表示hospitalSetQueryVo可以没用
    {
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current,limit);

        //构建查询条件(MP的wapper里的模糊查询等方法)
        QueryWrapper<HospitalSet> wapper = new QueryWrapper<>();

        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();

        if(!ObjectUtils.isEmpty(hosname))
            wapper.like("hosname", hospitalSetQueryVo.getHosname());//医院名称模糊查询
        if(!ObjectUtils.isEmpty(hoscode))
            wapper.eq("hoscode", hospitalSetQueryVo.getHoscode());//医院编号精准查询


        //调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wapper);

        return Result.ok(pageHospitalSet);
    }

    //4、添加医院设置
    @ApiOperation(value = "添加医院设置")
    @PostMapping("saveHospSet")
    public Result saveHospSet(@RequestBody HospitalSet hospitalSet)
    {
        //设置状态 1可用  0不可用
        hospitalSet.setStatus(1);
        //签名密钥
        Random random = new Random();
        hospitalSet.setSignKey( MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));

        boolean save = hospitalSetService.save(hospitalSet);

        return save ? Result.ok() : Result.fail();
    }

    //5、根据id获取医院设置
    @ApiOperation(value = "根据id获取医院设置")
    @GetMapping("getHospSetById/{id}")
    public Result getHospSetById(@PathVariable Long id)
    {
        /**里做一个抛出异常的测试，和下面代码无瓜
        try
        {
            int a = 1/0;
        }
        catch (Exception e)
        {
            throw new YyghException("失败",201);
        }
         */

        HospitalSet getbyId = hospitalSetService.getById(id);
        return Result.ok(getbyId);
    }

    //6、修改医院设置
    @ApiOperation(value = "修改医院设置")
    @PostMapping("updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet)
    {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        return flag ? Result.ok() : Result.fail();
    }

    //7、批量删除医院设置
    @ApiOperation(value = "批量删除医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospSet(@RequestBody List<Long> idLists)
    {
        boolean batchSet = hospitalSetService.removeByIds(idLists);
        return batchSet ? Result.ok() : Result.fail();
    }

    //8、医院设置锁定和解锁(修改提交，所以put方法)
    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("lockHospSet/{id}/{status}")
    public Result lockHospSet(@PathVariable Long id, @PathVariable Integer status)
    {
        //根据id查询医院设置信息
        HospitalSet hospSetbyId = hospitalSetService.getById(id);
        //设置状态
        hospSetbyId.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospSetbyId);

        return Result.ok();
    }

    //9、发送签名密钥
    @ApiOperation(value = "发送签名密钥")
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id)
    {
        HospitalSet sendByid = hospitalSetService.getById(id);
        String signKey = sendByid.getSignKey();
        String hoscode = sendByid.getHoscode();

        //TODO 发送短信
        return Result.ok();
    }
}
