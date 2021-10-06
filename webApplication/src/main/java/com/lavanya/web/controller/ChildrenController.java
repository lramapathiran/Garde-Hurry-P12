package com.lavanya.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lavanya.web.dto.ChildrenDto;
import com.lavanya.web.dto.UserDto;
import com.lavanya.web.proxies.ChildrenProxy;
import com.lavanya.web.proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * Controller used in MVC architecture to control all the requests related to Children object.
 * @author lavanya
 */
@Controller
public class ChildrenController {

    @Autowired
    ChildrenProxy childrenProxy;

    @Autowired
    UserProxy userProxy;

    /**
     * GET requests for /createChildren endpoint.
     * This controller-method displays the form to associate a child to the user connected.
     *
     * @param model to pass data to the view.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return addChildren.html
     */
    @GetMapping("/createChildren")
    public String createChildren(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        String subToken = token.substring(7);
        DecodedJWT jwt = JWT.decode(subToken);
        String fullname = jwt.getClaim("fullname").asString();
        String role = jwt.getClaim("role").asString();

        model.addAttribute("role", role);

        UserDto userDto = userProxy.getUserConnected(token);
        int numberOfChildren = userDto.getChildrenDtos().size();

        model.addAttribute("fullname", fullname);
        model.addAttribute("totalChildren", numberOfChildren);

        ChildrenDto childrenDto = new ChildrenDto();
        model.addAttribute("childrenDto", childrenDto);
        return "addChildren";
    }

    /**
     * POST requests for /saveChildren endpoint.
     * This controller-method is part of CRUD and is used to save in database Children object.
     * @param childrenDto which is is the children to save.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return addChildren.html.
     */
    @PostMapping("/saveChildren")
    public String saveChildren(@ModelAttribute ("ChildrenDto") ChildrenDto childrenDto, HttpSession session){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        childrenProxy.saveChildren(childrenDto, token);
        return "redirect:/createChildren";
    }

    /**
     * POST requests for /delete/children endpoint.
     * This controller-method is part of CRUD and is used to delete in database Children object.
     * @param childrenId id of the Children object to delete.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return userProfile.html
     */
    @PostMapping("/delete/children")
    public String deleteChildren(@ModelAttribute ("id") int childrenId, HttpSession session){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        ChildrenDto childrenDto = childrenProxy.getChildrenById(childrenId, token);
        childrenProxy.deleteChildren(childrenDto, token);
        return "redirect:/user";
    }
}
