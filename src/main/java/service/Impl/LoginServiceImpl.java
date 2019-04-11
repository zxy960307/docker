package service.Impl;

import factory.LoginFactory;
import service.ILoginService;
import vo.Admin;

/**
 * Created by 41463 on 2019/4/11.
 */
public class LoginServiceImpl implements ILoginService {

    @Override
    public Admin findByName(String name) throws Exception {

        try {
            return LoginFactory.LoginiInstance().findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
