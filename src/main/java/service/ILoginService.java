package service;

import vo.Admin;

/**
 * Created by 41463 on 2019/4/11.
 */
public interface ILoginService {
    public Admin findByName (String name) throws Exception;
}
