package com.miu.mpp_project;

import org.junit.jupiter.api.Test;

import com.miu.mpp_projectservice.impl.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		OrderServiceImplTest.class,
		ProductServiceImplTest.class,
		UserServiceImplTest.class
})
class MppApplicationTests {


}
