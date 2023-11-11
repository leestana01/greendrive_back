package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.Service.SubscriptionService;
import com.luckyseven.greendrive.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDto> addSubscription(@RequestBody SubscriptionDto dto){
        return new ResponseEntity<>(subscriptionService.addSubscription(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptionsByUserId(@PathVariable String userId){
        return new ResponseEntity<>(subscriptionService.getActiveSubscriptionsByUser(userId), HttpStatus.OK);
    }

}
