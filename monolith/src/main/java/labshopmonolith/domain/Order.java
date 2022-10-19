package labshopmonolith.domain;

import labshopmonolith.domain.OrderPlaced;
import labshopmonolith.MonolithApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Order_table")
@Data

public class Order  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String productId;
    
    
    
    
    
    private Integer qty;
    
    
    
    
    
    private String customerId;
    
    
    
    
    
    private Double amount;

    @PostPersist
    public void onPostPersist(){

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        labshopmonolith.external.DecreaseInventoryCommand decreaseInventoryCommand = new labshopmonolith.external.DecreaseInventoryCommand();
        // mappings goes here
        MonolithApplication.applicationContext.getBean(labshopmonolith.external.InventoryService.class)
            .decreaseInventory(/* get???(), */ decreaseInventoryCommand);



        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();

    }
    @PrePersist
    public void onPrePersist(){
    }

    public static OrderRepository repository(){
        OrderRepository orderRepository = MonolithApplication.applicationContext.getBean(OrderRepository.class);
        return orderRepository;
    }






}
