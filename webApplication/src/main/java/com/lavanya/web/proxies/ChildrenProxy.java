package com.lavanya.web.proxies;

import com.lavanya.web.dto.ChildrenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * interface required to communicate with api module and make all the requests related to Children object.
 * @author lavanya
 */
@FeignClient(name = "childrenApi", url = "localhost:9090")
public interface ChildrenProxy {

    @PostMapping(value="saveChildren", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void saveChildren(@RequestBody ChildrenDto childrenDto);

    @GetMapping("children/{childrenId}")
    ChildrenDto getChildrenById(@PathVariable ("childrenId") int childrenId);

    @PostMapping(value = "delete/children", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void deleteChildren(@RequestBody ChildrenDto childrenDto);
}
