package factory;

import dao.ILoginDao;
import dao.impl.LoginDaoImpl;

/**
 * Created by 41463 on 2019/4/11.
 */
public class LoginFactory {
    public static ILoginDao LoginiInstance() {
        return new LoginDaoImpl();
    }
}
