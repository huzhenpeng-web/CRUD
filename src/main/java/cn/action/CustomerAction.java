package cn.action;

import cn.domain.Customer;
import cn.service.CustomerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/customer")
public class CustomerAction {
    @Autowired
    private CustomerService customerService;

//    @GetMapping("/findAllCustomer")
//    @ResponseBody
//    public List<Customer> findAllCustomer() {//查询所有顾客
//        List<Customer> customerList = customerService.findAllCustomer();
//        return customerList;
//    }

    //分页查询
/*    @RequestMapping(value = "/split")
    @ResponseBody
    public PageInfo<Customer> split(int pageNum, int pageSize) {
        PageInfo pageInfo = customerService.findCustomerByPage(pageNum, pageSize);
        return pageInfo;
    }*/

    @RequestMapping(value = "/findCustomer")
    @ResponseBody
    public Customer findCustomer(int id) {//按照id查找顾客
        return customerService.findCustomer(id);
    }

    @RequestMapping(value = "/insertCustomer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int insertCustomer(@RequestBody Customer customer) {
//        System.out.println(customer.getUsername());
        return customerService.insertCustomer(customer);
    }

    @RequestMapping("/deleteCustomerById")//根据id删除顾客
    @ResponseBody
    public int deleteCustomer(@RequestBody int id) {
        return customerService.deleteCustomerById(id);
    }

    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    //按照顾客名字模糊搜索
    @RequestMapping(value = "/findCustomerByName")
    @ResponseBody
    public PageInfo<Customer> findCustomerByName(int pageNum, int pageSize, String customerName) {
       PageInfo  pageInfo = customerService.findCustomerByName(pageNum,pageSize,customerName);
       return pageInfo;
    }
}
