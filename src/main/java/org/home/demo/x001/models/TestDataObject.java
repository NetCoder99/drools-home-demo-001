package org.home.demo.x001.models;
import java.time.LocalDate;

public class TestDataObject implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	public String test;

	public String test_field1;

	public Integer test_field2;

	public String test_field3;

	private LocalDate test_field4;

	public TestDataObject() {
	}

	public String getTest() {
		return this.test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getTest_field1() {
		return this.test_field1;
	}

	public void setTest_field1(String test_field1) {
		this.test_field1 = test_field1;
	}

	public Integer getTest_field2() {
		return this.test_field2;
	}

	public void setTest_field2(Integer test_field2) {
		this.test_field2 = test_field2;
	}

	public String getTest_field3() {
		return test_field3;
	}

	public void setTest_field3(String test_field3) {
		this.test_field3 = test_field3;
	}

	public LocalDate getTest_field4() {
		return test_field4;
	}

	public void setTest_field4(LocalDate test_field4) {
		this.test_field4 = test_field4;
	}
}