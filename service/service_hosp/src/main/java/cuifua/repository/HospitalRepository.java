package cuifua.repository;

import cuifua.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author cuifua
 * @Date 2021/7/26 13:41
 * @Version 1.0
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String>
{
    //判断是否存在数据
    //这里不用实现方法，因为mongdb按照命名规则可以帮你完善出这个方法
    Hospital getHospitalByHoscode(String hoscode);

    //根据医院名称模糊查询医院对象
    List<Hospital> findHospitalByHosnameLike(String hosname);
}
