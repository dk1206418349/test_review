package com.cy.pj.common.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult implements Serializable{
	private static final long serialVersionUID = 2080943344863436345L;
	//状态码
	private int state=1;
	//状态信息
	private String message="ok";
	//封装控制层的正确值对象
	private Object data;
	public JsonResult(String message){
		this.message=message;
	}
	/**一般查询时调用，封装查询结果*/
	public JsonResult(Object data) {
		this.data=data;
	}
	/**出现异常时时调用*/
	public JsonResult(Throwable t){
		this.state=0;
		this.message=t.getMessage();
	}
	public int getState() {
		return state;
	}
}
