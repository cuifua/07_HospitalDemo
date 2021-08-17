package cuifua.repository;
import cuifua.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author cuifua
 * @Date 2021/7/27 21:08
 * @Version 1.0
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department,String>
{
    //上传科室信息
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
