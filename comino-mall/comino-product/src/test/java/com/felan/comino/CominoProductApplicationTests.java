package com.felan.comino;

import com.felan.comino.product.entity.BrandEntity;
import com.felan.comino.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CominoProductApplicationTests {
    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Apple");
        brandEntity.setDescript("苹果1");
        brandService.save(brandEntity);
        System.out.println("保存成功");
    }

}
