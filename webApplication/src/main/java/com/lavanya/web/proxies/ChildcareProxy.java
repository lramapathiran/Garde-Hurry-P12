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
    ChildcareDto saveChildcare(@RequestBody ChildcareDto childcareDto);

    @GetMapping("/childcare/{id}")
    ChildcareDto getChildcareById(@PathVariable("id") int childcareId);

    @PostMapping(value="/saveChildrenToWatch/{childrenToWatchId}/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void saveChildrenToWatchToChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId);

    @PostMapping(value="/deleteChildrenToWatch/{childrenToWatchId}/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void deleteChildrenToWatchInChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId);

    @PostMapping(value="validate/request/childcare/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void completeChildcareRequest(@PathVariable("childcareId") int childcareId);

    @PostMapping(value="/deleteChildcare/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void deleteChildcare(@PathVariable("childcareId") int childcareId);

    @PostMapping(value="/validate/childcare", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void validateOrNotChildcare(@RequestBody ChildcareDto childcareDto);

    @PostMapping(value="accomplish/childcare/{childcareId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void accomplishChildcare(@PathVariable("childcareId") int childcareId);

    @PostMapping("/userInNeed/childcares/Uncommented")
    List<ChildcareDto> getChildcaresUserInNeedNotCommented(@RequestBody UserDto userInNeedDto);

    @PostMapping("/userInCharge/childcares/Uncommented")
    List<ChildcareDto> getChildcaresUserInChargeNotCommented(@RequestBody UserDto userInChargeDto);

    @GetMapping("/user/count/positiveBadges/{id}")
    Integer countOfPositiveBadgesByUserId(@PathVariable("id") int userId);

    @GetMapping("/user/count/negativeBadges/{id}")
    Integer countOfNegativeBadgesByUserId(@PathVariable("id") int userId);
}
