package com.cy.pj.common.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 节点值对象，一般封装树结构（从数据库查询到的数据）
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node implements Serializable{
	private static final long serialVersionUID = 2048083156365694892L;
	/**节点id*/
	private Integer id;
	/**节点名称*/
	private String name;
	/**上级id*/
	private Integer parentId;
}
