package labshopmonolith.infra;
import labshopmonolith.domain.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RestController
// @RequestMapping(value="/inventories")
@Transactional
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;




    @RequestMapping(value = "inventories/{id}/decreaseinventory",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
    public Inventory decreaseInventory(@PathVariable(value = "id") Long id, @RequestBody DecreaseInventoryCommand decreaseInventoryCommand, HttpServletRequest request, HttpServletResponse response) throws Exception {
            System.out.println("##### /inventory/decreaseInventory  called #####");
            Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
            
            optionalInventory.orElseThrow(()-> new Exception("No Entity Found"));
            Inventory inventory = optionalInventory.get();
            inventory.decreaseInventory(decreaseInventoryCommand);
            
            inventoryRepository.save(inventory);
            return inventory;
            
    }
    



}
