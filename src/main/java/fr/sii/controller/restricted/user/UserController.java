package fr.sii.controller.restricted.user;

import fr.sii.controller.restricted.RestrictedController;
import fr.sii.domain.exception.NotFoundException;
import fr.sii.domain.exception.NotVerifiedException;
import fr.sii.dto.user.UserProfil;
import fr.sii.entity.User;
import fr.sii.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/restricted", produces = "application/json; charset=utf-8")
public class UserController extends RestrictedController {

    @Autowired
    private UserService userService;

    /**
     * Get current user profil
     *
     * @param req
     * @return
     * @throws NotVerifiedException
     * @throws NotFoundException
     * @throws IOException
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map<String, Object> getUserProfil(HttpServletRequest req) throws NotVerifiedException, NotFoundException, IOException {
        int userId = retrieveUserId(req);

        User u = userService.findById(userId);
        if (u == null) {
            throw new NotFoundException("User id [" + userId + "] not found");
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("email", u.getEmail());
        map.put("lastname", u.getLastname());
        map.put("firstname", u.getFirstname());
        map.put("phone", u.getPhone());
        map.put("company", u.getCompany());
        map.put("bio", u.getBio());
        map.put("social", u.getSocial());
        map.put("twitter", u.getTwitter());
        map.put("googleplus", u.getGoogleplus());
        map.put("github", u.getGithub());
        map.put("imageProfilURL", u.getImageProfilURL());

        return map;
    }

    /**
     * Edit current user profil
     *
     * @param req
     * @param profil
     * @return
     * @throws NotVerifiedException
     * @throws NotFoundException
     * @throws IOException
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public UserProfil putUserProfil(HttpServletRequest req, @RequestBody UserProfil profil) throws NotVerifiedException, NotFoundException, IOException {
        int userId = retrieveUserId(req);

        User u = userService.findById(userId);
        if (u == null) {
            throw new NotFoundException("User id [" + userId + "] not found");
        }
        profil.setEmail(u.getEmail());
        userService.update(u.getId(), profil);

        return profil;

    }
}
