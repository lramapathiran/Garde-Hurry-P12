package com.lavanya.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lavanya.web.dto.*;
import com.lavanya.web.proxies.ChildcareProxy;
import com.lavanya.web.proxies.ChildrenProxy;
import com.lavanya.web.proxies.FriendProxy;
import com.lavanya.web.proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Controller used in MVC architecture to control all the requests related to Childcare object.
 * @author lavanya
 */
@Controller
public class ChildcareController {

    @Autowired
    ChildcareProxy childcareProxy;

    @Autowired
    FriendProxy friendProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    ChildrenProxy childrenProxy;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/request/childcare")
    public String showChildcareRequestForm(HttpSession session, Model model) {

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

        ChildcareDto childcareDto = new ChildcareDto();
        List<FriendDto> friendDtos = friendProxy.getFriendsListByUser(token);
        UserDto userConnected = userProxy.getUserConnected(token);
        int totalChildren = userConnected.getChildrenDtos().size();

        List<UserDto> users = new ArrayList<>();

        for(FriendDto friendDto : friendDtos) {

            if(friendDto.getUserWhoInvite().getEmail().equals(userConnected.getEmail())){
                users.add(friendDto.getUserInvited());
            }else{
                users.add(friendDto.getUserWhoInvite());
            }
        }

        model.addAttribute("childcareDto", childcareDto);
        model.addAttribute("friends", users);
        model.addAttribute("maxChildren", totalChildren);

        return "requestChildcareStepOne";
    }

    @PostMapping("/saveChildcare")
    public String saveChildcare(@Valid @ModelAttribute ("childcareDto") ChildcareDto childcareDto, BindingResult result,
                                @ModelAttribute ("userDtoWatchingId") UUID userDtoWatchingId, HttpSession session, Model model){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        if (result.hasErrors()) {
            List<FriendDto> friendDtos = friendProxy.getFriendsListByUser(token);
            UserDto userConnected = userProxy.getUserConnected(token);
            int totalChildren = userConnected.getChildrenDtos().size();

            List<UserDto> users = new ArrayList<>();

            for(FriendDto friendDto : friendDtos) {

                if(friendDto.getUserWhoInvite().getEmail() == userConnected.getEmail()){
                    users.add(friendDto.getUserInvited());
                }else{
                    users.add(friendDto.getUserWhoInvite());
                }
            }

            model.addAttribute("childcareDto", childcareDto);
            model.addAttribute("friends", users);
            model.addAttribute("maxChildren", totalChildren);
            return "requestChildcareStepOne";
        }

        childcareDto.setUserDtoWatching(userProxy.getUser(userDtoWatchingId,token));
        ChildcareDto childcareDtoSaved = childcareProxy.saveChildcare(childcareDto,token);

        return "redirect:/save/request/children/" + childcareDtoSaved.getId();
    }

    @GetMapping("/save/request/children/{id}")
    public String completeChildcare(@PathVariable ("id") int childcareId, @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "name", required = false) String name, HttpSession session, Model model){

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

        String errorMessage = null;
        if(error != null) {
            errorMessage = "L'enfant " + name + " a déjà été ajouté, impossible de l'ajouter une seconde fois!";
        }
        model.addAttribute("errorMessage", errorMessage);

        ChildcareDto childcareDto = childcareProxy.getChildcareById(childcareId,token);
        model.addAttribute("childcare", childcareDto);

        int leftChildren = childcareDto.getNumberOfChildren() - childcareDto.getChildrenToWatch().size();
        model.addAttribute("leftChildren", leftChildren);

        if (childcareDto.getChildrenToWatch().size()<childcareDto.getNumberOfChildren()){
            String leftChildrenToAdd = "Vous devez encore ajouter " + leftChildren + " enfant(s) pour finaliser le formulaire de garde !";
            model.addAttribute("uncompleteAdditionOfChildrenToWatch", leftChildrenToAdd);
        }else{
            String success = "Vous avez compléter le formulaire de demande de garde avec succès, vous pouvez le valider pour notifier votre contact!";
            model.addAttribute("completeAdditionOfChildrenToWatch", success);
        }
        return "requestChildcareStepTwo";
    }

    @PostMapping("/saveChildrenToWatch")
    public String saveChildrenToWatchToChildcare(@ModelAttribute("childrenToWatchId") int childrenToWatchId, @ModelAttribute("childcareId") int childcareId,
                                                 HttpSession session) throws UnsupportedEncodingException {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        try{
            childcareProxy.saveChildrenToWatchToChildcare(childrenToWatchId,childcareId, token);
            return "redirect:/save/request/children/" + childcareId;
        }catch(Exception e){
            String name = childrenProxy.getChildrenById(childrenToWatchId,token).getName();
            String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
            String capNameEncoded = URLEncoder.encode(capName, "Utf-8");

            return "redirect:/save/request/children/" + childcareId + "?error=true&name=" + capNameEncoded;
        }


    }

    @PostMapping("/delete/childrenToWatch")
    public String deleteChildrenToWatchInChildcare(@ModelAttribute("childrenToWatchId") int childrenToWatchId,
                                                   @ModelAttribute("childcareId") int childcareId, HttpSession session){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        childcareProxy.deleteChildrenToWatchInChildcare(childrenToWatchId,childcareId,token);
        return "redirect:/save/request/children/" + childcareId;
    }

    @PostMapping("/validate/request/childcare")
    public String completeChildcareRequest(@ModelAttribute("childcareId") int childcareId, HttpSession session) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        childcareProxy.completeChildcareRequest(childcareId,token);
        return "redirect:/requests/childcares";
    }

    @GetMapping("/requests/childcares")
    public String showUserChildcaresRequestsDashboard(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        String subToken = token.substring(7);
        DecodedJWT jwt = JWT.decode(subToken);
        String fullname = jwt.getClaim("fullname").asString();String role = jwt.getClaim("role").asString();

        model.addAttribute("role", role);
        model.addAttribute("fullname", fullname);

        UserDto userDto = userProxy.getUserConnected(token);
        List<ChildcareDto> childcareDtosRequests = userDto.getChildcareDtosRequests();

        List<ChildcareDto> uncompleteChildcareDtosRequests = new ArrayList<>();
        List<ChildcareDto> unvalidatedChildcareDtosRequests = new ArrayList<>();
        List<ChildcareDto> validatedChildcareDtosRequests = childcareProxy.getChildcaresOfUserInNeedNotCommented(token);

        for(ChildcareDto childcareDto : childcareDtosRequests) {
            if(childcareDto.getComplete()==false) {
                uncompleteChildcareDtosRequests.add(childcareDto);
            }
            if(childcareDto.getComplete()==true && childcareDto.getValidated()==null){
                unvalidatedChildcareDtosRequests.add(childcareDto);
            }
        }
        model.addAttribute("uncompleteChildcares", uncompleteChildcareDtosRequests);
        model.addAttribute("validatedChildcares", validatedChildcareDtosRequests);
        model.addAttribute("unvalidatedChildcares", unvalidatedChildcareDtosRequests);

        return "childcaresRequestsDashboard";
    }

    @GetMapping("/missions/childcares")
    public String showUserChildcaresMissionsDashboard(HttpSession session, Model model) {

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

        UserDto userDto = userProxy.getUserConnected(token);

        List<ChildcareDto> childcareDtosMissions = userDto.getChildcareDtosMissions();

        List<ChildcareDto> awaitingChildcareDtosMissions = new ArrayList<>();
        List<ChildcareDto> acceptedChildcareDtosMissions = childcareProxy.getChildcaresOfUserInChargeNotCommented(token);

        for(ChildcareDto childcareDto : childcareDtosMissions) {
            if(childcareDto.getComplete() && childcareDto.getValidated()==null) {
                awaitingChildcareDtosMissions.add(childcareDto);
            }
        }

        model.addAttribute("childcaresMissions", acceptedChildcareDtosMissions);
        model.addAttribute("unvalidatedChildcaresMissions", awaitingChildcareDtosMissions);

        return "childcaresMissionsDashboard";
    }

    @PostMapping("/validateChildcare")
    public String validateChildcareByUserInCharge(ValidateChildcare validateChildcare, HttpSession session){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        ChildcareDto childcareDto = childcareProxy.getChildcareById(validateChildcare.getChildcareToValidateId(),token);

        if(validateChildcare.getChildcareStatus()==null){
            childcareDto.setValidated(false);
        }else{
            childcareDto.setValidated(true);
        }


        childcareProxy.validateOrNotChildcare(childcareDto,token);

        return "redirect:/missions/childcares";
    }

    @PostMapping("/delete/childcare")
    public String deleteChildcare(@ModelAttribute ("id") int childcareId, @ModelAttribute ("personWhoDelete") String personWhoDelete, HttpSession session) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        childcareProxy.deleteChildcare(childcareId,token);

        if(personWhoDelete.equals("childParent")) {
            return "redirect:/requests/childcares";
        }else{
            return "redirect:/missions/childcares";
        }
    }

    @PostMapping("/markAccomplishedChildcare")
    public String markChildcareAsAccomplished(@ModelAttribute ("childcareAccomplishedId") int childcareId, HttpSession session) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        childcareProxy.accomplishChildcare(childcareId,token);
        return "redirect:/missions/childcares";

    }


}
