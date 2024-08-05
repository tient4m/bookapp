package com.project.shopapp.controllers;


import com.project.shopapp.services.VNPayService.IVNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/")
public class VNPayController {
    @Autowired
    private IVNPayService vnPayService;


    @GetMapping("")
    public String home(){
        return "index";
    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                                      @RequestParam("orderInfo") String orderInfo,
                                      HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
       return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        return "redirect:" + "http://localhost:4200/";
    }
}
