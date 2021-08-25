package com.lavanya.web.proxies;

import com.lavanya.web.dto.ChildrenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * interface required to communicate with api module and make all the requests related to Children object.
 * @author lavanya
 */
@FeignClient(name = "childrenApi", url = "localhost:9090")
public interface ChildrenProxy {

    @PostMapping(value="/saveChildren", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void saveChildren(@RequestBody ChildrenDto childrenDto, @RequestHeader(name = "Authorization") String token);

    @GetMapping("/children/{childrenId}")
    ChildrenDto getChildrenById(@PathVariable ("childrenId") int childrenId, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value = "/delete/children", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void deleteChildren(@RequestBody ChildrenDto childrenDto, @RequestHeader(name = "Authorization") String token);
}
