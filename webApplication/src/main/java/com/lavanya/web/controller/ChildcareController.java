package com.lavanya.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lavanya.web.dto.*;
import com.lavanya.web.proxies.*;
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
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Autowired
    NotificationProxy notificationProxy;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * GET requests for /request/childcare endpoint.
     * This controller-method displays the form for the user connected to create a childcare request.
     * This is the first step before sending the request to the user in charge.
     *
     * @param model to pass data to the view.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return requestChildcareStepOne.html
     */
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

    /**
     * POST requests for /saveChildcare endpoint.
     * This controller-method is part of CRUD and is used to save in database Childcare object.
     * @param childcareDto which is is the childcare to save.
     * @param result check if there is any error in the childcare object before saving it.
     * @param userDtoWatchingId id of the user who the request is sent to so the user in charge.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @param model to pass data to the view.
     * @return requestChildcareStepTwo.html.
     */
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

    /**
     * GET requests for /save/request/children/{id} endpoint.
     * This controller-method displays the form for the user connected to associate the children to watch to the childcare created in step one.
     * This is the second step before sending the request to the user in charge.
     *
     * @param childcareId id of the childcare created in step one.
     * @param error only when an error exists while associating a children to watch to a childcare.
     * @param name of the children to watch generating an error.
     * @param model to pass data to the view.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return requestChildcareStepTwo.html
     */
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

    /**
     * POST requests for /saveChildrenToWatch endpoint.
     * This controller-method is part of CRUD and is used to save in database ChildrenToWatch associated to a childcare.
     * @param childrenToWatchId if of the children object to save as a childrenToWatch.
     * @param childcareId id of the childcare for which the children to watch is associated.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @throws UnsupportedEncodingException raised when characters are not supported
     * @return requestChildcareStepTwo.html.
     */
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

    /**
     * POST requests for /delete/childrenToWatch endpoint.
     * This controller-method is part of CRUD and is used to delete in database ChildrenToWatch object associated to a specific childcare.
     * @param childrenToWatchId id of the childrenToWatch object to delete.
     * @param childcareId id of the childcare for which the children to watch is associated.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return requestChildcareStepTwo.html.
     */
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

    /**
     * POST requests for /validate/request/childcare endpoint.
     * This controller-method marks a childcare as complete with all information and triggers automated notifications to the user in charge.
     * @param childcareId id of the childcare for which the children to watch is associated.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return childcaresRequestsDashboard.html.
     */
    @PostMapping("/validate/request/childcare")
    public String completeChildcareRequest(@ModelAttribute("childcareId") int childcareId, HttpSession session) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }
        childcareProxy.completeChildcareRequest(childcareId,token);

        ChildcareDto childcareDto = childcareProxy.getChildcareById(childcareId,token);
        String fromFullId = childcareDto.getUserDtoInNeed().getFirstName() + " " + childcareDto.getUserDtoInNeed().getLastName();
        String fromEmail = childcareDto.getUserDtoInNeed().getEmail();
        String  toFullId= childcareDto.getUserDtoWatching().getFirstName() + " " + childcareDto.getUserDtoWatching().getLastName();
        String toEmail= childcareDto.getUserDtoWatching().getEmail();
        LocalDate date = childcareDto.getDate();
        LocalTime timeStart = childcareDto.getTimeStart();
        LocalTime timeEnd = childcareDto.getTimeEnd();

        NotificationDto notificationDto = new NotificationDto(fromFullId,fromEmail,toFullId,toEmail,date,timeStart,timeEnd);

        notificationProxy.sendChildcareNotificationToUserInCharge(notificationDto);

        return "redirect:/requests/childcares";
    }

    /**
     * GET requests for /requests/childcares endpoint.
     * This controller-method retrieves from database all childcares requests not finalised  finalised but awaiting for approval from user in charge and for childcares requests accepted.
     * This endpoint displays all childcares requested by the user connected and allows all actions on it.
     *
     * @param model to pass data to the view.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return childcaresRequestsDashboard.html
     */
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

    /**
     * GET requests for /missions/childcares endpoint.
     * This controller-method retrieves from database all childcares missions the user connected was requested for.
     * This endpoint displays all childcares missions the user connected has to accept or refuse and has to accomplished for the childcares accepted.
     * This is a dashboard where the user connected can perform several actions.
     *
     * @param model to pass data to the view.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return childcaresMissionsDashboard.html
     */
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

    /**
     * POST requests for /validateChildcare endpoint.
     * This controller-method is used to update isValidated attribute of childcare object.
     * @param validateChildcare object that passes information to validate or not a childcare.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return childcaresMissionsDashboard.html
     */
    @PostMapping("/validateChildcare")
    public String validateChildcareByUserInCharge(ValidateChildcare validateChildcare, HttpSession session){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        ChildcareDto childcareDto = childcareProxy.getChildcareById(validateChildcare.getChildcareToValidateId(),token);

        String fromFullId = childcareDto.getUserDtoWatching().getFirstName() + " " + childcareDto.getUserDtoWatching().getLastName();
        String fromEmail = childcareDto.getUserDtoWatching().getEmail();
        String  toFullId= childcareDto.getUserDtoInNeed().getFirstName() + " " + childcareDto.getUserDtoInNeed().getLastName();
        String toEmail= childcareDto.getUserDtoInNeed().getEmail();
        LocalDate date = childcareDto.getDate();
        LocalTime timeStart = childcareDto.getTimeStart();
        LocalTime timeEnd = childcareDto.getTimeEnd();

        NotificationDto notificationDto = new NotificationDto(fromFullId,fromEmail,toFullId,toEmail,date,timeStart,timeEnd);

        if(validateChildcare.getChildcareStatus()==null){
            childcareDto.setValidated(false);
            notificationProxy.sendChildcareRefusalNotificationToUserInNeed(notificationDto);
        }else{
            childcareDto.setValidated(true);
            notificationProxy.sendChildcareAcceptanceNotificationToUserInNeed(notificationDto);
        }


        childcareProxy.validateOrNotChildcare(childcareDto,token);

        return "redirect:/missions/childcares";
    }

    /**
     * POST requests for /delete/childcare endpoint.
     * This controller-method is part of CRUD and is used to delete in database Childcare object.
     * @param childcareId id of the Childcare object to delete.
     * @param personWhoDelete string to identify if the person deleting the childcare is the user in charge or in need in this specific childcare.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return childcaresRequestsDashboard.html or childcaresMissionsDashboard.html according to the personWhoDelete.
     */
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

    /**
     * POST requests for /markAccomplishedChildcare endpoint.
     * This controller-method is used to update isAccomplished attribute of childcare object and so mark a childcare as accomplished by the user in charge.
     * @param childcareId id of the Childcare object to mark as accomplished
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return childcaresMissionsDashboard.html
     */
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
