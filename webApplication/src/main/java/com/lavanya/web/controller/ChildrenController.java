package com.lavanya.web.controller;

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

    @GetMapping("/createChildren/{userConnectedId}")
    public String createChildren(@PathVariable("userConnectedId") int userConnectedId, Model model) {
        ChildrenDto childrenDto = new ChildrenDto();
        model.addAttribute("childrenDto", childrenDto);
        model.addAttribute("userConnectedId",userConnectedId);
        return "addChildren";
    }

    @PostMapping("/saveChildren")
    public String saveChildren(@ModelAttribute ("ChildrenDto") ChildrenDto childrenDto, @ModelAttribute("userConnectedId") int userId){

        UserDto userDto = userProxy.getUserConnected(userId);
        childrenDto.setUser(userDto);

        childrenProxy.saveChildren(childrenDto);
        return "redirect:/createChildren/" + userId;
    }
}
