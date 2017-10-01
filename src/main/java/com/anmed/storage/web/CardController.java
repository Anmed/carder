package com.anmed.storage.web;

import com.anmed.storage.model.Card;
import com.anmed.storage.service.CardService;
import com.anmed.storage.service.UserService;
import com.anmed.storage.validator.CardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class CardController {

    private final CardService cardService;
    private final CardValidator cardValidator;
    private final UserService userService;

    @Autowired
    public CardController(CardService cardService, CardValidator cardValidator, UserService userService, UserService userService1) {
        this.cardService = cardService;
        this.cardValidator = cardValidator;
        this.userService = userService1;
    }

    @RequestMapping(value = "/card", method = RequestMethod.POST)
    public String addCard(@ModelAttribute("card") @Valid Card card, BindingResult bindingResult, Model model) {
        cardValidator.validate(card, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            return "card";
        }

        User springUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        card.setUser(userService.findByUsername(springUser.getUsername()));
        //since nothing was stated about other details - name also could be updated
        cardService.saveOrUpdate(card);
        return "redirect:/search";
    }

    @RequestMapping(value = "/card", method = RequestMethod.GET)
    public String cardPage(@ModelAttribute("card") Card card) {
        return "card";
    }


}
