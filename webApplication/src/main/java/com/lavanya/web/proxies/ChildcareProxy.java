package com.lavanya.web.proxies;

import com.lavanya.web.dto.ChildcareDto;
import com.lavanya.web.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * interface required to communicate with api module and make all the requests related to Childcare object.
 * @author lavanya
 */
@FeignClient(name = "childcareApi", url = "localhost:9090")
public interface ChildcareProxy {

    @PostMapping(value="/saveChildcare", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    ChildcareDto saveChildcare(@RequestBody ChildcareDto childcareDto, @RequestHeader(name = "Authorization") String token);

    @GetMapping("/childcare/{id}")
    ChildcareDto getChildcareById(@PathVariable("id") int childcareId, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value="/saveChildrenToWatch/{childrenToWatchId}/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void saveChildrenToWatchToChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value="/deleteChildrenToWatch/{childrenToWatchId}/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void deleteChildrenToWatchInChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value="validate/request/childcare/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void completeChildcareRequest(@PathVariable("childcareId") int childcareId, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value="/deleteChildcare/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void deleteChildcare(@PathVariable("childcareId") int childcareId, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value="/validate/childcare", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void validateOrNotChildcare(@RequestBody ChildcareDto childcareDto, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value="accomplish/childcare/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void accomplishChildcare(@PathVariable("childcareId") int childcareId, @RequestHeader(name = "Authorization") String token);

    @GetMapping("/userInNeed/childcares/Uncommented")
    List<ChildcareDto> getChildcaresOfUserInNeedNotCommented(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/userInCharge/childcares/Uncommented")
    List<ChildcareDto> getChildcaresOfUserInChargeNotCommented(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/userInNeed/childcares/ToAccomplish")
    List<ChildcareDto> getChildcaresOfUserInNeedToAccomplish(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/userInCharge/childcares/ToAccomplish")
    List<ChildcareDto> getChildcaresOfUserInChargeToAccomplish(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/user/count/positiveBadges")
    Integer countOfPositiveBadgesByUserId(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/user/count/negativeBadges")
    Integer countOfNegativeBadgesByUserId(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/userInCharge/count/childcaresToComment")
    Integer countOfChildcaresToCommentByUserInChargeId(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/userInNeed/count/childcaresToComment")
    Integer countOfChildcaresToCommentByUserInNeedId(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/userInCharge/count/childcaresToValidate")
    Integer countOfChildcaresToValidateByUserInChargeId(@RequestHeader(name = "Authorization") String token);
}
