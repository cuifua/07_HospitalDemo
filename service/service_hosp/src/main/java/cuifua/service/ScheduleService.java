package cuifua.service;

import cuifua.model.hosp.Schedule;
import cuifua.vo.hosp.ScheduleOrderVo;
import cuifua.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author cuifua
 * @Date 2021/7/27 22:41
 * @Version 1.0
 */
@Repository
public interface ScheduleService
{
    //上传排班接口
    void save(Map<String, Object> paramMap);

    //查询排班接口
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    //删除排班接口
    void remove(String hoscode, String hosScheduleId);

    //根据医院编号 和 科室编号 ，查询排班规则数据
    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    //根据医院编号 、科室编号和工作日期，查询排班详细信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    //获取可预约排班数据
    Map<String,Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    //获取排班id获取排班数据
    Schedule getScheduleId(String scheduleId);

    //根据排班id获取预约下单数据【生成订单里需要用户信息以及排班信息，这里是获取排班部分的接口】
    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    //订单完成后修改排班 用于mq的操作
    void update(Schedule schedule);
}
