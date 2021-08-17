package cuifua.controller;

import cuifua.model.cmn.Dict;
import cuifua.result.Result;
import cuifua.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author cuifua
 * @Date 2021/7/25 10:51
 * @Version 1.0
 */
@Api(tags = "数据字典的接口")
@RestController
@RequestMapping("/admin/cmn/dict")
//@CrossOrigin
public class DictController
{
    @Autowired
    private DictService dictService;

    //1、根据数据id查询子数据
    @ApiOperation(value = "根据数据id查询子数据")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id)
    {
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }

    //2、导出数据字典的接口
    @ApiOperation(value = "导出数据字典的接口")
    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response)
    {
        dictService.exportDictData(response);
    }

    //3、导入数据字典的接口
    @ApiOperation(value = "导入数据字典的接口")
    @PostMapping("importData")
    public Result importDict(MultipartFile file)
    {
        dictService.importDictData(file);
        return Result.ok();
    }

    //根据dictcode和value查询
    @ApiOperation(value = "根据dictcode和value查询")
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value)
    {
        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }

    //根据value查询
    @ApiOperation(value = "根据value查询")
    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value)
    {
        String dictName = dictService.getDictName("",value);
        return dictName;
    }

    //这个方法是，二级联动，查省份
    //根据dictCode获取下级节点
    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode)
    {
        List<Dict> list = dictService.findByDictCode(dictCode);//返回list，因为有很多省
        return Result.ok(list);
    }

}
