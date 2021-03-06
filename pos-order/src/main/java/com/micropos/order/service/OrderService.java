package com.micropos.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.dto.CartDto;
import com.micropos.dto.ItemDto;
import com.micropos.order.model.Cart;
import com.micropos.order.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@Service
public class OrderService {

    public static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private StreamBridge streamBridge;

    private RestTemplate restTemplate;

    private int orderCnt = 0;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double checkout(CartDto cartDto) {
        streamBridge.send("OrderDeliverer", new Order(orderCnt++, cartDto));
        int cartId = cartDto.getId();
        log.info("Checkout cart with ID {}", cartId);
        String url = "http://localhost:8080/carts/" +cartId + "/total";
        try {
            Double res = restTemplate.getForObject(url, Double.class);
            if (res == null)
                return -1;
            return res;
        }
        catch (Exception e) {
            return -1;
        }
    }
}
