/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.circle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is a processBeanPostProcessorTest defined by Neil Wang.
 *
 * @author Neil Wang
 * @version 1.0.0
 */
public class SpringSourceSummaryTests {

    private ApplicationContext applicationContext;
    private A a;
    private A aByFactoryBean;
    private AFactoryBean aFactoryBean;

    @BeforeEach
    void setUp() {
        applicationContext = new ClassPathXmlApplicationContext("tx.xml");
        a = (A) applicationContext.getBean("a");
        aByFactoryBean = (A) applicationContext.getBean("aFactoryBean");
        aFactoryBean = (AFactoryBean) applicationContext.getBean("&aFactoryBean");
    }

    @Test
    public void should_set_correct_bean_definition_when_doing_my_processor() {
        assertThat(a.name()).isEqualTo("zhang-san");
    }

    @Test
    public void should_get_application_context_correctly_if_A_implements_application_context_aware() {
        System.out.println(a.context());
        assertThat(a.context()).isEqualTo(applicationContext);
    }

    @Test
    public void should_get_environment_correctly_if_A_implements_application_context_environment() {
        assertThat(a.environment().getProperty("java.runtime.name")).isEqualTo("Java(TM) SE Runtime Environment");
    }

    @Test
    void should_get_bean_name_correctly() {
        assertThat(a.beanName()).isEqualTo("a");
    }

    @Test
    void should_a_factory_bean_created_a() {
        assertThat(aByFactoryBean).hasToString("A{name='null'}");
    }

    @Test
    void should_a_factory_bean_created_a_factory_bean() throws Exception {
        assertThat(aFactoryBean.getObject()).isInstanceOf(A.class);
    }
}
