package com.codegym.controller;

import com.codegym.model.Order;
import com.codegym.model.Province;
import com.codegym.model.User;
import com.codegym.service.order.IOrderService;
import com.codegym.service.order.OrderService;
import com.codegym.service.province.IProvinceService;
import com.codegym.service.user.IUserService;
import com.codegym.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces() {
        return provinceService.findAll();
    }

    @GetMapping("/info/{id}")
    public ModelAndView showUserInfo(@PathVariable Long id) {
        User user = userService.findbyId(id);
        Province province = provinceService.findById(user.getProvinceId());

        if (user.getName() != null) {
            ModelAndView modelAndView = new ModelAndView("user/detail");
            modelAndView.addObject(user);
            modelAndView.addObject(province);
            return modelAndView;
        } else {
            return new ModelAndView("user/error");
        }
    }

    @PostMapping("/info/{id}")
    public ModelAndView editCostomerInfo(@PathVariable Long id,@ModelAttribute User user) {
        User chosenUser = userService.findbyId(id);
        user.setUserId(chosenUser.getUserId());
        user.setPassword(chosenUser.getPassword());
        user.setUserStatus(chosenUser.isUserStatus());
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/user/detail");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "Edit info successfully");
        return modelAndView;
    }

    @GetMapping("/orders/{id}")
    public  ModelAndView listOrders(@PathVariable Long id,@PageableDefault(value = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/user/order/list");
        Page<Order> orders = orderService.findAllByUserId(id, pageable);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @GetMapping("/order-detail/{id}")
    public ModelAndView showOrder(@PathVariable Long id) {
        Order order = orderService.findbyId(id);
        ModelAndView modelAndView = new ModelAndView("/user/order/detail");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @GetMapping("/create-order/{id}")
    public ModelAndView createOrder(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/user/order/create");
        Order order = new Order();
        order.setUserId(id);
        modelAndView.addObject(order);
        return modelAndView;
    }

    @PostMapping("/create-order/{id}")
    public ModelAndView saveOrder(@PathVariable Long id, @ModelAttribute Order order) {
        ModelAndView modelAndView = new ModelAndView("/user/order/create");
        order.setUserId(id);
        order.setStatus(1);

        long millis = System.currentTimeMillis();
        Date createdDate = new Date(millis);
        order.setCreatedDate(createdDate);
        String temp = order.getReceiverAddress().trim();
        order.setReceiverAddress(temp);
        orderService.save(order);
        modelAndView.addObject("message", "Create new order successfully");
        return modelAndView;
    }
}
