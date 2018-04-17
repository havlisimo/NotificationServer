package cz.cvut.fit.havlito4.notification_server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//mapuje tabulku test
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //mapuje primární klíč test_id, kterej se automaticky generuje
    private Long testId;


    //mapuje atribut test_attribute
    private String testAttribute;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestAttribute() {
        return testAttribute;
    }

    public void setTestAttribute(String testAttribute) {
        this.testAttribute = testAttribute;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "testId=" + testId +
                ", testAttribute='" + testAttribute + '\'' +
                '}';
    }
}
