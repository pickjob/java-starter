package framework.mybatis;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author pickjob@126.com
 * @time 2019-06-18
 */
public interface OrderMapper {
    List<Order> selectOrders();
}
