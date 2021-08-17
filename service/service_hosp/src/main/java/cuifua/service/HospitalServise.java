package cuifua.service;

import cuifua.model.hosp.Hospital;
import cuifua.vo.hosp.HospitalQueryVo;
import cuifua.vo.order.SignInfoVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author cuifua
 * @Date 2021/7/26 13:43
 * @Version 1.0
 */
@Repository
public interface HospitalServise
{
    //上传医院接口
    void save(Map<String, Object> paramMap);

    //实现根据医院编号查询
    Hospital getByHoscode(String hoscode);

    //医院列表（条件查询分页）
    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    //更新医院上线状态
    void updateStatus(String id, Integer status);

    //医院详情信息
    Map<String, Object> getHospById(String id);

    //根据医院编号获取医院名称
    String getHospName(String hoscode);

    //根据医院名称模糊查询医院对象
    List<Hospital> findByHosname(String hosname);

    //根据医院编号获取医院预约挂号详情
    Map<String, Object> item(String hoscode);

}
