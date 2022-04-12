package com.miu.mpp_project.service;



import com.miu.mpp_project.model.Product;
import com.miu.mpp_project.enums.ProductStatusEnum;
import com.miu.mpp_project.exceptions.MyException;
import com.miu.mpp_project.repository.ProductRepository;
import com.miu.mpp_project.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productInfoRepository;


    private Product product;

    @Before
    public void setUp() {
        product = new Product();
        product.setId(1);
        product.setProductStock(10);
        product.setProductStatus(1);
    }

    @Test
    public void increaseStockTest() {
        when(productInfoRepository.findById(product.getId())).thenReturn(product);

        productService.increaseStock(1, 10);

        Mockito.verify(productInfoRepository, Mockito.times(1)).save(product);
    }

    @Test(expected = MyException.class)
    public void increaseStockExceptionTest() {
        productService.increaseStock(1, 10);
    }

    @Test
    public void decreaseStockTest() {
        when(productInfoRepository.findById(product.getId())).thenReturn(product);

        productService.decreaseStock(1, 9);

        Mockito.verify(productInfoRepository, Mockito.times(1)).save(product);
    }

    @Test(expected = MyException.class)
    public void decreaseStockValueLesserEqualTest() {
        when(productInfoRepository.findById(product.getId())).thenReturn(product);

        productService.decreaseStock(1, 10);
    }

    @Test(expected = MyException.class)
    public void decreaseStockExceptionTest() {
        productService.decreaseStock(1, 10);
    }

    @Test
    public void offSaleTest() {
        product.setProductStatus(ProductStatusEnum.UP.getCode());

        when(productInfoRepository.findById(product.getId())).thenReturn(product);

        productService.offSale(1);

        Mockito.verify(productInfoRepository, Mockito.times(1)).save(product);
    }

    @Test(expected = MyException.class)
    public void offSaleStatusDownTest() {
        product.setProductStatus(ProductStatusEnum.DOWN.getCode());

        when(productInfoRepository.findById(product.getId())).thenReturn(product);

        productService.offSale(1);
    }

    @Test(expected = MyException.class)
    public void offSaleProductNullTest() {
        when(productInfoRepository.findById(product.getId())).thenReturn(null);

        productService.offSale(1);
    }

    @Test
    public void onSaleTest() {
        product.setProductStatus(ProductStatusEnum.DOWN.getCode());

        when(productInfoRepository.findById(product.getId())).thenReturn(product);

        productService.onSale(1);

        Mockito.verify(productInfoRepository, Mockito.times(1)).save(product);
    }

    @Test(expected = MyException.class)
    public void onSaleStatusUpTest() {
        product.setProductStatus(ProductStatusEnum.UP.getCode());

        when(productInfoRepository.findById(product.getId())).thenReturn(product);

        productService.onSale(1);
    }

    @Test(expected = MyException.class)
    public void onSaleProductNullTest() {
        when(productInfoRepository.findById(product.getId())).thenReturn(null);

        productService.offSale(1);
    }

    @Test
    public void updateTest() {
        productService.update(product);

        Mockito.verify(productInfoRepository, Mockito.times(1)).save(product);
    }

    @Test(expected = MyException.class)
    public void updateProductStatusBiggerThenOneTest() {
        product.setProductStatus(2);

        productService.update(product);
    }

    @Test
    public void deleteTest() {
        when(productInfoRepository.findById(product.getId())).thenReturn(product);

        productService.delete(1);

        Mockito.verify(productInfoRepository, Mockito.times(1)).delete(product);
    }

    @Test(expected = MyException.class)
    public void deleteProductNullTest() {
        productService.delete(1);
    }
}