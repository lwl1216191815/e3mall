package cn.e3mall.item.controller;

import cn.e3mall.common.exception.ItemDescNotFoundException;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.item.vo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) throws ItemDescNotFoundException {
        TbItem tbItem = itemService.getItemById(itemId);
        E3Result result = itemDescService.getDescByItemId(itemId);
        Item item = new Item(tbItem);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",result.getData());
        return "item";
    }
}
