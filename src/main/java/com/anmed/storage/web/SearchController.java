package com.anmed.storage.web;

import com.anmed.storage.model.Card;
import com.anmed.storage.service.CardService;
import com.anmed.storage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SearchController {

    private final CardService cardService;
    private final UserService userService;

    @Autowired
    public SearchController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchForCards(@RequestParam(value = "searchItem") String search, HttpServletRequest request, Model model) {
        List<Card> cards;
        if (request.isUserInRole("ROLE_ADMIN")) {
            cards = cardService.listAll();
        } else {
            cards = cardService.findCardsByContainingStringAndUser(
                    search, userService.findByUsername(request.getUserPrincipal().getName())
            );
        }
        model.addAttribute("cards", cards);
        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchPage() {
        return "search";
    }
}
