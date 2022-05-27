package cn.mapper;

import cn.domain.Customer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerMapper {
//    /**
//     * 查询所有顾客
//     */
/*    @Select("select * from t_customer")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "jobs", property = "jobs"),
            @Result(column = "phone", property = "phone")
    })
    List<Customer> findAllCustomer();*/

    @Select("select * from t_customer where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "jobs", property = "jobs"),
            @Result(column = "phone", property = "phone")
    })
    Customer findCustomer(int id);

    @Insert("insert into t_customer(username,jobs,phone) values(#{username},#{jobs},#{phone})")
    int insertCustomer(Customer customer);

    @Update("update t_customer set username = #{username}, jobs = #{jobs} , phone = #{phone} where id = #{id}")
    int updateCustomer(Customer customer);

    @Delete("delete from t_customer where id = #{id}")
    int deleteCustomerById(int id);

    /**
     * 模糊搜索 顾客姓名
     */
    @Select("select * from t_customer where username like concat('%',#{username,jdbcType=VARCHAR},'%')")
    List<Customer> findCustomerByName(String customerName);
}
