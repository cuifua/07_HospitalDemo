package cuifua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cuifua.model.order.OrderInfo;
import cuifua.vo.order.OrderCountQueryVo;
import cuifua.vo.order.OrderQueryVo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Repository
public interface OrderService extends IService<OrderInfo>
{
    //保存订单
    Long saveOrder(String scheduleId, Long patientId);

    //根据订单id查询订单详情
    OrderInfo getOrder(String orderId);

    //订单列表（条件查询带分页）【就是用户下单预约界面里，有关自己预约的所有订单的界面】
    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    /**
    //后台管理员界面的订单展示页面
    Map<String,Object> show(Long orderId);
     */

    //取消预约
    Boolean cancelOrder(Long orderId);

    //就诊通知
    public void patientTips();

    //预约统计
    Map<String,Object> getCountMap(OrderCountQueryVo orderCountQueryVo );
}
