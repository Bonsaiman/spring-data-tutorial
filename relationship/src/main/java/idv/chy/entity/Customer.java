package idv.chy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_customer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long customerId;

    @Column(name = "name")
    private String customerName;

    @Column(name = "address")
    private String customerAddress;


    /**
     * cascade 設定關聯
     *      ALL,        所有持久化操作
     *      PERSIST,    只有插入才執行關聯操作
     *      MERGE,      只有修改才執行關聯操作
     *      REMOVE,     只有刪除才執行關聯操作
     *      REFRESH,
     *      DETACH
     * fetch 設定載入模式
     *      LAZY,       懶加載
     *      EAGER,      直接加載(預設)
     * orphanRemoval 移除關聯 (通常在修改的時候用到)
     *      一旦把關聯的資料設置為null, 或者修改為其他關聯資料
     *      如果想刪除關聯數據, 則可設置為true
     * optional 限制關聯的對象不能為null
     *      true,   可以為null
     *      false,  不能為null
     * mappedBy 將FK Constraint 交給另一方維護 (通常在雙向關聯中放棄一方約束)
     *      value = 另一方關聯的屬姓名
     */

    @OneToOne(mappedBy = "customer",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private Account account;

    // many to one
    @OneToMany(cascade = CascadeType.ALL)   // one to many 默認Lazy fetch
    @JoinColumn(name = "customer_id")
    private List<Message> messages;
}
