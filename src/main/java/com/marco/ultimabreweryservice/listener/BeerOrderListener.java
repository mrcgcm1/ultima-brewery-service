package com.marco.ultimabreweryservice.listener;

import com.marco.dtocommoninterface.config.JmsConfig;
import com.marco.dtocommoninterface.model.BeerDto;
import com.marco.dtocommoninterface.model.order.ValidateBeerOrderRequest;
import com.marco.dtocommoninterface.model.order.ValidateBeerOrderResponse;
import com.marco.ultimabreweryservice.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderListener {

    private final JmsTemplate jmsTemplate;

    private final BeerService beerService;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(@Payload ValidateBeerOrderRequest helloMessage, @Headers MessageHeaders headers, Message message) {

        System.out.println("Ricevuto il messaggio");

        System.out.println(helloMessage);

    }

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listenForHello(@Payload ValidateBeerOrderRequest helloMessage, @Headers MessageHeaders headers, Message message) throws JMSException {

        System.out.println("Ricevuto il messaggio dalla coda " + JmsConfig.VALIDATE_ORDER_QUEUE);

        System.out.println(helloMessage);

        BeerDto beer = beerService.getBeerById(helloMessage.getBeerId());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE_RESULT, ValidateBeerOrderResponse.builder().orderId(helloMessage.getBeerOrderDto().getId()).result(beer.getUpc() != null).build());


    }

}
