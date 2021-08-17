package cuifua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cuifua.model.hosp.HospitalSet;
import cuifua.vo.order.SignInfoVo;

/**
 * @Author cuifua
 * @Date 2021/7/23 14:39
 * @Version 1.0
 */
public interface HospitalSetService extends IService<HospitalSet>
{
    //2、根据医院系统传过来的对象里的编码属性，查询数据库，进而查询数据库里的签名
    String getSignKey(String hoscode);

    //下单的时候，调用医院接口，所以要获取医院签名
    SignInfoVo getSignInfoVo(String hoscode);
}
