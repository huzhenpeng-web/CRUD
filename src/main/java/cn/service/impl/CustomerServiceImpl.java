package cn.service.impl;

import cn.domain.Customer;
import cn.mapper.CustomerMapper;
import cn.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

/*    @Override
    public List<Customer> findAllCustomer() {
        return customerMapper.findAllCustomer();
    }*/

/*    @Override
    public PageInfo<Customer> findCustomerByPage(int pageNum, int size) {
        PageHelper.startPage(pageNum, size);
        //查询所有用户
        List<Customer> customerList = customerMapper.findAllCustomer();
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customerList);
        return  customerPageInfo;
    }*/

    @Override
    public Customer findCustomer(int id) {
        return customerMapper.findCustomer(id);
    }

    @Override
    public int deleteCustomerById(int id) {
        return customerMapper.deleteCustomerById(id);
    }

    @Override
    public int insertCustomer(Customer customer) {
        return customerMapper.insertCustomer(customer);
    }

    @Override
    public int updateCustomer(Customer customer) {
        return customerMapper.updateCustomer(customer);
    }

    @Override
    public PageInfo<Customer> findCustomerByName(int pageNum, int pageSize, String customerName) {
        PageHelper.startPage(pageNum, pageSize);
        //模糊搜索的所有商品
        List<Customer> customerList = customerMapper.findCustomerByName(customerName);
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customerList);
        return customerPageInfo;
    }
}
