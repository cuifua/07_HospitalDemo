package cuifua.service;


import com.baomidou.mybatisplus.extension.service.IService;
import cuifua.model.order.PaymentInfo;
import cuifua.model.order.RefundInfo;

public interface RefundInfoService extends IService<RefundInfo>
 {
    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);
}
