package com.lavanya.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lavanya.web.dto.ChildcareDto;
import com.lavanya.web.dto.CommentDto;
import com.lavanya.web.proxies.ChildcareProxy;
import com.lavanya.web.proxies.CommentProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Controller used in MVC architecture to control all the requests related to Comment object.
 * @author lavanya
 */
@Controller
public class CommentController {

    @Autowired
    ChildcareProxy childcareProxy;

    @Autowired
    CommentProxy commentProxy;

    @GetMapping("/feedback/{id}")
    public String showFeedback(@PathVariable("id") int childcareId, @RequestParam("value") String feedbackAuthor, HttpSession session, Model model){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        String subToken = token.substring(7);
        DecodedJWT jwt = JWT.decode(subToken);
        String fullname = jwt.getClaim("fullname").asString();
        String role = jwt.getClaim("role").asString();

        model.addAttribute("role", role);
        model.addAttribute("fullname", fullname);

        CommentDto commentDto = new CommentDto();
        model.addAttribute("feedbackAuthor", feedbackAuthor);
        model.addAttribute("commentDto", commentDto);
        model.addAttribute("childcareId", childcareId);

        return "addCommentAndRating";
    }

    @PostMapping("/saveComment")
    public String saveComment(@Valid @ModelAttribute("commentDto") CommentDto commentDto, BindingResult result, @ModelAttribute("feedbackAuthor") String feedbackAuthor,
                              @ModelAttribute("childcareId") int childcareId, HttpSession session, Model model){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        if (result.hasErrors()) {
            model.addAttribute("feedbackAuthor", feedbackAuthor);
            model.addAttribute("commentDto", commentDto);
            model.addAttribute("childcareId", childcareId);
            return "addCommentAndRating";
        }

        CommentDto commentDtoSaved = commentProxy.saveComment(commentDto, childcareId, feedbackAuthor,token);

        if(feedbackAuthor.equals("childParent")) {
            return "redirect:/requests/childcares";
        }else{
            return "redirect:/missions/childcares";
        }
    }
}
