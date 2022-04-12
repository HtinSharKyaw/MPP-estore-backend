package com.miu.mpp_project.service;


        import com.miu.mpp_project.model.OrderMain;
        import com.miu.mpp_project.model.Product;
        import com.miu.mpp_project.enums.OrderStatusEnum;
        import com.miu.mpp_project.exceptions.MyException;
        import com.miu.mpp_project.repository.OrderRepository;
        import com.miu.mpp_project.repository.ProductRepository;
        import com.miu.mpp_project.service.ProductService;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.springframework.test.context.junit4.SpringRunner;

        import java.util.HashSet;
        import java.util.Set;

        import static org.hamcrest.CoreMatchers.*;
        import static org.junit.Assert.assertThat;
        import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productInfoRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    private OrderMain orderMain;

    private Product product;

    @Before
    public void setUp() {
        orderMain = new OrderMain();
        orderMain.setOrderId(1L);
        orderMain.setOrderStatus(OrderStatusEnum.NEW.getCode());

        Product product = new Product();
        product.setId(1);


        Set<Product> set = new HashSet<>();
        set.add(product);

        orderMain.setProducts(set);

          product = new Product();
          product.setProductStock(10);
    }

    @Test
    public void finishSuccessTest() {
        when(orderRepository.findByOrderId(orderMain.getOrderId())).thenReturn(orderMain);

        OrderMain orderMainReturn = orderService.finish(orderMain.getOrderId());

        assertThat(orderMainReturn.getOrderId(), is(orderMain.getOrderId()));
        assertThat(orderMainReturn.getOrderStatus(), is(OrderStatusEnum.FINISHED.getCode()));
    }

    @Test(expected = MyException.class)
    public void finishStatusCanceledTest() {
        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());

        when(orderRepository.findByOrderId(orderMain.getOrderId())).thenReturn(orderMain);

        OrderMain orderMainReturn = orderService.finish(orderMain.getOrderId());

        assertThat(orderMainReturn.getOrderId(), is(orderMain.getOrderId()));
        assertThat(orderMainReturn.getOrderStatus(), is(OrderStatusEnum.FINISHED.getCode()));
    }

    @Test(expected = MyException.class)
    public void finishStatusFinishedTest() {
        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        when(orderRepository.findByOrderId(orderMain.getOrderId())).thenReturn(orderMain);

        OrderMain orderMainReturn = orderService.finish(orderMain.getOrderId());

        assertThat(orderMainReturn.getOrderId(), is(orderMain.getOrderId()));
        assertThat(orderMainReturn.getOrderStatus(), is(OrderStatusEnum.FINISHED.getCode()));
    }

    @Test
    public void cancelSuccessTest() {
        when(orderRepository.findByOrderId(orderMain.getOrderId())).thenReturn(orderMain);
        when(productInfoRepository.findById(orderMain.getProducts().iterator().next().getId())).thenReturn(product);

        OrderMain orderMainReturn = orderService.cancel(orderMain.getOrderId());

        assertThat(orderMainReturn.getOrderId(), is(orderMain.getOrderId()));
        assertThat(orderMainReturn.getOrderStatus(), is(OrderStatusEnum.CANCELED.getCode()));
        assertThat(orderMainReturn.getProducts().iterator().next().getProductStock(), is(10));
    }

    @Test
    public void cancelNoProduct() {
        when(orderRepository.findByOrderId(orderMain.getOrderId())).thenReturn(orderMain);
        orderMain.setProducts(new HashSet<>());

        OrderMain orderMainReturn = orderService.cancel(orderMain.getOrderId());

        assertThat(orderMainReturn.getOrderId(), is(orderMain.getOrderId()));
        assertThat(orderMainReturn.getOrderStatus(), is(OrderStatusEnum.CANCELED.getCode()));
    }

    @Test(expected = MyException.class)
    public void cancelStatusCanceledTest() {
        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());

        when(orderRepository.findByOrderId(orderMain.getOrderId())).thenReturn(orderMain);

        orderService.cancel(orderMain.getOrderId());
    }

    @Test(expected = MyException.class)
    public void cancelStatusFinishTest() {
        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        when(orderRepository.findByOrderId(orderMain.getOrderId())).thenReturn(orderMain);

        orderService.cancel(orderMain.getOrderId());
    }
}
