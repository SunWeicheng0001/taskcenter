package mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Select;

/**
 * Created by weicheng on 2015/7/14.
 */
public interface UserMapper {
    //@Select("select * from user where username=#{username}")
    public Map getUser(String username);
    //public void addUser(User user);
    //public void updateUser(User user);
    //public void deleteUser(String username, String password);
}
