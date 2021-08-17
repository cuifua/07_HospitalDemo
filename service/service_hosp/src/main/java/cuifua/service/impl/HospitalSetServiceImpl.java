package cuifua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cuifua.exception.YyghException;
import cuifua.mapper.HospitalSetMapper;
import cuifua.model.hosp.HospitalSet;
import cuifua.result.ResultCodeEnum;
import cuifua.service.HospitalSetService;
import cuifua.vo.order.SignInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author cuifua
 * @Date 2021/7/23 14:41
 * @Version 1.0
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService
{
    //2、根据医院系统传过来的对象里的编码属性，查询数据库，进而查询数据库里的签名
    @Override
    public String getSignKey(String hoscode)
    {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);

        return hospitalSet.getSignKey();
    }

    //下单的时候，调用医院接口，所以要获取医院签名
    @Override
    public SignInfoVo getSignInfoVo(String hoscode)

    {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        if(null == hospitalSet)
            throw new YyghException(ResultCodeEnum.HOSPITAL_OPEN);

        SignInfoVo signInfoVo = new SignInfoVo();
        signInfoVo.setApiUrl(hospitalSet.getApiUrl());
        signInfoVo.setSignKey(hospitalSet.getSignKey());
        return signInfoVo;
    }
}
