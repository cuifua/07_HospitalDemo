package cuifua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cuifua.model.order.OrderInfo;
import cuifua.vo.order.OrderCountQueryVo;
import cuifua.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo>
{
    //查询预约统计数据的方法
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);//在xml里可以用别名@Param("vo")
}
