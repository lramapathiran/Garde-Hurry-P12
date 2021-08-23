package com.lavanya.web.controller;

import com.lavanya.web.dto.ChildcareDto;
import com.lavanya.web.dto.CommentDto;
import com.lavanya.web.proxies.ChildcareProxy;
import com.lavanya.web.proxies.CommentProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String showFeedback(@PathVariable("id") int childcareId, @RequestParam("value") String feedbackAuthor, Model model){


        CommentDto commentDto = new CommentDto();
        model.addAttribute("feedbackAuthor", feedbackAuthor);
        model.addAttribute("commentDto", commentDto);
        model.addAttribute("childcareId", childcareId);

        return "addCommentAndRating";
    }

    @PostMapping("/saveComment")
    public String saveComment(@Valid @ModelAttribute("commentDto") CommentDto commentDto, BindingResult result, @ModelAttribute("feedbackAuthor") String feedbackAuthor,
                              @ModelAttribute("childcareId") int childcareId, Model model){

        if (result.hasErrors()) {
            model.addAttribute("feedbackAuthor", feedbackAuthor);
            model.addAttribute("commentDto", commentDto);
            model.addAttribute("childcareId", childcareId);
            return "addCommentAndRating";
        }

        CommentDto commentDtoSaved = commentProxy.saveComment(commentDto, childcareId, feedbackAuthor);

        if(feedbackAuthor.equals("childParent")) {
            return "redirect:/requests/childcares/" + commentDtoSaved.getUserComment().getId();
        }else{
            return "redirect:/missions/childcares/" + commentDtoSaved.getUserComment().getId();
        }
    }
}
