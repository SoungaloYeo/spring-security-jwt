package securitypoc.service;

import securitypoc.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    String createUser(User user);

    String updateUserRoles(User user);

    User getUserInfo(HttpServletRequest req);

    User getUserByUsername(String username);

    String getToken(String username, String password);

    String refreshToken(String username);

    void deleteUser(String username);
}
