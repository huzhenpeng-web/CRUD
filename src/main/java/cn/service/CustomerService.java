package cn.service;

import cn.domain.Customer;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
//    List<Customer> findAllCustomer();

    //分页查询
//    PageInfo<Customer> findCustomerByPage(int pageNum, int size);

    Customer findCustomer(int id);

    int deleteCustomerById(int id);

    int insertCustomer(Customer customer);

    int updateCustomer(Customer customer);

    //根据姓名模糊搜索顾客
    PageInfo<Customer> findCustomerByName(int pageNum, int pageSize, String customerName);
}
